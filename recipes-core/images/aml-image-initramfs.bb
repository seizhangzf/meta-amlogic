VIRTUAL-RUNTIME_dev_manager = "busybox-mdev"

DEPENDS_${PN} = " python-native"
PACKAGE_INSTALL = "base-files netbase ${VIRTUAL-RUNTIME_base-utils} ${VIRTUAL-RUNTIME_dev_manager} base-passwd ${ROOTFS_BOOTSTRAP_INSTALL} initramfs-meson-boot e2fsprogs "
IMAGE_FEATURES = ""

export IMAGE_BASENAME = "aml-image-initramfs"
IMAGE_LINGUAS = ""

LICENSE = "MIT"

# don't actually generate an image, just the artifacts needed for one
IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"

inherit core-image

IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"
KERNEL_BOOTARGS = "root=/dev/system rootfstype=ext4"
KERNEL_OFFSET = "0x1080000"
KERNEL_OFFSET_sc2 = "0x3080000"

do_bundle_boot_img() {
	${STAGING_BINDIR_NATIVE}/python-native/python ${STAGING_BINDIR_NATIVE}/mkbootimg.py --kernel ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE} --base 0x0 --kernel_offset ${KERNEL_OFFSET} --cmdline "${KERNEL_BOOTARGS}" --ramdisk ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.cpio.gz --second ${DEPLOY_DIR_IMAGE}/dtb.img --header_version 1 --output ${DEPLOY_DIR_IMAGE}/boot.img
}

do_rootfs[depends] += "aml-hosttools-native:do_install"

addtask bundle_boot_img before do_image_complete after do_image_cpio

do_bundle_boot_img[nostamp] = "1"
