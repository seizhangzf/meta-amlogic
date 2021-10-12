inherit bin_package

FILESEXTRAPATHS_prepend := "${THISDIR}/ipk:"

LICENSE = "CLOSED"

SRC_URI = "${@' '.join(['file://' + f + ';subdir=ipk' \
              for f in os.listdir(os.path.join(d.getVar('THISDIR'),'ipk')) \
              if f.endswith('.ipk')])}"

S = "${WORKDIR}/ipk"

DEPENDS += "wpeframework-interfaces wpeframework-clientlibraries wpeframework \
            gst-aml-drm-plugins gstreamer1.0 gstreamer1.0-plugins-base \
            jpeg"


FILES_${PN}-dev = "${includedir}/"
FILES_${PN} += "${libdir}/"

INSANE_SKIP_${PN} += "dev-so"

do_unpack[depends] += "xz-native:do_populate_sysroot"
