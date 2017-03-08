SUMMARY = "amlogic gstreamer plugin"
LICENSE = "LGPL-2.0+"
ASSUME_SHLIBS = "libamplayer.so:libplayer libamadec.so:libplayer  libamstreaming.so:libplayer libamavutils.so:libplayer libamadec.so:libplayer"
DEPENDS = " libplayer libffi gstreamer1.0 gstreamer1.0-plugins-base libpcre  glib-2.0 orc  zlib "

SRC_URI = "git://git.myamlogic.com/linux/multimedia/gstreamer_plugin.git;protocol=git;nobranch=1"
MIRRORS_prepend += "git://git.myamlogic.com/linux/multimedia/gstreamer_plugin.git git://git@openlinux.amlogic.com/yocto/platform/packages/amlogic/gstreamer_plugin.git;protocol=ssh; \n"

DEFAULT_PREFERENCE = "-1"
LIC_FILES_CHKSUM = "file://COPYING;md5=e431e272f5b8a6a4f948a910812f235e"
GBANCH = "yocto-gstplugin1.x"
SRCREV="cf88dbf029ce3b9fcad32c19a8d090c0e07fe01b"
S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CROSS=${TARGET_PREFIX} TARGET_DIR=${STAGING_DIR_TARGET} STAGING_DIR=${D} DESTDIR=${D}"
inherit autotools pkgconfig distro_features_check
FILES_${PN} += "/usr/lib/gstreamer-1.0/*"
INSANE_SKIP_${PN} = "ldflags dev-so "
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1" 
