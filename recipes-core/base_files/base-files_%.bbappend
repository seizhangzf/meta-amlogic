do_install_append_tm2 () {
mkdir -p ${D}/vendor
mkdir -p ${D}/data


cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/vendor            /vendor                    auto       defaults              0  0
EOF

}

FILES_${PN}_append_tm2 = " /vendor/* "
dirs755_append_tm2 = " /vendor "


do_install_append_g12a () {

mkdir -p ${D}/vendor/lib
mkdir -p ${D}/data
mkdir -p ${D}/opt

ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/vendor/lib/libdolbyms12.so

}
FILES_${PN}_append_g12a = " /vendor/* /data /opt "
dirs755_append_g12a = " /vendor /data /opt"
INSANE_SKIP_${PN} = "dev-so"


do_install_append_sc2 () {

mkdir -p ${D}/vendor/lib
mkdir -p ${D}/data
mkdir -p ${D}/opt

ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/vendor/lib/libdolbyms12.so

}
FILES_${PN}_append_sc2 = " /vendor/* /data /opt "
dirs755_append_sc2 = " /vendor /data /opt"
