inherit module

SUMMARY = "Optee tdk kernel driver"
LICENSE = "CLOSED"

SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/tdk.git;protocol=${AML_GIT_PROTOCOL};branch=tdk-v2.4"
SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

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
TARGET_ARGS_armv7a=" KERNEL_A32_SUPPORT=true"
do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    export ${TARGET_ARGS}
    oe_runmake  -C ${STAGING_KERNEL_DIR} M="${WORKDIR}/git/linuxdriver" ARCH=${ARCH} CROSS_COMPILE=${CROSS_COMPILE} modules
}
KERNEL_MODULE_AUTOLOAD += "optee_armtz"
KERNEL_MODULE_AUTOLOAD += "optee"
