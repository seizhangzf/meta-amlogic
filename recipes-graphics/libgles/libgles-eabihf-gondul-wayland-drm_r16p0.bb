DESCRIPTION = "libGLES with wayland for 32bit Mali gondul (Drm)"

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
DEPENDS += "wayland libdrm"

# Add wayland
RPROVIDES_${PN} += "libwayland-egl.so"

SRCREV = "faba60eec6d252f60b027c9c216d0bbb473b58a9"
SRC_URI = "git://git@openlinux.amlogic.com/yocto/platform/hardware/arm/mali-linux.git;protocol=ssh;branch=r16p0-RDK"

S = "${WORKDIR}/git"
GPU_MODEL = "gondul"

inherit autotools pkgconfig

do_install() {
    # install headers
    install -d -m 0755 ${D}${includedir}/EGL
    install -m 0755 ${S}/include/EGL/*.h ${D}${includedir}/EGL/
    install -d -m 0755 ${D}${includedir}/GLES
    install -m 0755 ${S}/include/GLES/*.h ${D}${includedir}/GLES/
    install -d -m 0755 ${D}${includedir}/GLES2
    install -m 0755 ${S}/include/GLES2/*.h ${D}${includedir}/GLES2/
    install -d -m 0755 ${D}${includedir}/GLES3
    install -m 0755 ${S}/include/GLES3/*.h ${D}${includedir}/GLES3/
    install -d -m 0755 ${D}${includedir}/KHR
    install -m 0755 ${S}/include/KHR/*.h ${D}${includedir}/KHR/
    # wayland headers
    install -m 0755 ${S}/include/EGL_platform/platform_wayland/*.h ${D}${includedir}/EGL
    # gbm headers
    install -m 0755 ${S}/include/EGL_platform/platform_wayland/gbm/gbm.h ${D}${includedir}

    # Copy the .pc files
    install -d -m 0755 ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/lib/pkgconfig/common/*.pc ${D}${libdir}/pkgconfig/
    # gbm.pc
    install -m 0644 ${S}/lib/pkgconfig/common/gbm/*.pc ${D}${libdir}/pkgconfig/

    install -d ${D}${libdir}
    install -d ${D}${includedir}

    patchelf --set-soname libMali.so ${S}/lib/eabihf/${GPU_MODEL}/${PV}/wayland/drm/libMali.so 
    # wayland lib
    install -m 0755 ${S}/lib/eabihf/${GPU_MODEL}/${PV}/wayland/drm/libMali.so ${D}${libdir}/

    ln -s libMali.so ${D}${libdir}/libEGL.so.1.4.0
    ln -s libEGL.so.1.4.0 ${D}${libdir}/libEGL.so.1
    ln -s libEGL.so.1 ${D}${libdir}/libEGL.so

    ln -s libMali.so ${D}${libdir}/libGLESv1_CM.so.1.1.0
    ln -s libGLESv1_CM.so.1.1.0 ${D}${libdir}/libGLESv1_CM.so.1
    ln -s libGLESv1_CM.so.1 ${D}${libdir}/libGLESv1_CM.so

    ln -s libMali.so ${D}${libdir}/libGLESv2.so.2.1.0
    ln -s libGLESv2.so.2.1.0 ${D}${libdir}/libGLESv2.so.2
    ln -s libGLESv2.so.2 ${D}${libdir}/libGLESv2.so

    ln -s libMali.so ${D}${libdir}/libwayland-egl.so.1.0.0
    ln -s libwayland-egl.so.1.0.0 ${D}${libdir}/libwayland-egl.so.1
    ln -s libwayland-egl.so.1 ${D}${libdir}/libwayland-egl.so

    ln -s libMali.so ${D}${libdir}/libvulkan.so.1.0.0
    ln -s libvulkan.so.1.0.0 ${D}${libdir}/libvulkan.so.1
    ln -s libvulkan.so.1 ${D}${libdir}/libvulkan.so

    ln -s libMali.so ${D}${libdir}/libgbm.so.1.0.0
    ln -s libgbm.so.1.0.0 ${D}${libdir}/libgbm.so.1
    ln -s libgbm.so.1 ${D}${libdir}/libgbm.so
}

FILES_${PN} += "${libdir}/*.so"
FILES_${PN}-dev = "${includedir} ${libdir}/pkgconfig/*"
INSANE_SKIP_${PN} = "ldflags dev-so"
