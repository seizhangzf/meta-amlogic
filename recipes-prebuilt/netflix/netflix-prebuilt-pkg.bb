inherit bin_package

FILESEXTRAPATHS_prepend := "${THISDIR}/ipk:"

LICENSE = "CLOSED"

SRC_URI = "${@' '.join(['file://' + f + ';subdir=ipk' \
              for f in os.listdir(os.path.join(d.getVar('THISDIR'),'ipk')) \
              if f.endswith('.ipk')])}"

S = "${WORKDIR}/ipk"

#This is for netflix-aml
DEPENDS += "aml-audio-service aml-netflix-esn c-ares cjson curl elfutils essos \
            expat fdk-aac freetype glib-2.0 gst-aml-drm-plugins gstreamer1.0 \
            gstreamer1.0-plugins-base harfbuzz hdmicec iarmbus iarmmgrs icu jpeg \
            lcms libdwarf libmng libnl libogg libpng libunwind libwebp nghttp2 \
            openh264 openjpeg openssl optee-userspace playready rdkservices tremor \
            util-linux westeros wpeframework wpeframework-interfaces zlib \
            "

#This is for netflix-aml
RDEPENDS_${PN} += " \
            aml-audio-service nghttp2 playready rdkservices wpeframework \
            wpeframework-interfaces aml-netflix-esn \
            "

FILES_${PN}-dev = "${includedir}/"
#Need include everything
FILES_${PN} += "/"

FILES_SOLIBSDEV = ""
SOLIBS = ".so"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

FILES_SOLIBSDEV = ""
SOLIBS = ".so"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} += "file-rdeps dev-so"

do_unpack[depends] += "xz-native:do_populate_sysroot"
