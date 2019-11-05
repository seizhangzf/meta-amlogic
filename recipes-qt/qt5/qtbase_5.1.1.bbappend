FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += " \
        file://Resolve_Compilation_error.patch \
        file://fix-compilation-in-new-ddk.patch \
        "

DEPENDS += "virtual/libgles1 virtual/libgles2 virtual/egl"

do_configure_prepend() {
if ! grep -q 'qeglfshooks_8726m' ${S}/src/plugins/platforms/eglfs/eglfs.pri; then
        cat >> ${S}/src/plugins/platforms/eglfs/eglfs.pri << 'EOF'
    SOURCES += $$PWD/../../../../mkspecs/devices/linux-arm-amlogic-8726M-g++/qeglfshooks_8726m.cpp
    HEADERS += $$PWD/../../../../mkspecs/devices/linux-arm-amlogic-8726M-g++/qplatformdefs.h
EOF
fi
}

