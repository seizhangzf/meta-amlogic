inherit module
SUMMARY = "Tuner prebuilt drivers"
LICENSE = "CLOSED"
SRC_URI = " \
           file://atbm8881_fe_64.ko \
           file://avl6762_fe_64.ko \
           file://mxl661_fe_64.ko \
           file://r840_fe_64.ko \
           file://r842_fe_64.ko \
           file://si2151_fe_64.ko \
           file://si2159_fe_64.ko \
           file://si2168_fe_64.ko \
           "

do_configure[noexec] = "1"

do_compile() {
    cp ${WORKDIR}/*.ko ${S}
}

do_install() {
    KO_DIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/tuner
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${KO_DIR}
    install -m 0644 *.ko ${KO_DIR}
}

S = "${WORKDIR}/modules"

