inherit module

SUMMARY = "Post process modules load"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM=""

SRC_URI_append = " file://modules-load.sh"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}/etc
    install -m 0755 ${WORKDIR}/modules-load.sh ${D}/etc
}

do_install_append() {

}

FILES_${PN} = " /etc/modules-load.sh"