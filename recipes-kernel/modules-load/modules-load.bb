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
    case ${MACHINE} in
    *t5d*)
        sed -i 's@PATH/.*/dvb_demux.ko@PATH/media/aml_hardware_dmx.ko@' ${D}/etc/modules-load.sh
        ;;
    esac
}

do_install_append() {

}

FILES_${PN} = " /etc/modules-load.sh"