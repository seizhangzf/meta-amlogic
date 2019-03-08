inherit native

SRC_URI += " \
            file://aml_image_v2_packer_new \
            file://aml_sdc_burn.ini \
            file://img2simg \
            file://res_packer \
            file://axg/* \
            file://gxl/* \
            file://g12a/* \
            file://txlx/* \
            "
LICENSE = "Closed"
do_populate_lic[noexec] = "1"

do_install () {
    mkdir -p ${D}${bindir}/aml-img-packer/
    install -m 0755 ${S}/../aml_image_v2_packer_new ${D}${bindir}/aml-img-packer/
    install -m 0644 ${S}/../aml_sdc_burn.ini ${D}${bindir}/aml-img-packer/
    install -m 0755 ${S}/../img2simg ${D}${bindir}/aml-img-packer/
    install -m 0755 ${S}/../res_packer ${D}${bindir}/aml-img-packer/
    cp -rf ${S}/../axg ${D}${bindir}/aml-img-packer/
    cp -rf ${S}/../gxl ${D}${bindir}/aml-img-packer/
    cp -rf ${S}/../g12a ${D}${bindir}/aml-img-packer/
    cp -rf ${S}/../txlx ${D}${bindir}/aml-img-packer/
}
