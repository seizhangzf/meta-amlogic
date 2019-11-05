include brcm-bsa.inc

INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-dev += "dev-elf ldflags"

S = "${WORKDIR}/git/3rdparty/embedded/bsa_examples/linux"
SRC_URI += "file://bsaserver.service"
SRC_URI += "file://bsa.sh"

AML_BRCM_BSA_APP = "app_manager app_3d app_ag app_av app_avk app_ble \
                    app_ble_ancs app_ble_blp app_ble_cscc app_ble_eddystone app_ble_hrc \
                    app_ble_htp app_ble_pm app_ble_rscc app_ble_tvselect app_ble_wifi app_ble_wifi_setup\
                    app_cce app_dg app_fm app_ftc app_fts app_hd app_headless \
                    app_hh app_hl app_hs app_nsa app_opc app_ops app_pan \
                    app_pbc app_pbs app_sac app_sc app_switch app_tm app_socket app_audio_source "

do_compile() {
   for app in ${AML_BRCM_BSA_APP}; do
        make  -C ${S}/$app/build CPU=arm ARMGCC="${CC}" BSASHAREDLIB=TRUE VERBOSE=1;
    done
}

do_install() {
    install -m 0755 -d ${D}/${bindir}
    install -m 0755 -d ${D}/${libdir}
    install -D -m 755 ${WORKDIR}/git/server/arm/bsa_server ${D}/${bindir}
    install -D -m 755 ${S}/libbsa/build/arm/sharedlib/libbsa.so ${D}/${libdir}/libbsa.so
    for app in ${AML_BRCM_BSA_APP}; do
        install -D -m 755 ${S}/$app/build/arm/$app ${D}/${bindir}
    done
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/bsaserver.service ${D}${systemd_unitdir}/system/
    install -m 0755 ${WORKDIR}/bsa.sh ${D}${bindir}

}
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES_${PN} += "${bindir}/*"
FILES_${PN} += "${libdir}/libbsa.so"
FILES_${PN} += "${systemd_unitdir}/system/bsaserver.service"
#SYSTEMD_SERVICE_${PN} = "bsaserver.service"
#inherit systemd
