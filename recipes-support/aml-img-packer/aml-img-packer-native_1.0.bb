inherit native

SRC_URI = " \
            file://aml_image_v2_packer_new \
            file://aml_sdc_burn.ini \
            file://img2simg \
            file://res_packer \
            "

SRC_URI_append_axg = " file://axg/"
SRC_URI_append_gxl = " file://gxl/"
SRC_URI_append_g12a = " file://g12a/"
SRC_URI_append_txlx = " file://txlx/"
SRC_URI_append_g12b = " file://g12b/"
SRC_URI_append_tm2 = " file://tm2/"

LICENSE = "Closed"
do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

SOC_FAMILY = "gxl"
SOC_FAMILY_gxl = "gxl"
SOC_FAMILY_axg = "axg"
SOC_FAMILY_txlx = "txlx"
SOC_FAMILY_g12a = "g12a"
SOC_FAMILY_g12b = "g12b"
SOC_FAMILY_tm2 = "tm2"

SOC_BOARD = "default"
SOC_BOARD_ab301 = "ab301"
SOC_BOARD_t962x3 = "ab301"

PR = "r3"

S= "${WORKDIR}"
do_install () {
    install -d ${D}${bindir}/aml-img-packer/
    install -d ${D}${bindir}/aml-img-packer/${SOC_FAMILY}/logo_img_files
    install -m 0755 ${WORKDIR}/aml_image_v2_packer_new ${D}${bindir}/aml-img-packer/
    install -m 0644 ${WORKDIR}/aml_sdc_burn.ini ${D}${bindir}/aml-img-packer/
    install -m 0755 ${WORKDIR}/img2simg ${D}${bindir}/aml-img-packer/
    install -m 0755 ${WORKDIR}/res_packer ${D}${bindir}/aml-img-packer/
    cd ${WORKDIR}/${SOC_FAMILY}
    for file in $(find -maxdepth 1 -type f); do
            install -m 0644 -D ${file} ${D}${bindir}/aml-img-packer/${SOC_FAMILY}/${file}
        done
    cd ${WORKDIR}/${SOC_FAMILY}/logo_img_files
    for file in $(find -maxdepth 1 -type f); do
            install -m 0644 -D ${file} ${D}${bindir}/aml-img-packer/${SOC_FAMILY}/logo_img_files/${file}
    done
    if [ -e ${WORKDIR}/${SOC_FAMILY}/logo_img_files/${SOC_BOARD}/bootup.bmp ] ; then
        install -m 0644 -D ${WORKDIR}/${SOC_FAMILY}/logo_img_files/${SOC_BOARD}/bootup.bmp ${D}${bindir}/aml-img-packer/${SOC_FAMILY}/logo_img_files/bootup.bmp
    fi
}
FILES_${PN} = "${bindir}/aml-img-packer/*"
