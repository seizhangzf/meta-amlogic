#inherit module
SUMMARY = "Tuner prebuilt drivers"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"
include prebuilt.inc

do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${WORKDIR}/git"
KERNEL_VERSION = "4.9.113"
do_install() {
    KO_DIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/tuner
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${KO_DIR}
    install -m 0644 ${S}/kernel-module/tuner/*.ko ${KO_DIR}
}

FILES_${PN} = "/lib/modules/${KERNEL_VERSION}/kernel/tuner" 