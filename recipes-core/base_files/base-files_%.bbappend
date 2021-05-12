#/*-----------------------TM2 TV--------------------------------------*/
do_install_append_tm2 () {
    mkdir -p ${D}/vendor
    mkdir -p ${D}/data
    cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/vendor            /vendor                    auto       defaults              0  0
EOF
}
FILES_${PN}_append_tm2 = " /vendor/* "
dirs755_append_tm2 = " /vendor "

#/*-----------------------T5D TV--------------------------------------*/
do_install_append_t5d () {
    mkdir -p ${D}/vendor
    mkdir -p ${D}/data
    cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/vendor            /vendor                    auto       defaults              0  0
EOF
}
FILES_${PN}_append_t5d = " /vendor/* "
dirs755_append_t5d = " /vendor "

#/*-----------------------T5D-K5.4 TV--------------------------------------*/
do_install_append_t5d-5.4 () {
    mkdir -p ${D}/vendor
    mkdir -p ${D}/data
    cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/vendor            /vendor                    auto       defaults              0  0
EOF
}
FILES_${PN}_append_t5d-5.4 = " /vendor/* "
dirs755_append_t5d-5.4 = " /vendor "

#/*-----------------------G12A STB--------------------------------------*/
do_install_append_g12a () {
    mkdir -p ${D}/vendor/lib
    mkdir -p ${D}/data
    mkdir -p ${D}/opt
    ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/vendor/lib/libdolbyms12.so
}
FILES_${PN}_append_g12a = " /vendor/* /data /opt "
dirs755_append_g12a = " /vendor /data /opt"

#/*-----------------------S4 STB--------------------------------------*/
do_install_append_s4 () {
    mkdir -p ${D}/vendor/lib
    mkdir -p ${D}/data
    mkdir -p ${D}/opt
    ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/vendor/lib/libdolbyms12.so
}
FILES_${PN}_append_s4 = " /vendor/* /data /opt "
dirs755_append_s4 = " /vendor /data /opt"

#/*-----------------------SC2 STB--------------------------------------*/
do_install_append_sc2 () {
    mkdir -p ${D}/vendor/lib
    mkdir -p ${D}/data
    mkdir -p ${D}/opt
    ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/vendor/lib/libdolbyms12.so
}
FILES_${PN}_append_sc2 = " /vendor/* /data /opt "
dirs755_append_sc2 = " /vendor /data /opt"

#/*-----------------------SC2-K5.4 STB--------------------------------------*/
do_install_append_sc2-5.4 () {
    mkdir -p ${D}/vendor/lib
    mkdir -p ${D}/data
    mkdir -p ${D}/opt
    echo fdd00000.dwc2_a > ${D}/etc/adb_udc_file
    ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/vendor/lib/libdolbyms12.so
}
FILES_${PN}_append_sc2-5.4 = " /vendor/* /data /opt "
dirs755_append_sc2-5.4 = " /vendor /data /opt"

INSANE_SKIP_${PN} = "dev-so"
