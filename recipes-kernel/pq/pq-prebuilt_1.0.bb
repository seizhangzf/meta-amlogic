inherit module
SUMMARY = "pq prebuilt drivers"
LICENSE = "CLOSED"

SRC_URI = " \
           file://dnlp_alg_32.ko \
           file://ldim_alg_32.ko \
           "
do_configure[noexec] = "1"

do_compile() {
    cp ${WORKDIR}/*.ko ${S}
}

do_install() {
    KO_DIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/pq
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${KO_DIR}
    install -m 0644 *.ko ${KO_DIR}
}

S = "${WORKDIR}/modules"

