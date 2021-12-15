SUMMARY = "Package Group For AMLOGIC Components"

LICENSE = "CLOSED"

inherit packagegroup

PACKAGES = "\
    packagegroup-amlogic-tv \
    "
RDEPENDS_packagegroup-amlogic-tv = "\
                                    aml-tvserver \
                                    aml-hdcp \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'aml-dtv', 'aml-dtvdemod', '', d)} \
                                    aml-pqserver \
                                    "
