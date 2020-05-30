DESCRIPTION = "liblog for android tools"
PR = "r0"
LICENSE = "Apache-2.0"

FILESEXTRAPATHS_prepend := "${THISDIR}/android-tools-logcat:"

LIC_FILES_CHKSUM = "file://${THISDIR}/android-tools-logcat/LICENSE-2.0;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRCREV = "${AUTOREV}"

PV = "${SRCPV}"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/aml_commonlib;protocol=${AML_GIT_PROTOCOL};branch=master;"
#SRC_URI += "file://liblog.patch"
SRC_URI += "file://LICENSE-2.0"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/aml_commonlib')}"

S = "${WORKDIR}/git/"

#do_package_qa[noexec] = "1"

do_compile(){
    ${MAKE} -C ${S}/liblog
}

do_install(){
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -m 0644 ${S}/liblog/liblog.so ${D}${libdir}
    #install -m 0644 ${S}/liblog/lib/* ${D}${libdir}
    cp -ra ${S}/liblog/include/* ${D}${includedir}
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "

INSANE_SKIP_${PN}-dev = "dev-so"
