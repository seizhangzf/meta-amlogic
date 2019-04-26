FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG += "wayland gbm"
# rewrite PACKAGECONFIG for wayland and gbm
PACKAGECONFIG[wayland] = "-Dwaffle_has_wayland=1,-Dwaffle_has_wayland=0,virtual/egl wayland"
PACKAGECONFIG[gbm] = "-Dwaffle_has_gbm=1,-Dwaffle_has_gbm=0,virtual/egl udev"


