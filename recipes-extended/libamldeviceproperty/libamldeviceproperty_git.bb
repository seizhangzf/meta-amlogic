SUMMARY = "libaml_deviceproperty.so for  Amlogic platform support."
LICENSE = "CLOSED"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/aml_commonlib.git;protocol=${AML_GIT_PROTOCOL};branch=master"

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"
#For common patches
S = "${WORKDIR}/git/"
PROVIDES = "libamldeviceproperty"

do_compile(){
    ${MAKE} -C ${S}/aml_deviceproperty
}

do_install(){
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -m 0644 ${S}/aml_deviceproperty/libamldeviceproperty.so ${D}${libdir}
    cp -ra ${S}/aml_deviceproperty/aml_device_property.h ${D}${includedir}
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "

INSANE_SKIP_${PN}-dev = "dev-so"
