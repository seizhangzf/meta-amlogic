DEPENDS_append = " aml-img-packer-native"
DEPENDS_append_onepass = " core-image-minimal"
DEPENDS_append = " vendor-image"

SOC_FAMILY = "gxl"
SOC_FAMILY_gxl = "gxl"
SOC_FAMILY_axg = "axg"
SOC_FAMILY_txlx = "txlx"
SOC_FAMILY_g12a = "g12a"
SOC_FAMILY_g12b = "g12b"
SOC_FAMILY_tm2 = "tm2"

PACK_CONF = "${@bb.utils.contains('DISTRO_FEATURES','absystem','aml_upgrade_package_ab.conf','aml_upgrade_package.conf', d)}"

do_aml_pack_32b() {
       if ${@bb.utils.contains('DISTRO_FEATURES','onepass','false','true',d)}; then
            dir64b=`echo ${DEPLOY_DIR_IMAGE} | sed -r 's/_32b//'`
            if [ -e $dir64b ];then
                cp $dir64b/boot.img ${DEPLOY_DIR_IMAGE}
                cp $dir64b/dtb.img ${DEPLOY_DIR_IMAGE}/
                cp $dir64b/u-boot* ${DEPLOY_DIR_IMAGE}
            fi
        fi

    cp -f ${STAGING_DIR_NATIVE}${bindir}/aml-img-packer/aml_* ${DEPLOY_DIR_IMAGE}/
    cp -f ${STAGING_DIR_NATIVE}${bindir}/aml-img-packer/img2simg ${DEPLOY_DIR_IMAGE}/
    cp -f ${STAGING_DIR_NATIVE}${bindir}/aml-img-packer/res_packer ${DEPLOY_DIR_IMAGE}/
    cp -rf ${STAGING_DIR_NATIVE}${bindir}/aml-img-packer/${SOC_FAMILY}/* ${DEPLOY_DIR_IMAGE}/
    ${DEPLOY_DIR_IMAGE}/res_packer -r ${DEPLOY_DIR_IMAGE}/logo_img_files ${DEPLOY_DIR_IMAGE}/logo.img
    ${DEPLOY_DIR_IMAGE}/img2simg ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.ext4 ${DEPLOY_DIR_IMAGE}/rootfs.ext4.img2simg
    if [ -f ${DEPLOY_DIR_IMAGE}/vendor-image-${MACHINE}.ext4 ];then
        ${DEPLOY_DIR_IMAGE}/img2simg ${DEPLOY_DIR_IMAGE}/vendor-image-${MACHINE}.ext4 ${DEPLOY_DIR_IMAGE}/vendor.ext4.img2simg
    fi

    ${DEPLOY_DIR_IMAGE}/aml_image_v2_packer_new -r ${DEPLOY_DIR_IMAGE}/${PACK_CONF} ${DEPLOY_DIR_IMAGE} ${DEPLOY_DIR_IMAGE}/aml_upgrade_package.img
}
do_aml_pack_32b[depends] += "core-image-minimal:do_image_complete "
do_aml_pack_32b[depends] += "vendor-image:do_image_complete "
addtask aml_pack_32b after do_image_complete before do_build
do_aml_pack_32b[nostamp] = "1"