inherit module

SUMMARY = "Optee tdk kernel driver"
LICENSE = "CLOSED"

SRC_URI = "git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh;branch=tdk-v2.4"
SRCREV = "bca0ab730d6e9701730fdc1f3015ccb0abfe60b9"

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
