SUMMARY = "amlogic gstreamer plugin"
LICENSE = "LGPL-2.0+"
ASSUME_SHLIBS = "libamplayer.so:libplayer libamadec.so:libplayer  libamstreaming.so:libplayer libamavutils.so:libplayer libamadec.so:libplayer"
DEPENDS = " libplayer libffi gstreamer1.0 gstreamer1.0-plugins-base libpcre  glib-2.0 orc  zlib "

#SRC_URI = "git://git.myamlogic.com/linux/multimedia/gstreamer_plugin.git;protocol=git;nobranch=1"
SRC_URI = "git://git@openlinux.amlogic.com/yocto/platform/packages/amlogic/gstreamer_plugin.git;protocol=ssh;nobranch=1"
SRC_URI += "file://0001-Fix-youtube-vp9-video-is-frozen-and-mosaic-by-Guo.Tao.patch"
SRC_URI += "file://0002-gst-12-null-clock-excpetion.patch"
SRC_URI += "file://0003-correct-query-video-position-for-vp9.patch"
SRC_URI += "file://0004-gst-aml-plugins1-add-TVP-support-1-1.patch"
SRC_URI += "file://0005-add-set-video-axis-scaled-for-wpe-webkit.patch"
SRC_URI += "file://0006-For-2019-MSE-conformance-test-HE-AAC-ExplicitSBR.patch"
#SRC_URI += "file://0007-Fix-Youtube-stuck-after-seek-remove-while-loop.patch"
SRC_URI += "file://0007-Fix-Youtube-stuck-after-seek.patch"
SRC_URI += "file://0008-For-2019-youtube-Progressive-Test-41-accurary.patch"

#SRC_URI += "file://fix-CodecID-redefinition.patch"

#MIRRORS_prepend += "git://git.myamlogic.com/linux/multimedia/gstreamer_plugin.git git://git@openlinux.amlogic.com/yocto/platform/packages/amlogic/gstreamer_plugin.git;protocol=ssh; \n"

DEFAULT_PREFERENCE = "-1"
LIC_FILES_CHKSUM = "file://COPYING;md5=e431e272f5b8a6a4f948a910812f235e"
GBANCH = "yocto-gstplugin1.x"
SRCREV="6c2fbebd31204a466244ca4fda676739abf15cd4"

S = "${WORKDIR}/git/gst-aml-plugins-1.2"

EXTRA_OEMAKE = "CROSS=${TARGET_PREFIX} TARGET_DIR=${STAGING_DIR_TARGET} STAGING_DIR=${D} DESTDIR=${D}"
inherit autotools pkgconfig distro_features_check
FILES_${PN} += "/usr/lib/gstreamer-1.0/*"
INSANE_SKIP_${PN} = "ldflags dev-so "
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
