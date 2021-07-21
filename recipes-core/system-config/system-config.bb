inherit systemd

SUMMARY = "system config"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM=""

SRC_URI += "file://system-config.service"
SRC_URI += "file://system-config.sh"


do_install() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/system-config.service ${D}/${systemd_unitdir}/system

    mkdir -p ${D}${bindir}
    install -m 0755 ${WORKDIR}/system-config.sh ${D}/${bindir}
}

do_install_append() {

IS_TV=${@bb.utils.contains('DISTRO_FEATURES','amlogic-tv','ztv','z',d)}

if [ ${IS_TV} == 'ztv' ]; then
cat >> ${D}/${bindir}/system-config.sh <<EOF
if [ -f /lib/modules/${KERNEL_VERSION}/kernel/system/dovi_tm2_tv_16.ko ]
then
    /bin/echo Y > /sys/module/amdolby_vision/parameters/dolby_vision_enable
    /bin/echo 0x100 > /sys/module/amdolby_vision/parameters/debug_dolby
fi
echo 1 > /sys/module/amvdec_ports/parameters/use_di_localbuffer
EOF
else
cat >> ${D}/${bindir}/system-config.sh <<EOF
echo 0 > /sys/module/amvdec_ports/parameters/enable_nr
echo 0 > /sys/module/amvdec_ports/parameters/use_di_localbuffer
EOF
fi
}

FILES_${PN} += "${bindir}/*"
FILES_${PN} += "${systemd_unitdir}/system/*"

SYSTEMD_SERVICE_${PN} += "system-config.service"
