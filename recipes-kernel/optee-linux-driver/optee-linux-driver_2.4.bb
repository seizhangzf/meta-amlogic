inherit module

SUMMARY = "Optee tdk kernel driver"
LICENSE = "CLOSED"

SRC_URI = "git://git.myamlogic.com/vendor/amlogic/tdk.git;nobranch=1"
MIRRORS_prepend += "git://git.myamlogic.com/vendor/amlogic/tdk.git git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh; \n"
SRCREV = "2b4fb5c1dcef812b296194d8362d6c891b05cb34"

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"

do_install() {
    MODULE_DIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/amlogic/optee
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${MODULE_DIR}
    install -m 0666 ${WORKDIR}/git/linuxdriver/optee.ko ${MODULE_DIR}
    install -m 0666 ${WORKDIR}/git/linuxdriver/optee/optee_armtz.ko ${MODULE_DIR}
}

FILES_${PN} = "optee_armtz.ko optee.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"

EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${WORKDIR}/git/linuxdriver" modules'

KERNEL_MODULE_AUTOLOAD += "optee_armtz"
KERNEL_MODULE_AUTOLOAD += "optee"
