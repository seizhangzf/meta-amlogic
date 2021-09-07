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
    mkdir -p ${D}/factory
    mkdir -p ${D}/data
    # if dm-verity is enabled, mount /dev/mapper/vendor(/dev/dm-1) as ro
    if ${@bb.utils.contains('DISTRO_FEATURES', 'dm-verity', 'true', 'false', d)}; then
        cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/dm-1            /vendor                    ext4       ro              0  0
 /dev/factory         /factory                   auto       defaults        0  0
EOF
    else
        cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/vendor            /vendor                    auto       defaults              0  0
 /dev/factory           /factory                   auto       defaults              0  0
EOF
    fi
}
FILES_${PN}_append_t5d = " /vendor/* /factory/* "
dirs755_append_t5d = " /vendor /factory "

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
    mkdir -p ${D}/factory
    mkdir -p ${D}/data
    mkdir -p ${D}/opt
    ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/vendor/lib/libdolbyms12.so
    # if dm-verity is enabled, mount /dev/mapper/vendor(/dev/dm-1) as ro
    if ${@bb.utils.contains('DISTRO_FEATURES', 'dm-verity', 'true', 'false', d)}; then
        cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/dm-1            /vendor                    ext4       ro              0  0
 /dev/factory         /factory                   auto       defaults        0  0
EOF
    else
        cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/vendor            /vendor                    auto       defaults              0  0
 /dev/factory           /factory                   auto       defaults              0  0
EOF
    fi
}
FILES_${PN}_append_s4 = " /vendor/* /data /opt /factory/* "
dirs755_append_s4 = " /vendor /data /opt /factory "

#/*-----------------------SC2 STB--------------------------------------*/
do_install_append_sc2 () {
    mkdir -p ${D}/vendor/lib
    mkdir -p ${D}/factory
    mkdir -p ${D}/data
    mkdir -p ${D}/opt
    ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/vendor/lib/libdolbyms12.so
    # if dm-verity is enabled, mount /dev/mapper/vendor(/dev/dm-1) as ro
    if ${@bb.utils.contains('DISTRO_FEATURES', 'dm-verity', 'true', 'false', d)}; then
        cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/dm-1            /vendor                    ext4       ro              0  0
 /dev/factory         /factory                   auto       defaults        0  0
EOF
    else
        cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/vendor            /vendor                    auto       defaults              0  0
 /dev/factory           /factory                   auto       defaults              0  0
EOF
    fi
}
FILES_${PN}_append_sc2 = " /vendor/* /data /opt /factory/* "
dirs755_append_sc2 = " /vendor /data /opt /factory "

INSANE_SKIP_${PN} = "dev-so"
