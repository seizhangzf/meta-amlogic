SUMMARY = "Amlogic IR remote configuration setup tool"
DESCRIPTION = "Provides a handy way to setup remote controller key code for Amlogic Soc."
SECTION = "base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ac402fdf7a55a005619ca325e91db3ec"

SRC_URI = "git://${AML_ROOT_GIT}/linux/amlogic/remotecfg.git;protocol=${AML_GIT_PROTOCOL};nobranch=1 \
           file://init"

SRCREV="a09a2c302e1c4bfce5f077f9de1f087874055f94"

S = "${WORKDIR}/git"
PR = "r1"

inherit update-rc.d


INITSCRIPT_NAME = "remote_setup"
INITSCRIPT_PARAMS = "start 12 2 3 4 5 . stop 60 0 1 6 ."

do_install () {
    install -d ${D}/usr/bin
    install -d ${D}${sysconfdir}
    oe_runmake TARGET_DIR=${D} install
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}
