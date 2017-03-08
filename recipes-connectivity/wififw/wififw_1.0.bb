SUMMARY = "Wifi firmware"
SECTION = "base"
LICENSE = "GPLv2"

SRC_URI = "git://git.myamlogic.com/platform/hardware/amlogic/wifi.git;protocol=git;nobranch=1"

SRCREV = "43a4cd66d2b721c18874af43e4f92b615c7f8007"

MIRRORS_prepend += "git://git.myamlogic.com/platform/hardware/amlogic/wifi.git git://git@openlinux.amlogic.com/yocto/platform/hardware/amlogic/wifi.git;protocol=ssh; \n"

inherit pkgconfig

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

PACKAGES =+ "${PN}-ap6181 \
             ${PN}-ap6210 \
             ${PN}-ap6476 \
             ${PN}-ap6493 \
             ${PN}-ap6330 \
             ${PN}-bcm40181 \
             ${PN}-bcm40183 \
             ${PN}-ap62x2 \
             ${PN}-ap6335 \
             ${PN}-ap6234 \
             ${PN}-ap6441 \
             ${PN}-ap6212 \
             ${PN}-bcm4354 \
             ${PN}-bcm4356 \
             ${PN}-bcm43458 \
            "

do_install() {
	mkdir -p ${D}${sysconfdir}/wifi/6181/
#ap6181
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6181/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6181/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6181/Wi-Fi/nvram_ap6181.txt ${D}${sysconfdir}/wifi/6181/nvram.txt
#ap6210
	mkdir -p ${D}${sysconfdir}/wifi/6210/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6210/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6210/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6210/Wi-Fi/nvram_ap6210.txt ${D}${sysconfdir}/wifi/6210/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6210/BT/*.hcd ${D}${sysconfdir}/wifi/
#ap6476
	mkdir -p ${D}${sysconfdir}/wifi/6476/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6476/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6476/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6476/Wi-Fi/nvram_ap6476.txt ${D}${sysconfdir}/wifi/6476/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6476/GPS/*.hcd ${D}${sysconfdir}/wifi/6476/
#ap6493
	mkdir -p ${D}${sysconfdir}/wifi/6493/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6493/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6493/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6493/Wi-Fi/nvram_ap6493.txt ${D}${sysconfdir}/wifi/6493/nvram.txt
#ap6330
	mkdir -p ${D}${sysconfdir}/wifi/6330/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6330/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6330/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6330/Wi-Fi/nvram_ap6330.txt ${D}${sysconfdir}/wifi/6330/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/AP6330/BT/*.hcd ${D}${sysconfdir}/wifi/6330/
#bcm40181
	mkdir -p ${D}${sysconfdir}/wifi/40181/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/40181/*.bin ${D}${sysconfdir}/wifi/40181/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/40181/nvram.txt ${D}${sysconfdir}/wifi/40181/nvram.txt
#bcm40183
	mkdir -p ${D}${sysconfdir}/wifi/40183/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/40183/*.bin ${D}${sysconfdir}/wifi/40183/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/40183/nvram.txt ${D}${sysconfdir}/wifi/40183/nvram.txt
#ap62x2
	mkdir -p ${D}${sysconfdir}/wifi/62x2/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/62x2/*.bin ${D}${sysconfdir}/wifi/62x2/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/62x2/nvram.txt ${D}${sysconfdir}/wifi/62x2/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/62x2/BT/*.hcd ${D}${sysconfdir}/wifi/62x2/
#ap6335
	mkdir -p ${D}${sysconfdir}/wifi/6335/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6335/*.bin ${D}${sysconfdir}/wifi/6335/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6335/nvram.txt ${D}${sysconfdir}/wifi/6335/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6335/BT/*.hcd ${D}${sysconfdir}/wifi/6335/
#ap6234
	mkdir -p ${D}${sysconfdir}/wifi/6234/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6234/*.bin ${D}${sysconfdir}/wifi/6234/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6234/nvram.txt ${D}${sysconfdir}/wifi/6234/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6234/BT/*.hcd ${D}${sysconfdir}/wifi/6234/
#ap6441
	mkdir -p ${D}${sysconfdir}/wifi/6441/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6441/*.bin ${D}${sysconfdir}/wifi/6441/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6441/nvram.txt ${D}${sysconfdir}/wifi/6441/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6441/BT/*.hcd ${D}${sysconfdir}/wifi/6441/
#ap6212
	mkdir -p ${D}${sysconfdir}/wifi/6212/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6212/*.bin ${D}${sysconfdir}/wifi/6212/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6212/nvram.txt ${D}${sysconfdir}/wifi/6212/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/6212/BT/*.hcd ${D}${sysconfdir}/wifi/6212/
#bcm4354
	mkdir -p ${D}${sysconfdir}/wifi/4354/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/4354/*.bin ${D}${sysconfdir}/wifi/4354/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/4354/nvram*.txt ${D}${sysconfdir}/wifi/4354/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/4354/*.hcd ${D}${sysconfdir}/wifi/4354/
#bcm4356
	mkdir -p ${D}${sysconfdir}/wifi/4356/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/4356/*.bin ${D}${sysconfdir}/wifi/4356/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/4356/nvram*.txt ${D}${sysconfdir}/wifi/4356/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/4356/*.hcd ${D}${sysconfdir}/wifi/4356/
#bcm4358 
	mkdir -p ${D}${sysconfdir}/wifi/4358/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/43458/*.bin ${D}${sysconfdir}/wifi/4358/
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/43458/nvram*.txt ${D}${sysconfdir}/wifi/4358/nvram.txt
	install -D -m 0644 ${WORKDIR}/git/bcm_ampak/config/43458/*.hcd ${D}${sysconfdir}/wifi/4358/
}

FILES_${PN}-ap6181 = "\
                ${sysconfdir}/6181/fw_bcm40181a2_apsta.bin \
                ${sysconfdir}/6181/fw_bcm40181a2.bin \
                ${sysconfdir}/6181/fw_bcm40181a2_p2p.bin \
                ${sysconfdir}/6181/nvram_ap6181.txt"

FILES_${PN}-ap6210 = "\
                ${sysconfdir}/6210/fw_bcm40181a2_apsta.bin \
                ${sysconfdir}/6210/fw_bcm40181a2.bin \
                ${sysconfdir}/6210/fw_bcm40181a2_p2p.bin \
                ${sysconfdir}/6210/nvram.txt \
                ${sysconfdir}/6210/bcm20710a1.hcd"

FILES_${PN}-ap6476 = "\
                ${sysconfdir}/6476/fw_bcm40181a2_apsta.bin \
                ${sysconfdir}/6476/fw_bcm40181a2.bin \
                ${sysconfdir}/6476/fw_bcm40181a2_p2p.bin \
                ${sysconfdir}/6476/nvram.txt \
                ${sysconfdir}/6476/bcm2076b1.hcd"

FILES_${PN}-ap6493 = "\
                ${sysconfdir}/6493/fw_bcm40183b2_apsta.bin \
                ${sysconfdir}/6493/fw_bcm40183b2.bin \
                ${sysconfdir}/6493/fw_bcm40183b2_p2p.bin \
                ${sysconfdir}/6493/nvram.txt \
                ${sysconfdir}/6493/bcm40183b2.hcd"

FILES_${PN}-ap6330 = "\
                ${sysconfdir}/6330/fw_bcm40183b2_apsta.bin \
                ${sysconfdir}/6330/fw_bcm40183b2.bin \
                ${sysconfdir}/6330/fw_bcm40183b2_p2p.bin \
                ${sysconfdir}/6330/nvram.txt \
                ${sysconfdir}/6330/bcm40183b2.hcd"

FILES_${PN}-bcm40181 = "\
                ${sysconfdir}/40181/fw_bcm40181a0_apsta.bin \
                ${sysconfdir}/40181/fw_bcm40181a2_apsta.bin \
                ${sysconfdir}/40181/fw_bcm40181a2_p2p.bin \
                ${sysconfdir}/40181/fw_bcm40181a0.bin \
                ${sysconfdir}/40181/fw_bcm40181a2.bin \
                ${sysconfdir}/40181/nvram.txt"

FILES_${PN}-bcm40183 = "\
                ${sysconfdir}/40183/fw_bcm40183b2_apsta.bin \
                ${sysconfdir}/40183/fw_bcm40183b2.bin \
                ${sysconfdir}/40183/fw_bcm40183b2_p2p.bin \
                ${sysconfdir}/40183/nvram.txt"

FILES_${PN}-ap62x2 = "\
                ${sysconfdir}/wifi/62x2/fw_bcm43241b4_ag_apsta.bin \
                ${sysconfdir}/wifi/62x2/fw_bcm43241b4_ag.bin \
                ${sysconfdir}/wifi/62x2/fw_bcm43241b4_ag_p2p.bin \
                ${sysconfdir}/wifi/62x2/nvram.txt \
                ${sysconfdir}/wifi/62x2/bcm43241b4.hcd"

FILES_${PN}-ap6335 = "\
                ${sysconfdir}/wifi/6335/fw_bcm4339a0_ag.bin \
                ${sysconfdir}/wifi/6335/fw_bcm4339a0e_ag_apsta.bin  \
                ${sysconfdir}/wifi/6335/fw_bcm4339a0e_ag_p2p.bin \
                ${sysconfdir}/wifi/6335/nvram.txt \
                ${sysconfdir}/wifi/6335/fw_bcm4339a0_ag_apsta.bin \
                ${sysconfdir}/wifi/6335/fw_bcm4339a0_ag_p2p.bin \
                ${sysconfdir}/wifi/6335/fw_bcm4339a0e_ag.bin \
                ${sysconfdir}/wifi/6335/nvram_ap6335e.txt \
                ${sysconfdir}/wifi/6335/bcm4335c0.hcd"

FILES_${PN}-ap6234 = "\
                ${sysconfdir}/wifi/6234/fw_bcm43341b0_ag_apsta.bin \
                ${sysconfdir}/wifi/6234/fw_bcm43341b0_ag.bin \
                ${sysconfdir}/wifi/6234/fw_bcm43341b0_ag_p2p.bin \
                ${sysconfdir}/wifi/6234/nvram.txt \
                ${sysconfdir}/wifi/6234/bcm43341b0.hcd"

FILES_${PN}-ap6441 = "\
                ${sysconfdir}/wifi/6441/fw_bcm43341b0_ag_apsta.bin \
                ${sysconfdir}/wifi/6441/fw_bcm43341b0_ag.bin \
                ${sysconfdir}/wifi/6441/fw_bcm43341b0_ag_p2p.bin \
                ${sysconfdir}/wifi/6441/nvram.txt \
                ${sysconfdir}/wifi/6441/bcm43341b0.hcd"

FILES_${PN}-ap6212 = "\
                ${sysconfdir}/wifi/6212/fw_bcm43438a0_apsta.bin \
                ${sysconfdir}/wifi/6212/fw_bcm43438a0.bin \
                ${sysconfdir}/wifi/6212/fw_bcm43438a0_p2p.bin \
                ${sysconfdir}/wifi/6212/nvram.txt \
                ${sysconfdir}/wifi/6212/bcm43438a0.hcd"

FILES_${PN}-bcm4354 = "\
               ${sysconfdir}/wifi/4354/bcm4354a1.hcd \
               ${sysconfdir}/wifi/4354/fw_bcm4354a1_ag_apsta.bin \
               ${sysconfdir}/wifi/4354/fw_bcm4354a1_ag.bin \
               ${sysconfdir}/wifi/4354/fw_bcm4354a1_ag_p2p.bin \
               ${sysconfdir}/wifi/4354/nvram.txt"

FILES_${PN}-bcm4356 = " \
                ${sysconfdir}/wifi/4356/bcm4356a2.hcd \
                ${sysconfdir}/wifi/4356/fw_bcm4356a2_ag_apsta.bin \
                ${sysconfdir}/wifi/4356/fw_bcm4356a2_ag.bin \
                ${sysconfdir}/wifi/4356/fw_bcm4356a2_ag_p2p.bin \
                ${sysconfdir}/wifi/4356/nvram.txt"

FILES_${PN}-bcm43458 = " \
                ${sysconfdir}/wifi/43458/BCM4345C0.hcd \
                ${sysconfdir}/wifi/43458/fw_bcm43455c0_ag_apsta.bin \
                ${sysconfdir}/wifi/43458/fw_bcm43455c0_ag.bin \
                ${sysconfdir}/wifi/43458/fw_bcm43455c0_ag_p2p.bin \
                ${sysconfdir}/wifi/43458/nvram.txt"

# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"
