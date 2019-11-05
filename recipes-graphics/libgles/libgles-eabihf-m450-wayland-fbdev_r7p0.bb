DESCRIPTION = "libGLES with wayland for 32bit Mali 450 (Fbdev)"

LICENSE = "Proprietary"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

# These libraries shouldn't get installed in world builds unless something
# explicitly depends upon them.
EXCLUDE_FROM_WORLD = "1"
PROVIDES = "virtual/libgles1 virtual/libgles2 virtual/egl"
RPROVIDES_${PN} += "libGLESv2.so libEGL.so libGLESv1_CM.so libMali.so"
DEPENDS += "patchelf-native"

# Add wayland
RPROVIDES_${PN} += "libwayland-egl.so"

SRCREV = "4ee50d25ba1dfc340f23e312fb07f42f64eb05e3"
SRC_URI = "git://git@openlinux.amlogic.com/yocto/platform/hardware/arm/mali-linux.git;protocol=ssh;branch=r6p1-RDK"

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
    install -m 0644 ${S}/lib/pkgconfig/common/*.pc ${D}${libdir}/pkgconfig/

    install -d ${D}${libdir}
    install -d ${D}${includedir}

    patchelf --set-soname libMali.so ${S}/lib/eabihf/m450/${PV}/wayland/fbdev/libMali.so
    # wayland lib
    install -m 0755 ${S}/lib/eabihf/m450/${PV}/wayland/fbdev/libMali.so ${D}${libdir}/

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
}

FILES_${PN} += "${libdir}/*.so"
FILES_${PN}-dev = "${includedir} ${libdir}/pkgconfig/*"
INSANE_SKIP_${PN} = "ldflags dev-so"
