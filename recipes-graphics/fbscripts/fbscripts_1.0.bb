SUMMARY  = "scripts for fb"
DESCRIPTION = "Some scripts for configuring amlogic fb."
LICENSE  = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263" 
SRC_URI  = "\
  file://fbscript \
  file://get_hdmi_mode.awk \
  file://set_display_mode.sh \
  file://COPYING \
"

inherit update-rc.d
INITSCRIPT_NAME = "fbscript"
INITSCRIPT_PARAMS = "start 12 2 3 4 5 . stop 60 0 1 6 ."
#do_populate_lic[noexec] = "1"

S = "${WORKDIR}"
do_install() {
    install -d ${D}${sysconfdir}/init.d/
	sed -e 's,/etc/,${sysconfdir}/,g' \
		-e 's,/sbin/,${sbindir}/,g' \
		${WORKDIR}/fbscript > ${D}${sysconfdir}/init.d/fbscript
    install -m 0755 ${WORKDIR}/fbscript ${D}${sysconfdir}/init.d/fbscript
    install -d ${D}${sysconfdir}/display
    install -m 0755 ${WORKDIR}/get_hdmi_mode.awk ${D}${sysconfdir}/display
    install -m 0755 ${WORKDIR}/set_display_mode.sh ${D}${sysconfdir}/display
}

RDEPENDS_${PN} += "gawk"
