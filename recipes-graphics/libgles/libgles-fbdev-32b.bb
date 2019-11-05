DESCRIPTION = "libGLES for 32bit Mali 450 (Fbdev)"

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

SRCREV = "ff2ef217a995bba2fd9b4337f38bb0dfcf3f3ccc"
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

    # Copy the .pc files
    install -d -m 0755 ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/pkgconfig/egl.pc ${D}${libdir}/pkgconfig/
    install -m 0644 ${S}/pkgconfig/gles2.pc ${D}${libdir}/pkgconfig/glesv2.pc
    install -m 0644 ${S}/pkgconfig/eglfs.pc ${D}${libdir}/pkgconfig/

    install -d ${D}${libdir}
    install -d ${D}${includedir}


    patchelf --set-soname libMali.so ${S}/arm32/${VERSION}/libMali.so
    install -m 0755 ${S}/arm32/${VERSION}/libMali.so ${D}${libdir}/libMali.so

    ln -s libMali.so ${D}${libdir}/libEGL.so.1.4
    ln -s libEGL.so.1.4 ${D}${libdir}/libEGL.so.1
    ln -s libEGL.so.1 ${D}${libdir}/libEGL.so
    ln -s libMali.so ${D}${libdir}/libGLESv1_CM.so.1.1
    ln -s libGLESv1_CM.so.1.1 ${D}${libdir}/libGLESv1_CM.so.1
    ln -s libGLESv1_CM.so.1 ${D}${libdir}/libGLESv1_CM.so
    ln -s libMali.so ${D}${libdir}/libGLESv2.so.2.0
    ln -s libGLESv2.so.2.0 ${D}${libdir}/libGLESv2.so.2
    ln -s libGLESv2.so.2 ${D}${libdir}/libGLESv2.so
}

FILES_${PN} += "${libdir}/*.so"
FILES_${PN}-dev = "${includedir} ${libdir}/pkgconfig/*"
INSANE_SKIP_${PN} = "ldflags dev-so"
