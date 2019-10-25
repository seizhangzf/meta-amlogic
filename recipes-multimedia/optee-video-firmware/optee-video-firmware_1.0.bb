SUMMARY = "amlogic optee video firmware"
LICENSE = "CLOSED"
DEPENDS = "libplayer bzip2 libxml2"
RDEPENDS_${PN} = "libbz2 optee-userspace"

BRANCH = "tdk-v2.4-video-firmware"
SRC_URI = "git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh;branch=${BRANCH}"
SRC_URI += "file://videoFirmwarePreload.service"
SRCREV ?= "${AUTOREV}"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"
#do_compile () {
#}
do_install () {
	#mkdir -p ${D}/lib
	mkdir -p ${D}/usr/bin
	#mkdir -p ${D}/usr/lib
	mkdir -p ${D}/lib/teetz
	#mkdir -p ${D}/usr/include
	#mkdir -p ${D}/video

    install -m 0644 video_firmware/ta/526fc4fc-7ee6-4a12-96e3-83da9565bce8.ta ${D}/lib/teetz
    #install -m 0644 video_firmware/lib/tee_preload_fw.so  ${D}/usr/lib
    #install -m 0644 prebuilt/libplayready-3.0_tee.a ${D}/usr/lib
    #cp -rf include ${D}/usr
    #install -m 0755 prebuilt/dashpr/libdashpr.so.1  ${D}/usr/lib
    install -m 0755 video_firmware/ca/tee_preload_fw  ${D}/usr/bin

    # systemd service file
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/videoFirmwarePreload.service ${D}${systemd_unitdir}/system/
    #cp -r video/* ${D}/video
}

FILES_${PN} += "/lib/teetz/526fc4fc-7ee6-4a12-96e3-83da9565bce8.ta"
INSANE_SKIP_${PN} = "ldflags"

FILES_${PN} += "${systemd_unitdir}/system/videoFirmwarePreload.service"
SYSTEMD_SERVICE_${PN} = "videoFirmwarePreload.service"
inherit systemd
