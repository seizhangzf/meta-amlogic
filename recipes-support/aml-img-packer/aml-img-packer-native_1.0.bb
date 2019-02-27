inherit native

SRC_URI += " \
            file://aml_image_v2_packer_new \
            file://aml_sdc_burn.ini \
            file://img2simg \
            file://ubi/* \
            file://ext4/* \
            "
LICENSE = "Closed"
do_populate_lic[noexec] = "1"
