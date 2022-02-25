DESCRIPTION = "nagra-sdk"
SECTION = "nagra-sdk"
LICENSE = "CLOSE"
PV = "git${SRCPV}"
PR = "r0"

#Only enable it in OpenLinux
#SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'nagra', 'git://${AML_GIT_ROOT_OP}/nagra-sdk-nocs.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=projects/openlinux/v3.2-rdk','', d)}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/aml-patches/../vendor/nagra/nagra-sdk')}"

PN = 'nagra-sdk'
SRCREV ?= "${AUTOREV}"
S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

do_install() {
        echo ${MACHINE}
        install -d -m 0755 ${D}/lib/teetz
        case ${MACHINE} in
        mesonsc2*-ah232*)
          CHIPDIR=S905C2
          install -D -m 0644 ${S}/lib/ta/${CHIPDIR}/efdfed0c-a6bd-44d3-9c64-de426fc5fb89.ta ${D}/lib/teetz
        ;;
        mesonsc2*-ah221*)
          CHIPDIR=S905C2L
        ;;
        *)
          CHIPDIR=
        ;;
    esac
        echo ${CHIPDIR}
    install -d -m 0755 ${D}/${libdir}
    install -D -m 0644 ${S}/lib/ca/${libdir}/libnagra_dal.so ${D}/${libdir}

    install -D -m 0644 ${S}/lib/ta/${CHIPDIR}/bc2f95bc-14b6-4445-a43c-a1796e7cac31.ta ${D}/lib/teetz

}

FILES_${PN} = "${libdir}/* /usr/lib/* /lib/teetz/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
