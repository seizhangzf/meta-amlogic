FILESEXTRAPATHS_prepend := "${THISDIR}/busybox:"

SRC_URI += "file://busybox.config"

do_prepare_config_prepend () {
    rm ${WORKDIR}/defconfig
    mv ${WORKDIR}/busybox.config ${WORKDIR}/defconfig
}
