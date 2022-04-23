SUMMARY = "aml media hal"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

#do_configure[noexec] = "1"
#inherit autotools pkgconfig
inherit cmake
DEPENDS += "aml-amaudioutils liblog aml-libdvr aml-mediahal-sdk aml-cas-hal libamadec"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/common/aml_mp_sdk;protocol=${AML_GIT_PROTOCOL};branch=master;"
#SRC_URI = "file://aml-comp/multimedia/aml_mp_sdk;protocol=file;branch=master;"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/multimedia/aml_mp_sdk')}"
SRCREV = "${AUTOREV}"
S="${WORKDIR}/git/"
RDEPENDS_${PN} += "aml-amaudioutils liblog aml-libdvr aml-mediahal-sdk aml-cas-hal libamadec"
EXTRA_OEMAKE = "STAGING_DIR=${STAGING_DIR_TARGET} \
		  TARGET_DIR=${D} \
		"

#do_compile () {
#	cd ${S}
#	oe_runmake -j1 ${EXTRA_OEMAKE} all
#}
#do_install() {
#    install -d ${D}${bindir}
#    install -d ${D}${libdir}
#    install -d ${D}${includedir}
#    cp -rf ${S}/include/Aml_MP        ${D}/usr/include
#    install -m 0644  -D ${S}/libaml_mp_sdk.so ${D}/usr/lib/libaml_mp_sdk.so
#    install -m 0755  -D ${S}/amlMpPlayerDemo  ${D}/usr/bin/
#}


FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-dev = "dev-elf dev-so"

