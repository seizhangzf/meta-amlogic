inherit bin_package

FILESEXTRAPATHS_prepend := "${THISDIR}/ipk:"

LICENSE = "CLOSED"

SRC_URI = "${@' '.join(['file://' + f + ';subdir=ipk' \
              for f in os.listdir(os.path.join(d.getVar('THISDIR'),'ipk')) \
              if f.endswith('.ipk')])}"

S = "${WORKDIR}/ipk"

DEPENDS += "aml-audio-service curl expat freetype icu jpeg \
            gstreamer1.0 gstreamer1.0-plugins-base gst-aml-drm-plugins \
            westeros libmng libnl libogg libpng libunwind \
            libwebp nghttp2 openjpeg openssl tremor zlib \
            wpeframework wpeframework-interfaces aml-netflix-esn"

RDEPENDS_${PN} += "nghttp2 aml-netflix-esn"

FILES_${PN}-dev = "${includedir}/"
FILES_${PN} += "${libdir}/"

INSANE_SKIP_${PN} += "file-rdeps dev-so"

do_unpack[depends] += "xz-native:do_populate_sysroot"
