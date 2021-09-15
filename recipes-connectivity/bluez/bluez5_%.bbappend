inherit systemd
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-bluez5_utils-add-qca9377-bt-support-1-3.patch"
SRC_URI += "file://0001-BT-add-qca6174-bt-support-2-3.patch"
SRC_URI += "file://0001-BT-add-amlbt-w1-5-5.patch"
SRC_URI += "file://0001-BT-when-iperf-BT-play-caton-1-2.patch"
SRC_URI += "file://main.conf"
SRC_URI += "file://bluez.service"
SRC_URI += "file://bluez_tool.sh"

do_install_append(){
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/bluez.service ${D}/${systemd_unitdir}/system

    mkdir -p ${D}${sysconfdir}/bluetooth
    mkdir -p ${D}${bindir}
    install -m 644 ${WORKDIR}/main.conf ${D}${sysconfdir}/bluetooth/

    echo "MACHINE_ARCH is ${MACHINE_ARCH}"
    case ${MACHINE_ARCH} in
    mesons4*ap223)
        sed -i '/Debug=0/a Device=rtk' ${D}${sysconfdir}/bluetooth/main.conf
    ;;
    mesons4*aq223)
        sed -i '/Debug=0/a Device=aml' ${D}${sysconfdir}/bluetooth/main.conf
    ;;
    *)
        sed -i '/Debug=0/a Device=qca' ${D}${sysconfdir}/bluetooth/main.conf
    ;;
    esac

    install -m 0755 ${WORKDIR}/bluez_tool.sh ${D}/${bindir}
}


FILES_${PN} += "${bindir}/*"
FILES_${PN} += "${systemd_unitdir}/system/*"

SYSTEMD_SERVICE_${PN} += "bluez.service"
