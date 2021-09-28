inherit image
SDKEXTCLASS ?= "${@['populate_sdk', 'populate_sdk_ext']['linux' in d.getVar("SDK_OS", True)]}"
inherit ${SDKEXTCLASS}

DEPENDS_append = " android-tools-native"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
#IMAGE_INSTALL = "udev busybox ${ROOTFS_PKGMANAGE_BOOTSTRAP}"
IMAGE_INSTALL = "udev busybox"
IMAGE_INSTALL_append = "\
                    initramfs-meson-boot \
                    e2fsprogs \
                   "

#IMAGE_INSTALL_append_aarch64 = "\
#                    kernel-modules \
#                    gpu \
#                    kmod \
#                    ${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS} \
#                    ${CORE_IMAGE_EXTRA_INSTALL} \
#                    "

IMAGE_INSTALL_append = "\
                    cryptsetup \
                    lvm2-udevrules \
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

#do_rootfs_append () {
#    import shutil
#    rootfsdir = d.getVar('IMAGE_ROOTFS', True) or ""
#    bootdir = "%s/boot" % rootfsdir
#    shutil.rmtree(bootdir)
#}

KERNEL_BOOTARGS = "rootfstype=ext4"
KERNEL_OFFSET = "0x1080000"
KERNEL_OFFSET_sc2 = "0x3080000"

do_bundle_initramfs_dtb() {
	mkbootimg --kernel ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE} --base 0x0 --kernel_offset ${KERNEL_OFFSET} --cmdline "${KERNEL_BOOTARGS}" --ramdisk ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.cpio.gz --second ${DEPLOY_DIR_IMAGE}/dtb.img --output ${DEPLOY_DIR_IMAGE}/boot.img
}

addtask bundle_initramfs_dtb before do_image_complete after do_image_cpio
#always regenerate boot.img
do_bundle_initramfs_dtb[nostamp] = "1"

do_rootfs[depends] += "aml-hosttools-native:do_install"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"

deploy_verity_hash() {
    if [ -f ${DEPLOY_DIR_IMAGE}/${DM_VERITY_IMAGE}.${DM_VERITY_IMAGE_TYPE}.verity.env ]; then
        bbwarn "install -D -m 0644 ${DEPLOY_DIR_IMAGE}/${DM_VERITY_IMAGE}.${DM_VERITY_IMAGE_TYPE}.verity.env \
            ${IMAGE_ROOTFS}/${datadir}/system-dm-verity.env"
        install -D -m 0644 ${DEPLOY_DIR_IMAGE}/${DM_VERITY_IMAGE}.${DM_VERITY_IMAGE_TYPE}.verity.env \
            ${IMAGE_ROOTFS}/${datadir}/system-dm-verity.env
    else
        bberror "Cannot find ${DEPLOY_DIR_IMAGE}/${DM_VERITY_IMAGE}.${DM_VERITY_IMAGE_TYPE}.verity.env"
    fi

    if [ -f ${DEPLOY_DIR_IMAGE}/${VENDOR_DM_VERITY_IMAGE}.${DM_VERITY_IMAGE_TYPE}.verity.env ]; then
        bbwarn " install -D -m 0644 ${DEPLOY_DIR_IMAGE}/${VENDOR_DM_VERITY_IMAGE}.${DM_VERITY_IMAGE_TYPE}.verity.env \
            ${IMAGE_ROOTFS}/${datadir}/vendor-dm-verity.env"
        install -D -m 0644 ${DEPLOY_DIR_IMAGE}/${VENDOR_DM_VERITY_IMAGE}.${DM_VERITY_IMAGE_TYPE}.verity.env \
            ${IMAGE_ROOTFS}/${datadir}/vendor-dm-verity.env
    else
        bberror "Cannot find ${DEPLOY_DIR_IMAGE}/${VENDOR_DM_VERITY_IMAGE}.${DM_VERITY_IMAGE_TYPE}.verity.env"
    fi
}
do_rootfs[depends] += "${@bb.utils.contains('DISTRO_FEATURES', 'dm-verity', '${DM_VERITY_IMAGE}:do_image_${DM_VERITY_IMAGE_TYPE}', '', d)}"
do_rootfs[depends] += "${@bb.utils.contains('DISTRO_FEATURES', 'dm-verity', '${VENDOR_DM_VERITY_IMAGE}:do_image_${DM_VERITY_IMAGE_TYPE}', '', d)}"
ROOTFS_POSTPROCESS_COMMAND += "${@bb.utils.contains('DISTRO_FEATURES', 'dm-verity', 'deploy_verity_hash;', '', d)}"
