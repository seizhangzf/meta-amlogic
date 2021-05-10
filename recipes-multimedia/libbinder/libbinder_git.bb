DESCRIPTION = "libbinder for IPC"
PR = "r0"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${THISDIR}/files/LICENSE-2.0.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "linux-meson"

SRCREV = "${AUTOREV}"

PV = "${SRCPV}"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/aml_commonlib;protocol=${AML_GIT_PROTOCOL};branch=master;"
SRC_URI += "file://LICENSE-2.0.txt"
SRC_URI += "file://binder.service"

#For common patches
#SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/aml_commonlib')}"

S = "${WORKDIR}/git/libbinder"

inherit systemd

EXTRA_OEMAKE = "'STAGING_DIR=${STAGING_DIR_TARGET}'"

do_compile(){
    cd ${S}
    oe_runmake all
}


do_install(){
    install -d ${D}${libdir}
    install -d ${D}${bindir}
    install -d ${D}${includedir}
    install -d ${D}${includedir}/binder
    install -d ${D}${includedir}/utils
    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${S}/libbinder.so ${D}${libdir}
    install -m 0755 ${S}/servicemanager ${D}${bindir}
    install -m 0644 ${S}/include/binder/* ${D}${includedir}/binder
    install -m 0644 ${S}/include/utils/* ${D}${includedir}/utils
    install -m 0644 ${WORKDIR}/binder.service ${D}/${systemd_unitdir}/system
}

SYSTEMD_SERVICE_${PN} = "binder.service"

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "
FILES_${PN} += "${systemd_unitdir}/system/binder.service"

INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "dev-so ldflags dev-elf"

