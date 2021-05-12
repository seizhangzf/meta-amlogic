SUMMARY = "A console-only image that fully supports the target device \
hardware."

IMAGE_FEATURES += "splash"

LICENSE = "MIT"

inherit image_meson
inherit core-image
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PACKAGE_INSTALL += "\
                   initramfs-meson-boot \
                   e2fsprogs \
                   mali-examples \
                   fbscripts \
                   directfb \
                   qtbase \
                   qt5everywheredemo \
                   qt5-opengles2-test \
                   vim \
                   rpm \
                   remotecfg \
                   libplayer \
                   gst-aml-plugins \
                   gst-player \
                   playscripts \
                   kernel-modules \
                   gpu \
                   ntfsprogs \
                   ntfs-3g \
                   "

INITRAMFS_SRC_URI = "file://initramfs-default"

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

python do_copy_initramfslist() {
    src_uri = (d.getVar('INITRAMFS_SRC_URI', True) or "").split()
    if len(src_uri) == 0:
        return
    fetcher = bb.fetch2.Fetch(src_uri, d)
    fetcher.unpack(d.getVar('WORKDIR', True))
}
KERNEL_OFFSET = "0x1080000"
KERNEL_OFFSET_sc2-5.4 = "0x3080000"

do_bundle_initramfs_dtb() {
	mkbootimg --kernel ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE} --base 0x0 --kernel_offset ${KERNEL_OFFSET} --ramdisk ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.cpio.gz --second ${DEPLOY_DIR_IMAGE}/dtb.img --output ${DEPLOY_DIR_IMAGE}/boot.img
}

addtask copy_initramfslist before do_image
addtask bundle_initramfs_dtb before do_image_complete after do_image_cpio
#always regenerate boot.img
do_bundle_initramfs_dtb[nostamp] = "1"
