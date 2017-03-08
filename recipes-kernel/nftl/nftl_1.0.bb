inherit module

SUMMARY = "Amlogic FTL for Nand flash"
LICENSE = "Proprietary"

SRC_URI = "git://git.myamlogic.com/platform/hardware/amlogic/nand.git;branch=m-amlogic-openlinux-20160907;nobranch=1"

SRCREV = "bcb9e0b588b79d0c85d6ca0464fdee7459b9e1a6"

MIRRORS_prepend += "git://git.myamlogic.com/platform/hardware/amlogic/nand.git git://git@openlinux.amlogic.com/yocto/platform/hardware/amlogic/nand.git;protocol=ssh; \n"

do_configure[noexec] = "1"
do_populate_lic[noexec] = "1"

do_install() {
    NANDDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/amlogic/nand
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${NANDDIR}
    install -m 0666 ${WORKDIR}/git/amlnf_3.14/aml_nftl_dev.ko ${NANDDIR}
}
FILES_${PN} = "aml_nftl_dev.ko"
# Header file provided by a separate package
DEPENDS += ""

EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${WORKDIR}/git/amlnf_3.14" EXTRA_CFLAGS+="-I${WORKDIR}/git/amlnf/include -I${STAGING_KERNEL_DIR}/drivers/amlogic/amlnf/ntd" modules'
