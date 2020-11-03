DESCRIPTION = "android tools adbd"
PR = "r0"
LICENSE = "Apache-2.0"


S = "${WORKDIR}"

DEPENDS := "zlib openssl"

#FILESEXTRAPATHS_prepend := "${WORKDIR}/debian/patches:"

LIC_FILES_CHKSUM = "file://${WORKDIR}/debian/copyright;md5=141efd1050596168ca05ced04e4f498b"

SRC_URI = "https://launchpad.net/ubuntu/+archive/primary/+sourcefiles/android-tools/4.2.2+git20130218-3ubuntu23/android-tools_${PV}.orig.tar.xz;name=core \
https://launchpad.net/ubuntu/+archive/primary/+sourcefiles/android-tools/4.2.2+git20130218-3ubuntu41/android-tools_${PV}-3ubuntu41.debian.tar.gz;name=debian"

SRC_URI += "file://0001-Fix-makefiles-for-out-of-tree-build.patch"
SRC_URI += "file://0002-Fix-adbd-for-non-Ubuntu-systems.patch;striplevel=1"
SRC_URI += "file://0003-Fix-build-issue-with-uclibc.patch"
SRC_URI += "file://0004-Fix-build-issue-with-musl.patch"
SRC_URI += "file://0006-fix-big-endian-build.patch"
SRC_URI += "file://0005-Use-pkgconf-to-get-libs-deps.patch"
SRC_URI += "file://0007-include-cdefs-h-when-needed.patch"
SRC_URI += "file://0008-Include-sysmacros.h-to-compile-with-glibc-2.28.patch"
SRC_URI += "file://0009-Fix-makefiles-for-out-of-tree-ext4_utils-build.patch"
SRC_URI += "file://0010-adb-added-patch-for-openssl-1.1.0-compatibility.patch"

SRC_URI += "file://adbd.service"
SRC_URI += "file://adbd_post.sh"
SRC_URI += "file://adbd_prepare.sh"


SRC_URI[core.md5sum] = "0e653b129ab0c95bdffa91410c8b55be"
SRC_URI[debian.md5sum] = "214cce305149326ca1aa661ef2b54886"

SYSTEMD_AUTO_ENABLE = "disable"
SYSTEMD_SERVICE_${PN} = "adbd.service"

FILES_${PN} += "${systemd_unitdir}/system/adbd.service"



do_process_patches_in_srccode_tarball() {
    #re-organize the code directory hierarchy, because some pathes can't apply use the default hierarchy after unpack
    rm -rf ${S}/core
    rm -rf ${S}/extras
    mv ${S}/android-tools/* ${S}
    rm -rf ${S}/android-tools

    #apply the patches in the src code tarball, otherwise patches in 'SRC_URI' can't apply 
    patch -p1 -d ${S} <  ${S}/debian/patches/remove-selinux-android.patch
    patch -p1 -d ${S} <  ${S}/debian/patches/add_adbd.patch
    patch -p1 -d ${S} <  ${S}/debian/patches/reboot-syscall.patch
    patch -p1 -d ${S} <  ${S}/debian/patches/enable-emulator.patch
    patch -p1 -d ${S} <  ${S}/debian/patches/ppc64el-ftbfs.patch
    patch -p1 -d ${S} <  ${S}/debian/patches/add-bq-vendor-id.patch
    patch -p1 -d ${S} <  ${S}/debian/patches/add-meizu-vendor-id.patch
}

do_compile(){
    SRCDIR="${S}" ${MAKE} -f ${S}/debian/makefiles/adbd.mk
}

do_install(){
    install -d ${D}${bindir}
    install -d ${D}/${systemd_unitdir}/system
    install -m 0755 adbd ${D}${bindir}
    install -m 0755 adbd_prepare.sh ${D}${bindir}
    install -m 0755 adbd_post.sh ${D}${bindir}
    install -m 0644 ${S}/adbd.service ${D}/${systemd_unitdir}/system 
    echo "MACHINE_ARCH is ${MACHINE_ARCH}"
    case ${MACHINE_ARCH} in
        "mesonc1_ae409" | "mesonc1_ae401" | "mesonc1_ae400")
            sed 's@ff400000.dwc2_a@ff500000.dwc2_a@' -i ${D}${bindir}/adbd_post.sh
        ;;
        "mesonsc2_ah212" | "mesonsc2_ah219")
            sed 's@ff400000.dwc2_a@fdd00000.dwc2_a@' -i ${D}${bindir}/adbd_post.sh
        ;;
    esac
}

addtask do_process_patches_in_srccode_tarball before do_patch after do_unpack
inherit systemd
