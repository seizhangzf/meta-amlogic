DESCRIPTION = "irdeto-sdk"
SECTION = "irdeto-sdk"
LICENSE = "CLOSE"
PV = "git${SRCPV}"
PR = "r0"

#Only enable it in OpenLinux
#IRDETO_BRANCH = "TBD"
#IRDETO_BRANCH_sc2 = "openlinux/sc2-msr4-linux"
#SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'irdeto', 'git://${AML_GIT_ROOT_OP}/irdeto-sdk.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=${IRDETO_BRANCH}','', d)}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/aml-patches/vendor/irdeto/irdeto-sdk')}"

PN = 'irdeto-sdk'
SRCREV ?= "${AUTOREV}"
S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

IRDETO_PATH = "TBD"
IRDETO_PATH_sc2 = "v3/dev/S905C2"

do_install() {
    install -d -m 0644 ${D}/usr/lib
    install -d -m 0644 ${D}/lib/teetz
    install -d -m 0644 ${D}/usr/include
    install -D -m 0644 ${S}/lib/ca/libirdetoca.so ${D}/usr/lib
    install -D -m 0644 ${S}/lib/ta/${IRDETO_PATH}/b64fd559-658d-48a4-bbc7-b95d8663f457.ta ${D}/lib/teetz
    install -D -m 0644 ${S}/include/*.h ${D}/usr/include
}

FILES_${PN} = "${libdir}/* /lib/teetz/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
