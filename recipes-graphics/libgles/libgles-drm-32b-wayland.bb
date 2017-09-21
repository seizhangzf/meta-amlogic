DESCRIPTION = "libGLES with wayland for 32bit Mali 450 (Drm)"

LICENSE = "Proprietary"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

# These libraries shouldn't get installed in world builds unless something
# explicitly depends upon them.
EXCLUDE_FROM_WORLD = "1"
PROVIDES = "virtual/libgles1 virtual/libgles2 virtual/egl"
RPROVIDES_${PN} += "libGLESv2.so libEGL.so libGLESv1_CM.so libMali.so"

# Add wayland
RPROVIDES_${PN} += "libwayland-egl.so"

SRCREV = "eb5e0f9d406bf7196af290884e23a04319139b61"
SRC_URI = "git://git.myamlogic.com/linux/amlogic/mali-linux.git;nobranch=1"
VERSION = "r7p0"
MIRRORS_prepend += "git://git.myamlogic.com/linux/amlogic/mali-linux.git git://git@openlinux.amlogic.com/yocto/platform/hardware/arm/mali-linux.git;protocol=ssh; \n"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

do_install() {
    # install headers
    install -d -m 0755 ${D}${includedir}/EGL
    install -m 0755 ${S}/include/EGL/*.h ${D}${includedir}/EGL/
    install -d -m 0755 ${D}${includedir}/GLES
    install -m 0755 ${S}/include/GLES/*.h ${D}${includedir}/GLES/
    install -d -m 0755 ${D}${includedir}/GLES2
    install -m 0755 ${S}/include/GLES2/*.h ${D}${includedir}/GLES2/
    install -d -m 0755 ${D}${includedir}/KHR
    install -m 0755 ${S}/include/KHR/*.h ${D}${includedir}/KHR/
    # wayland headers
    install -m 0755 ${S}/include/EGL_platform/platform_wayland/*.h ${D}${includedir}/EGL

    # Copy the .pc files
    install -d -m 0755 ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/pkgconfig/egl.pc ${D}${libdir}/pkgconfig/
    install -m 0644 ${S}/pkgconfig/gles2.pc ${D}${libdir}/pkgconfig/glesv2.pc
    install -m 0644 ${S}/pkgconfig/eglfs.pc ${D}${libdir}/pkgconfig/
    # wayland .pc
    install -m 0644 ${S}/pkgconfig/wayland-egl.pc ${D}${libdir}/pkgconfig/

    install -d ${D}${libdir}
    install -d ${D}${includedir}

    # waylnad lib
    install -m 0755 ${S}/arm32/${VERSION}/wayland-drm/libMali.so ${D}${libdir}/libMali.so

    ln -s libMali.so ${D}${libdir}/libEGL.so.1.4
    ln -s libEGL.so.1.4 ${D}${libdir}/libEGL.so.1
    ln -s libEGL.so.1 ${D}${libdir}/libEGL.so

    ln -s libMali.so ${D}${libdir}/libGLESv1_CM.so.1.1
    ln -s libGLESv1_CM.so.1.1 ${D}${libdir}/libGLESv1_CM.so.1
    ln -s libGLESv1_CM.so.1 ${D}${libdir}/libGLESv1_CM.so

    ln -s libMali.so ${D}${libdir}/libGLESv2.so.2.0
    ln -s libGLESv2.so.2.0 ${D}${libdir}/libGLESv2.so.2
    ln -s libGLESv2.so.2 ${D}${libdir}/libGLESv2.so

    ln -s libMali.so ${D}${libdir}/libwayland-egl.so.0
    ln -s libwayland-egl.so.0 ${D}${libdir}/libwayland-egl.so

    ln -s libMali.so ${D}${libdir}/libgbm.so.1
    ln -s libgbm.so.1 ${D}${libdir}/libgbm.so
}

FILES_${PN} += "${libdir}/*.so"
FILES_${PN}-dev = "${includedir} ${libdir}/pkgconfig/*"
INSANE_SKIP_${PN} = "ldflags dev-so"
