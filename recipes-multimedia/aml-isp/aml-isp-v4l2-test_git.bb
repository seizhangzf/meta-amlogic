SUMMARY = "aml isp v4l test"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../license/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

MBRANCH = "android-p"
SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/arm/isp;protocol=${AML_GIT_PROTOCOL};branch=${MBRANCH};"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/arm_isp/')}"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

do_configure[noexec] = "1"
inherit autotools pkgconfig

S="${WORKDIR}/git/app/v4l2_test_android"

EXTRA_OEMAKE="STAGING_DIR=${STAGING_DIR_TARGET} \
                TARGET_DIR=${D} \
             " 
do_compile() {
    cd ${S}
    rm -rf obj
    mkdir obj
    oe_runmake  v4l2_test
}
do_install() {
   install -d ${D}${bindir}
   install -m 0755 ${S}/v4l2_test ${D}${bindir}/
}

 
FILES_${PN} = "${bindir}/* "
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "ldflags"
