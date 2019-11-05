#!/bin/sh

PATH=/sbin:/bin:/usr/sbin:/usr/bin

ROOT_MOUNT="/rootfs"
INIT="/sbin/init"
ROOT_DEVICE="/dev/system"
MOUNT="/bin/mount"
UMOUNT="/bin/umount"
FIRMWARE=""

# Copied from initramfs-framework. The core of this script probably should be
# turned into initramfs-framework modules to reduce duplication.
udev_daemon() {
	OPTIONS="/sbin/udev/udevd /sbin/udevd /lib/udev/udevd /lib/systemd/systemd-udevd"

	for o in $OPTIONS; do
		if [ -x "$o" ]; then
			echo $o
			return 0
		fi
	done

	return 1
}

_UDEV_DAEMON=`udev_daemon`

early_setup() {
    mkdir -p /proc
    mkdir -p /sys
    mount -t proc proc /proc
    mount -t sysfs sysfs /sys
    mount -t devtmpfs none /dev

    mkdir -p /run
    mkdir -p /var/run

    $_UDEV_DAEMON --daemon
    udevadm trigger --action=add
}

read_args() {
    [ -z "$CMDLINE" ] && CMDLINE=`cat /proc/cmdline`
    for arg in $CMDLINE; do
        optarg=`expr "x$arg" : 'x[^=]*=\(.*\)'`
        case $arg in
            root=*)
                ROOT_DEVICE=$optarg ;;
            rootfstype=*)
                modprobe $optarg 2> /dev/null ;;
            LABEL=*)
                label=$optarg ;;
            video=*)
                video_mode=$arg ;;
            vga=*)
                vga_mode=$arg ;;
            console=*)
                if [ -z "${console_params}" ]; then
                    console_params=$arg
                else
                    console_params="$console_params $arg"
                fi ;;
            firmware=*)
                FIRMWARE=$optarg ;;
            init=*)
                init=$optarg ;;
            debugshell*)
                if [ -z "$optarg" ]; then
                        shelltimeout=30
                else
                        shelltimeout=$optarg
                fi 
        esac
    done
}

boot_root() {
    # Watches the udev event queue, and exits if all current events are handled
    udevadm settle

    # The rootfs does not yet contain kernel modules.  Copy it!
    if [ ! -d ${ROOT_MOUNT}/lib/modules ];
    then
        cp -rf /lib/modules ${ROOT_MOUNT}/lib/
        cp -rf /lib/firmware ${ROOT_MOUNT}/lib/
        cp -rf /etc/modprobe.d ${ROOT_MOUNT}/etc/
        cp -rf /etc/modules-load.d ${ROOT_MOUNT}/etc/
        cp -rf /etc/modules ${ROOT_MOUNT}/etc/
	fi

    mount -n --move /proc ${ROOT_MOUNT}/proc
    mount -n --move /sys ${ROOT_MOUNT}/sys
    mount -n --move /dev ${ROOT_MOUNT}/dev

    cd $ROOT_MOUNT

    # busybox switch_root supports -c option
    exec switch_root -c /dev/console $ROOT_MOUNT $INIT ||
        fatal "Couldn't switch_root, dropping to shell"
}

fatal() {
    echo $1 >$CONSOLE
    echo >$CONSOLE
    exec sh
}

early_setup

[ -z "$CONSOLE" ] && CONSOLE="/dev/console"

read_args

#Waiting for device to become ready

wait_for_device () {
    i=1
    while [ "$i" -le 30 ]
    do
        if [ -b "${ROOT_DEVICE}" ]; then
            echo "${ROOT_DEVICE} is ready now."
            break
        fi
        echo "${ROOT_DEVICE} is not ready.  Waited for ${i} second"
        sleep 1
        i=$((i+1))
    done
}

format_and_install() {
    if [ -f "/${ROOT_MOUNT}/${FIRMWARE}" ] ; then
        echo "formating file system"
        export LD_LIBRARY_PATH=/usr/lib
        umount /dev/system
        mkfs.ext4 -F /dev/system
        mkdir -p system
    	if ! mount -o rw,noatime,nodiratime -t ext4 /dev/system /system ; then
		fatal "Could not mount system device"
    	fi
        echo "extracting file system ..."
        gunzip -c /${ROOT_MOUNT}/${FIRMWARE} | tar -xf - -C /system
        if [ $? -ne 0 ]; then
            echo "Error: untar failed."
        else
            echo "Done"
        fi
        device=/dev/boot
        if [ -f "/${ROOT_MOUNT}/boot.img" ]; then
            echo "Writing boot.img into boot partition(${device})..."
            dd if=/${ROOT_MOUNT}/boot.img of=${device}
            echo "Done"
        fi
        sync
        echo "copying existing modules to rootfs"
        cp -rf /lib/modules /system/lib/
        cp -rf /lib/firmware /system/lib/
        cp -rf /etc/modprobe.d /system/etc/
        cp -rf /etc/modules-load.d /system/etc/
        cp -rf /etc/modules /system/etc/
        echo "update complete"
        umount $ROOT_MOUNT
        ROOT_DEVICE=/dev/system
        ROOT_MOUNT=/system
    else
        echo "cannot locate ${FIRMWARE}"
        echo "boot normally..."
    fi
}

# Try to mount the root image read-write and then boot it up.
# This function distinguishes between a read-only image and a read-write image.
# In the former case (typically an iso), it tries to make a union mount if possible.
# In the latter case, the root image could be mounted and then directly booted up.
mount_and_boot() {
    mkdir $ROOT_MOUNT
    mknod /dev/loop0 b 7 0 2>/dev/null

    if [ "${FIRMWARE}" != "" ];
    then
        ROOT_DEVICE="/dev/mmcblk1p1"
        wait_for_device
    fi
	if [ "$ROOT_DEVICE" != "" ];
	then
    	if ! mount -o rw,noatime,nodiratime $ROOT_DEVICE $ROOT_MOUNT ; then
		fatal "Could not mount rootfs device"
    	fi
	fi

	if [ "${FIRMWARE}" != "" ]; then
		format_and_install
	fi

    if touch $ROOT_MOUNT/bin 2>/dev/null; then
		# The root image is read-write, directly boot it up.
		boot_root
    fi

    # determine which unification filesystem to use
    union_fs_type=""
    if grep -q -w "overlay" /proc/filesystems; then
	union_fs_type="overlay"
    elif grep -q -w "aufs" /proc/filesystems; then
	union_fs_type="aufs"
    else
	union_fs_type=""
    fi

    # make a union mount if possible
    case $union_fs_type in
	"overlay")
	    mkdir -p /rootfs.ro /rootfs.rw
	    if ! mount -n --move $ROOT_MOUNT /rootfs.ro; then
		rm -rf /rootfs.ro /rootfs.rw
		fatal "Could not move rootfs mount point"
	    else
		mount -t tmpfs -o rw,noatime,mode=755 tmpfs /rootfs.rw
		mkdir -p /rootfs.rw/upperdir /rootfs.rw/work
		mount -t overlay overlay -o "lowerdir=/rootfs.ro,upperdir=/rootfs.rw/upperdir,workdir=/rootfs.rw/work" $ROOT_MOUNT
		mkdir -p $ROOT_MOUNT/rootfs.ro $ROOT_MOUNT/rootfs.rw
		mount --move /rootfs.ro $ROOT_MOUNT/rootfs.ro
		mount --move /rootfs.rw $ROOT_MOUNT/rootfs.rw
	    fi
	    ;;
	"aufs")
	    mkdir -p /rootfs.ro /rootfs.rw
	    if ! mount -n --move $ROOT_MOUNT /rootfs.ro; then
		rm -rf /rootfs.ro /rootfs.rw
		fatal "Could not move rootfs mount point"
	    else
		mount -t tmpfs -o rw,noatime,mode=755 tmpfs /rootfs.rw
		mount -t aufs -o "dirs=/rootfs.rw=rw:/rootfs.ro=ro" aufs $ROOT_MOUNT
		mkdir -p $ROOT_MOUNT/rootfs.ro $ROOT_MOUNT/rootfs.rw
		mount --move /rootfs.ro $ROOT_MOUNT/rootfs.ro
		mount --move /rootfs.rw $ROOT_MOUNT/rootfs.rw
	    fi
	    ;;
	"")
	    mount -t tmpfs -o rw,noatime,mode=755 tmpfs $ROOT_MOUNT/media
	    ;;
    esac

    # boot the image
    boot_root
}

wait_for_device
mount_and_boot
