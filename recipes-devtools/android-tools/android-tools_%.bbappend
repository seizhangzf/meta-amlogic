FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://cutils.mk;subdir=${BPN}"
TOOLS = " cutils"

do_install_append() {

    if echo ${TOOLS} | grep -q "cutils" ; then
        install -d ${D}${libdir}
        install -d ${D}${includedir}/cutils
    install -m0644 ${B}/cutils/libcutils.so ${D}${libdir}
    install -D ${S}/system/core/include/cutils/*.h ${D}${includedir}/cutils
    fi
}
INSANE_SKIP_${PN}-dev += "dev-elf ldflags"
FILES_${PN}-dev += "${includedir}/cutils"
