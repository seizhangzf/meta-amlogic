SUMMARY = "Widevine DRM implementation."
HOMEPAGE = "https://www.widevine.com/"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

DEPENDS = "gyp openssl python-native protobuf protobuf-native"

PV = "3.5.gitr${SRCPV}"

SRCREV = "dc634218bb17fd88a270d48d1b8f9524d984fd59"

SRC_URI = "git://git@github.com/Metrological/widevine.git;protocol=ssh;branch=master \
           file://0001-Use-system-provided-protobuf.patch \
           file://0002-Allow-build-system-to-chose-protobuf-config-system-p.patch \
           "

inherit pythonnative pkgconfig

PACKAGECONFIG ?= "wpeframework"
PACKAGECONFIG[debug] = ",,"

S = "${WORKDIR}/git"

WIDEVINE_BOARD = "wpe"
WIDEVINE_ARCHITECTURE = "wpe"

export WV_BOARD="${WIDEVINE_BOARD}"
export WV_CC="${CC}"
export WV_CXX="${CXX}"
export WV_AR="${AR}"
export WV_HOST_CXX_FLAGS = "${CXXFLAGS}"
export WV_HOST_CC = "${CC}"
export WV_HOST_CXX = "${CXX}"
export WV_STAGING = "${STAGING_DIR_TARGET}"
export WV_STAGING_NATIVE = "${STAGING_DIR_NATIVE}"
export WV_PROTOBUF_CONFIG = "system"

do_configure() {
    (cd ${B};rm -rf out; rm -rf Makefile;\
    find . -name \*.mk -delete;\
    find . -name \*.pyc -delete;)
}

do_compile() {
    # build in release with -r or -c Release
    cd ${B}; ${PYTHON} build.py ${@bb.utils.contains("PACKAGECONFIG", "debug", "", "-r", d)} ${WIDEVINE_ARCHITECTURE}
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/out/${WIDEVINE_ARCHITECTURE}/${@bb.utils.contains("PACKAGECONFIG", "debug", "Debug", "Release", d)}/widevine_ce_cdm_unittest ${D}${bindir}/
    install -d ${D}${libdir}
    install -m 0755 ${B}/out/${WIDEVINE_ARCHITECTURE}/${@bb.utils.contains("PACKAGECONFIG", "debug", "Debug", "Release", d)}/lib/*.so ${D}${libdir}/

    install -d ${D}${includedir}
    install -m 0644 ${S}/cdm/include/*.h ${D}${includedir}/
    install -m 0644 ${S}/core/include/*.h ${D}${includedir}/

    install -d ${D}${includedir}/host/rpi/
    install -m 0644 ${S}/cdm/src/host/rpi/*.h ${D}${includedir}/host/
}

do_clean[noexec] = "1"
do_buildclean[noexec] = "1"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${includedir}/ ${includedir}/host/ ${bindir}/widevine_ce_cdm_unittest"
