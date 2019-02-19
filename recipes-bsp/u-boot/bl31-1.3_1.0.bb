FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''

SRC_URI = "git://git.myamlogic.com/firmware/bin/bl31.git;nobranch=1"

MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl31.git git://git@openlinux.amlogic.com/firmware/bin/bl31.git;protocol=ssh; \n"
SRCREV="192987dfaddc779724b3cd40515c29eeb393a81d"

S = "${WORKDIR}/git"

PR = "r1"
PV = "v1.0+git${SRCREV}"

do_copy_lic() {
    cp ${THISDIR}/common/COPYING  ${S}
}

addtask copy_lic before do_patch after do_unpack

do_install () {
    mkdir -p ${D}${datadir}/bootloader/bl31_1.3/bin
    cp -rf ${S}/* ${D}${datadir}/bootloader/bl31_1.3/bin/
}


FILES_${PN}-dev= "${datadir}/bootloader/bl31_1.3/bin/*"
