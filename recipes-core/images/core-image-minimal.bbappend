inherit image
inherit more-image
inherit more-new
SDKEXTCLASS ?= "${@['populate_sdk', 'populate_sdk_ext']['linux' in d.getVar("SDK_OS", True)]}"
inherit ${SDKEXTCLASS}


FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PACKAGE_INSTALL += "\
                   initramfs-meson-boot \
                   e2fsprogs \
                   kernel-modules \
                   nftl \
                   gpu \
                   kmod \
                   "

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

BOOTIMG_SRC_URI = "file://mkbootimg"
python do_install_mkbootimg() {
    import shutil
    src_uri = (d.getVar('BOOTIMG_SRC_URI', True) or "").split()
    if len(src_uri) == 0:
        return
    fetcher = bb.fetch2.Fetch(src_uri, d)
    fetcher.unpack(d.getVar('STAGING_BINDIR_NATIVE', True))
}

do_install_mkbootimg[nostamp] = "1"
addtask install_mkbootimg before do_bundle_initramfs_dtb

do_bundle_initramfs_dtb() {
	mkbootimg --kernel ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE} --base 0x0 --kernel_offset 0x1080000 --ramdisk ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.cpio.gz --second ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE_FOR_MAKE}-${KERNEL_DEVICETREE} --output ${DEPLOY_DIR_IMAGE}/boot.img
}

addtask bundle_initramfs_dtb before do_image_complete after do_image_cpio
#always regenerate boot.img
do_bundle_initramfs_dtb[nostamp] = "1"

IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"
