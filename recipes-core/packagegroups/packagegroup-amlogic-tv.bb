SUMMARY = "Package Group For AMLOGIC Components"

LICENSE = "CLOSED"

inherit packagegroup

PACKAGES = "\
    packagegroup-amlogic-tv \
    "
RDEPENDS_packagegroup-amlogic-tv = "\
                                    aml-tvserver \
                                    aml-pqserver \
                                    aml-hdcp \
                                    "
