SUMMARY = "amlogic gstreamer plugin"
LICENSE = "LGPL-2.0+"
DEPENDS = " libplayer libffi gstreamer1.0 gstreamer1.0-plugins-base libpcre  glib-2.0 orc  zlib "

#SRC_URI = "git://git.myamlogic.com/linux/multimedia/gstreamer_plugin.git;protocol=git;nobranch=1"
SRC_URI = "git://git@openlinux.amlogic.com/yocto/platform/packages/amlogic/gstreamer_plugin.git;protocol=ssh;nobranch=1"
SRC_URI += "file://fix-CodecID-redefinition.patch"

#MIRRORS_prepend += "git://git.myamlogic.com/linux/multimedia/gstreamer_plugin.git git://git@openlinux.amlogic.com/yocto/platform/packages/amlogic/gstreamer_plugin.git;protocol=ssh; \n"

DEFAULT_PREFERENCE = "-1"
LIC_FILES_CHKSUM = "file://COPYING;md5=e431e272f5b8a6a4f948a910812f235e"
SRCREV="a57f2302ac76c0cdecd8922d8b0b0696f985fd14"

S = "${WORKDIR}/git/gst-aml-plugins-1.2"

EXTRA_OEMAKE = "CROSS=${TARGET_PREFIX} TARGET_DIR=${STAGING_DIR_TARGET} STAGING_DIR=${D} DESTDIR=${D}"
inherit autotools pkgconfig distro_features_check
FILES_${PN} += "/usr/lib/gstreamer-1.0/*"
INSANE_SKIP_${PN} = "ldflags dev-so "
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1" 
