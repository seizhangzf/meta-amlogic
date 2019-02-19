FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''

SRC_URI = "git://git.myamlogic.com/firmware/bin/bl2.git;nobranch=1"

MIRRORS_prepend += "git://git.myamlogic.com/firmware/bin/bl2.git git://git@openlinux.amlogic.com/firmware/bin/bl2.git;protocol=ssh; \n"
SRCREV="353a31d228dfcb6439304dfaf12ddb2886b602d6"

S = "${WORKDIR}/git"

PR = "r1"
PV = "v1.0+git${SRCREV}"

do_copy_lic() {
    cp ${THISDIR}/common/COPYING  ${S}
}

addtask copy_lic before do_patch after do_unpack

do_install () {
    mkdir -p ${D}${datadir}/bootloader/bl2/bin
    cp -rf ${S}/* ${D}${datadir}/bootloader/bl2/bin/
}


FILES_${PN}-dev= "${datadir}/bootloader/bl2/bin/*"
