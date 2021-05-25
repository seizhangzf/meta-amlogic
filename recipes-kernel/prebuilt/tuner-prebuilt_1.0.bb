inherit module
SUMMARY = "Tuner prebuilt drivers"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

include prebuilt.inc

do_configure[noexec] = "1"
do_compile[noexec] = "1"
ARM_TARGET = "4.9"
ARM_TARGET_aarch64 = "5.4"

S = "${WORKDIR}/git"
do_install() {
    KO_DIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/tuner
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${KO_DIR}
    if $(ls ${S}/kernel-module/tuner/${ARM_TARGET}/*.ko 2>&1 > /dev/null); then
        install -m 0644 ${S}/kernel-module/tuner/${ARM_TARGET}/*.ko ${KO_DIR}
    fi
}
