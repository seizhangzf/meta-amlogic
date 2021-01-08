DESCRIPTION = "aml mediahal sdk"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-amlogic/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/mediahal_sdk;protocol=${AML_GIT_PROTOCOL};branch=linux-master"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/aml-patches/../multimedia/mediahal-sdk')}"

do_compile[noexec] = "1"

SRCREV ?= "${AUTOREV}"

PV = "git${SRCPV}"

S = "${WORKDIR}/git"

ARM_TARGET="arm.aapcs-linux.hard"
TA_TARGET="noarch"
do_install() {
    install -d -m 0644 ${D}/usr/lib
    install -d -m 0644 ${D}/usr/include

    install -D -m 0644 ${S}/prebuilt/${TA_TARGET}/include/*.h ${D}/usr/include
    install -D -m 0644 ${S}/prebuilt/${ARM_TARGET}/libmediahal_resman.so ${D}/usr/lib
}

FILES_${PN} = "${libdir}/* ${includedir}/*"
FILES_${PN}-dev = "${includedir}/* "
