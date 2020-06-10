inherit image
SDKEXTCLASS ?= "${@['populate_sdk', 'populate_sdk_ext']['linux' in d.getVar("SDK_OS", True)]}"
inherit ${SDKEXTCLASS}

DEPENDS_${PN}_append = " python-native"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
IMAGE_INSTALL = "udev busybox ${ROOTFS_PKGMANAGE_BOOTSTRAP}"
IMAGE_INSTALL_append = "\
                    initramfs-meson-boot \
                    e2fsprogs \
                   "

IMAGE_INSTALL_append_aarch64 = "\
                    kernel-modules \
                    gpu \
                    kmod \
                    ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
                    ${CORE_IMAGE_EXTRA_INSTALL} \
                    "

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"

python __anonymous () {
    import re
    type = d.getVar('KERNEL_IMAGETYPE', True) or ""
    alttype = d.getVar('KERNEL_ALT_IMAGETYPE', True) or ""
    types = d.getVar('KERNEL_IMAGETYPES', True) or ""
    if type not in types.split():
        types = (type + ' ' + types).strip()
    if alttype not in types.split():
        types = (alttype + ' ' + types).strip()
    d.setVar('KERNEL_IMAGETYPES', types)

    typeformake = re.sub(r'\.gz', '', types)
    d.setVar('KERNEL_IMAGETYPE_FOR_MAKE', typeformake)
}

do_rootfs_append () {
    import shutil
    rootfsdir = d.getVar('IMAGE_ROOTFS', True) or ""
    bootdir = "%s/boot" % rootfsdir
    shutil.rmtree(bootdir)
}

KERNEL_BOOTARGS = "rootfstype=ext4"

do_bundle_initramfs_dtb() {
	${STAGING_BINDIR_NATIVE}/python-native/python ${STAGING_BINDIR_NATIVE}/mkbootimg.py --kernel ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE} --base 0x0 --kernel_offset 0x1080000 --cmdline "${KERNEL_BOOTARGS}" --ramdisk ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.cpio.gz --second ${DEPLOY_DIR_IMAGE}/dtb.img --header_version 1 --output ${DEPLOY_DIR_IMAGE}/boot.img
}

addtask bundle_initramfs_dtb before do_image_complete after do_image_cpio
#always regenerate boot.img
do_bundle_initramfs_dtb[nostamp] = "1"

do_rootfs[depends] += "aml-hosttools-native:do_install"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"
