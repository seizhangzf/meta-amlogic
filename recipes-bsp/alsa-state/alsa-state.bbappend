do_install_append() {
    if test -f "${D}${sysconfdir}/asound.conf";
    then
        rm ${D}${sysconfdir}/asound.conf
    fi
}
