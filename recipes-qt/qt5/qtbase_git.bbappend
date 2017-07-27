FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"
# patches from Buildroot to fix mali compilation
SRC_URI += "\
    file://0007-eglfs-fix-eglfs_mali-compile-for-odroid-mali.patch \
    file://0001-force-disable-ARM-crc32.patch \
"
PACKAGECONFIG += " \
                  eglfs \
"

DEPENDS += "virtual/libgles1 virtual/libgles2 virtual/egl"

ALLOW_EMPTY_${PN}-examples = "1"
ALLOW_EMPTY_${PN}-fonts = "1"
PACKAGES += "${PN}-fonts"
