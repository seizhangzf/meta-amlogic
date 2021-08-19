inherit module
SUMMARY = "pq prebuilt drivers"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

include prebuilt.inc

do_configure[noexec] = "1"
do_compile[noexec] = "1"
ARM_TARGET = "32"
ARM_TARGET_aarch64 = "64"

S = "${WORKDIR}/git"
do_install() {
    PQ_KO_VERSION=$(echo ${KERNEL_VERSION} | cut -d'-' -f1 | cut -d'.' -f1,2)
    KO_DIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/pq
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${KO_DIR}
    if $(ls ${S}/kernel-module/pq/${PQ_KO_VERSION}/${ARM_TARGET}/*.ko 2>&1 > /dev/null); then
        install -m 0644 ${S}/kernel-module/pq/${PQ_KO_VERSION}/${ARM_TARGET}/*.ko ${KO_DIR}
    fi
}
