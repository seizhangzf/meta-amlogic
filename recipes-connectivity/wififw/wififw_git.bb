SUMMARY = "Wifi firmware"
SECTION = "base"
LICENSE = "GPLv2"

SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/amlogic/wifi.git;protocol=${AML_GIT_PROTOCOL};branch=n-amlogic"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/hardware/aml-4.9/amlogic/wifi')}"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

inherit pkgconfig

do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

PACKAGES =+ "${PN}-ap6181 \
             ${PN}-ap6210 \
             ${PN}-ap6476 \
             ${PN}-ap6493 \
             ${PN}-ap6398 \
             ${PN}-ap6330 \
             ${PN}-ath10k \
             ${PN}-bcm40181 \
             ${PN}-bcm40183 \
             ${PN}-ap62x2 \
             ${PN}-ap6335 \
             ${PN}-ap6234 \
             ${PN}-ap6441 \
             ${PN}-ap6212 \
             ${PN}-ap6256 \
             ${PN}-bcm4354 \
             ${PN}-bcm4356 \
             ${PN}-bcm43458 \
             ${PN}-qca6174\
            "

do_install() {
	mkdir -p ${D}${sysconfdir}/bluetooth/
	mkdir -p ${D}${sysconfdir}/wifi/6181/
#ap6181
	install -D -m 0644 ${S}/bcm_ampak/config/AP6181/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6181/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6181/Wi-Fi/nvram_ap6181.txt ${D}${sysconfdir}/wifi/6181/nvram.txt
#ap6210
	mkdir -p ${D}${sysconfdir}/wifi/6210/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6210/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6210/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6210/Wi-Fi/nvram_ap6210.txt ${D}${sysconfdir}/wifi/6210/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/AP6210/BT/bcm20710a1.hcd ${D}${sysconfdir}/bluetooth/
#ap6476
	mkdir -p ${D}${sysconfdir}/wifi/6476/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6476/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6476/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6476/Wi-Fi/nvram_ap6476.txt ${D}${sysconfdir}/wifi/6476/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/AP6476/GPS/bcm2076b1.hcd ${D}${sysconfdir}/bluetooth/
#ap6493
	mkdir -p ${D}${sysconfdir}/wifi/6493/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6493/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6493/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6493/Wi-Fi/nvram_ap6493.txt ${D}${sysconfdir}/wifi/6493/nvram.txt
#ap6398
	mkdir -p ${D}${sysconfdir}/wifi/6398/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6398/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6398/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6398/Wi-Fi/nvram_ap6398s.txt ${D}${sysconfdir}/wifi/6398/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/AP6398/BT/BCM4359C0SR2.hcd ${D}/etc/bluetooth/;
#ap6330
	mkdir -p ${D}${sysconfdir}/wifi/6330/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6330/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6330/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6330/Wi-Fi/nvram_ap6330.txt ${D}${sysconfdir}/wifi/6330/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/AP6330/BT/bcm40183b2.hcd ${D}${sysconfdir}/bluetooth/
#ap6256
	mkdir -p ${D}${sysconfdir}/wifi/6256/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6256/Wi-Fi/*.bin ${D}${sysconfdir}/wifi/6256/
	install -D -m 0644 ${S}/bcm_ampak/config/AP6256/Wi-Fi/nvram_ap6256.txt ${D}${sysconfdir}/wifi/6256/nvram.txt

#ath10k
	mkdir -p ${D}/lib/firmware/ath10k/QCA6174/hw3.0/
	mkdir -p ${D}/lib/firmware/ath10k/QCA9888/hw2.0/
	install -D -m 0644 ${S}/qcom/config/ath10k/QCA6174/hw3.0/*.bin ${D}/lib/firmware/ath10k/QCA6174/hw3.0/
	install -D -m 0644 ${S}/qcom/config/ath10k/QCA9888/hw2.0/*.bin ${D}/lib/firmware/ath10k/QCA9888/hw2.0/
#bcm40181
	mkdir -p ${D}${sysconfdir}/wifi/40181/
	install -D -m 0644 ${S}/bcm_ampak/config/40181/*.bin ${D}${sysconfdir}/wifi/40181/
	install -D -m 0644 ${S}/bcm_ampak/config/40181/nvram.txt ${D}${sysconfdir}/wifi/40181/nvram.txt
#bcm40183
	mkdir -p ${D}${sysconfdir}/wifi/40183/
	install -D -m 0644 ${S}/bcm_ampak/config/40183/*.bin ${D}${sysconfdir}/wifi/40183/
	install -D -m 0644 ${S}/bcm_ampak/config/40183/nvram.txt ${D}${sysconfdir}/wifi/40183/nvram.txt
#ap62x2
	mkdir -p ${D}${sysconfdir}/wifi/62x2/
	install -D -m 0644 ${S}/bcm_ampak/config/62x2/*.bin ${D}${sysconfdir}/wifi/62x2/
	install -D -m 0644 ${S}/bcm_ampak/config/62x2/nvram.txt ${D}${sysconfdir}/wifi/62x2/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/62x2/BT/bcm43241b4.hcd ${D}${sysconfdir}/bluetooth/
#ap6335
	mkdir -p ${D}${sysconfdir}/wifi/6335/
	install -D -m 0644 ${S}/bcm_ampak/config/6335/*.bin ${D}${sysconfdir}/wifi/6335/
	install -D -m 0644 ${S}/bcm_ampak/config/6335/nvram.txt ${D}${sysconfdir}/wifi/6335/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/6335/BT/bcm4335c0.hcd ${D}${sysconfdir}/bluetooth/
#ap6234
	mkdir -p ${D}${sysconfdir}/wifi/6234/
	install -D -m 0644 ${S}/bcm_ampak/config/6234/*.bin ${D}${sysconfdir}/wifi/6234/
	install -D -m 0644 ${S}/bcm_ampak/config/6234/nvram.txt ${D}${sysconfdir}/wifi/6234/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/6234/BT/bcm43341b0.hcd ${D}${sysconfdir}/bluetooth/
#ap6441
	mkdir -p ${D}${sysconfdir}/wifi/6441/
	install -D -m 0644 ${S}/bcm_ampak/config/6441/*.bin ${D}${sysconfdir}/wifi/6441/
	install -D -m 0644 ${S}/bcm_ampak/config/6441/nvram.txt ${D}${sysconfdir}/wifi/6441/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/6441/BT/bcm43341b0.hcd ${D}${sysconfdir}/bluetooth/
#ap6212
	mkdir -p ${D}${sysconfdir}/wifi/6212/
	install -D -m 0644 ${S}/bcm_ampak/config/6212/*.bin ${D}${sysconfdir}/wifi/6212/
	install -D -m 0644 ${S}/bcm_ampak/config/6212/nvram.txt ${D}${sysconfdir}/wifi/6212/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/6212/BT/bcm43438a0.hcd ${D}${sysconfdir}/bluetooth/
#bcm4354
	mkdir -p ${D}${sysconfdir}/wifi/4354/
	install -D -m 0644 ${S}/bcm_ampak/config/4354/*.bin ${D}${sysconfdir}/wifi/4354/
	install -D -m 0644 ${S}/bcm_ampak/config/4354/nvram*.txt ${D}${sysconfdir}/wifi/4354/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/4354/bcm4354a1.hcd ${D}${sysconfdir}/bluetooth/
#bcm4356
	mkdir -p ${D}${sysconfdir}/wifi/4356/
	install -D -m 0644 ${S}/bcm_ampak/config/4356/*.bin ${D}${sysconfdir}/wifi/4356/
	install -D -m 0644 ${S}/bcm_ampak/config/4356/nvram*.txt ${D}${sysconfdir}/wifi/4356/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/4356/bcm4356a2.hcd ${D}${sysconfdir}/bluetooth/
#bcm43458
	mkdir -p ${D}${sysconfdir}/wifi/43458/
	install -D -m 0644 ${S}/bcm_ampak/config/43458/*.bin ${D}${sysconfdir}/wifi/43458/
	install -D -m 0644 ${S}/bcm_ampak/config/43458/nvram*.txt ${D}${sysconfdir}/wifi/43458/nvram.txt
	install -D -m 0644 ${S}/bcm_ampak/config/43458/BCM4345C0.hcd ${D}${sysconfdir}/bluetooth/

#qca6174
	mkdir -p ${D}${sysconfdir}/wifi/qca6174/wlan/
	mkdir -p ${D}${sysconfdir}/bluetooth/qca6174/
	mkdir -p ${D}${base_libdir}/firmware/qca_bt/
	install -D -m 0644 ${S}/qcom/config/qca6174/wifi/*.bin ${D}${sysconfdir}/wifi/qca6174/
	install -D -m 0644 ${S}/qcom/config/qca6174/wifi/wlan/* ${D}${sysconfdir}/wifi/qca6174/wlan/
	install -D -m 0644 ${S}/qcom/config/qca6174/bt_bluez/* ${D}${sysconfdir}/bluetooth/qca6174/
	install -D -m 0644 ${S}/qcom/config/qca6174/bt_bluez/* ${D}${base_libdir}/firmware/qca_bt/
}

FILES_${PN}-ap6181 = "\
                ${sysconfdir}/wifi/6181/*"\

FILES_${PN}-ap6210 = "\
                ${sysconfdir}/wifi/6210/* \
                ${sysconfdir}/bluetooth/bcm20710a1.hcd"

FILES_${PN}-ap6476 = "\
                ${sysconfdir}/wifi/6476/* \
                ${sysconfdir}/bluetooth/bcm2076b1.hcd"

FILES_${PN}-ap6493 = "\
                ${sysconfdir}/wifi/6493/*"

FILES_${PN}-ap6256 = "\
                ${sysconfdir}/wifi/6256/*"

FILES_${PN}-ap6398 = "\
                ${sysconfdir}/wifi/6398/* \
                ${sysconfdir}/bluetooth/BCM4359C0SR2.hcd"

FILES_${PN}-ap6330 = "\
                ${sysconfdir}/wifi/6330/* \
                ${sysconfdir}/bluetooth/bcm40183b2.hcd"

FILES_${PN}-ath10k = "\
                lib/firmware/ath10k/QCA6174/hw3.0/*.bin \
                lib/firmware/ath10k/QCA9888/hw2.0/*.bin"

FILES_${PN}-bcm40181 = "\
                ${sysconfdir}/wifi/40181/*"

FILES_${PN}-bcm40183 = "\
                ${sysconfdir}/wifi/40183/*"

FILES_${PN}-ap62x2 = "\
                ${sysconfdir}/wifi/62x2/* \
                ${sysconfdir}/bluetooth/bcm43241b4.hcd"

FILES_${PN}-ap6335 = "\
                ${sysconfdir}/wifi/6335/* \
                ${sysconfdir}/bluetooth/bcm4335c0.hcd"

FILES_${PN}-ap6234 = "\
                ${sysconfdir}/wifi/6234/* \
                ${sysconfdir}/bluetooth/bcm43341b0.hcd"

FILES_${PN}-ap6441 = "\
                ${sysconfdir}/wifi/6441/* \
                ${sysconfdir}/bluetooth/bcm43341b0.hcd"

FILES_${PN}-ap6212 = "\
                ${sysconfdir}/wifi/6212/*\
                ${sysconfdir}/bluetooth/bcm43438a0.hcd"

FILES_${PN}-bcm4354 = "\
               ${sysconfdir}/wifi/4354/* \
               ${sysconfdir}/bluetooth/bcm4354a1.hcd"

FILES_${PN}-bcm4356 = " \
                ${sysconfdir}/wifi/4356/* \
                ${sysconfdir}/bluetooth/bcm4356a2.hcd"

FILES_${PN}-bcm43458 = " \
                ${sysconfdir}/bluetooth/BCM4345C0.hcd \
                ${sysconfdir}/wifi/43458/*"

FILES_${PN}-qca6174= " \
                ${sysconfdir}/bluetooth/qca6174/* \
                ${base_libdir}/firmware/qca_bt/* \
                ${sysconfdir}/wifi/qca6174/*"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"
