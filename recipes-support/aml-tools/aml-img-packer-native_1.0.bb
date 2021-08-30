SUMMARY = "aml image packing utility"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

inherit native
include hosttools.inc

do_configure[noexec] = "1"
do_compile[noexec] = "1"

SOC_FAMILY = "gxl"
SOC_FAMILY_gxl = "gxl"
SOC_FAMILY_axg = "axg"
SOC_FAMILY_txlx = "txlx"
SOC_FAMILY_g12a = "g12a"
SOC_FAMILY_g12b = "g12b"
SOC_FAMILY_tm2 = "tm2"
SOC_FAMILY_sc2 = "sc2"
SOC_FAMILY_t5d = "t5d"
SOC_FAMILY_s4 = "s4"

SOC_BOARD = "default"
SOC_BOARD_ab301 = "ab301"
SOC_BOARD_t962x3 = "ab301"

PR = "r3"

S= "${WORKDIR}/git"

do_install () {
    install -d ${D}${bindir}/aml-img-packer/
    install -d ${D}${bindir}/aml-img-packer/${SOC_FAMILY}/logo_img_files
    install -m 0755 ${S}/aml-image-packer/aml_image_v2_packer_new ${D}${bindir}/aml-img-packer/
    install -m 0644 ${S}/aml-image-packer/aml_sdc_burn.ini ${D}${bindir}/aml-img-packer/
    install -m 0755 ${S}/aml-image-packer/img2simg ${D}${bindir}/aml-img-packer/
    install -m 0755 ${S}/aml-image-packer/res_packer ${D}${bindir}/aml-img-packer/
    cd ${S}/aml-image-packer/${SOC_FAMILY}
    for file in $(find -maxdepth 1 -type f); do
            install -m 0644 -D ${file} ${D}${bindir}/aml-img-packer/${SOC_FAMILY}/${file}
        done
    cd ${S}/aml-image-packer/${SOC_FAMILY}/logo_img_files
    for file in $(find -maxdepth 1 -type f); do
            install -m 0644 -D ${file} ${D}${bindir}/aml-img-packer/${SOC_FAMILY}/logo_img_files/${file}
    done
    if [ -e ${S}/aml-image-packer/${SOC_FAMILY}/logo_img_files/${SOC_BOARD}/bootup.bmp ] ; then
        install -m 0644 -D ${S}/aml-image-packer/${SOC_FAMILY}/logo_img_files/${SOC_BOARD}/bootup.bmp ${D}${bindir}/aml-img-packer/${SOC_FAMILY}/logo_img_files/bootup.bmp
    fi
}
FILES_${PN} = "${bindir}/aml-img-packer/*"
