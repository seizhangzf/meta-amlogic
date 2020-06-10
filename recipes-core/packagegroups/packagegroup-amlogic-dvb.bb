SUMMARY = "Package Group For AMLOGIC Components"

LICENSE = "CLOSED"

inherit packagegroup

PACKAGES = "\
    packagegroup-amlogic-dvb \
    "

RDEPENDS_packagegroup-amlogic-dvb = " aml-dvb \
                                    tuner-prebuilt \
                                     "
