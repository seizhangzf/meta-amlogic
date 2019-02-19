inherit native
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

EXTRA_OEMAKE = ''

#SRC_URI = "git://git.myamlogic.com/uboot.git;nobranch=1"
SRC_URI = "git://git.myamlogic.com/amlogic/tools/fip.git;nobranch=1"

SRC_URI += "file://0001-Take-UBOOT_SRC_FOLDER-from-env-and-use-absolute-path.patch"

#MIRRORS_prepend += "git://git.myamlogic.com/uboot.git git://git@openlinux.amlogic.com/yocto/uboot.git;protocol=ssh; \n"
SRCREV="3111b84198e37a51135baa6b462805a230b76fee"

S = "${WORKDIR}/git"

PR = "r1"
PV = "v1.0+git${SRCREV}"

do_copy_lic() {
    cp ${THISDIR}/common/COPYING  ${S}
}

addtask copy_lic before do_patch after do_unpack

do_install () {
    mkdir -p ${D}/${bindir}/fip/
    cp -rf ${S}/* ${D}/${bindir}/fip/
    cp mk ${D}/${bindir}
}
