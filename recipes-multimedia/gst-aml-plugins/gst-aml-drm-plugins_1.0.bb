SUMMARY = "amlogic gstreamer plugin"
LICENSE = "CLOSED"
DEPENDS = " gstreamer1.0 gstreamer1.0-plugins-base glib-2.0 zlib aml-mediadrm-widevine "
DEPENDS += "gstreamer1.0-plugins-bad"

SRC_URI = "git://${AML_GIT_ROOT}/linux/multimedia/gstreamer_plugin.git;protocol=${AML_GIT_PROTOCOL};branch=buildroot-gstdrmplugin1.x"
#SRC_URI += " file://0001-rdk-dev.patch;patchdir=../"
#SRC_URI += " file://0001-gst-aml-drm-disable-parse-1-1.patch;patchdir=../"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/multimedia/gst-aml-drm-plugins1', '../')}"

SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"

S = "${WORKDIR}/git/gst-aml-drm-plugins-1.0"

EXTRA_OEMAKE = "CROSS=${TARGET_PREFIX} TARGET_DIR=${STAGING_DIR_TARGET} STAGING_DIR=${D} DESTDIR=${D}"
inherit autotools pkgconfig distro_features_check
FILES_${PN} += "/usr/lib/gstreamer-1.0/*"
INSANE_SKIP_${PN} = "ldflags dev-so "
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
