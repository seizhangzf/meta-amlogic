FILESEXTRAPATHS_prepend := "${THISDIR}/busybox:"

SRC_URI += "file://busybox.config"
SRC_URI += "file://nice.cfg"

do_prepare_config_prepend () {
    rm ${WORKDIR}/defconfig
    mv ${WORKDIR}/busybox.config ${WORKDIR}/defconfig
}
