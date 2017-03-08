SUMMARY  = "scripts for play"
DESCRIPTION = "Some scripts for configure audio decoders and alsa conf."
LICENSE  = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263" 
SRC_URI  = "\
  file://alsactl.conf \
  file://asound.conf \
  file://property_set.sh \
  file://COPYING \
"
S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/profile.d
    install -m 0755 ${WORKDIR}/alsactl.conf ${D}${sysconfdir}/
    install -m 0755 ${WORKDIR}/asound.conf ${D}${sysconfdir}/
    install -m 0755 ${WORKDIR}/property_set.sh ${D}${sysconfdir}/profile.d/
}
