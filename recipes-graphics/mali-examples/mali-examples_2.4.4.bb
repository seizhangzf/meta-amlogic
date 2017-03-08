SUMMARY = "Mali OpenGL-ES examples from ARM"
SECTION = "graphic"
LICENSE = "ARM"
LIC_FILES_CHKSUM = "file://Mali_OpenGL_ES_SDK_v2.4.4_Documentation.html;md5=561d0b7167db0f2048c175658731c52e"

SRC_HASH = "71fdbd"
SRC_URI = "http://malideveloper.arm.com/downloads/SDK/LINUX/${PV}/Mali_OpenGL_ES_SDK_v${PV}.${SRC_HASH}_Linux_x86.tar.gz"
SRC_URI[md5sum] = "441f89136e4fc1137214610aff0f7021"

SRC_URI += "file://CMakeLists.patch"

RDEPENDS_${PN} += "libMali.so"

S = "${WORKDIR}/Mali_OpenGL_ES_SDK_v2.4.4"

inherit cmake

EXTRA_OECMAKE = " \
                -DTARGET=arm \
			    -DCMAKE_INSTALL_PREFIX=/usr/share/arm \
			    -DTOOLCHAIN_ROOT=${TARGET_PREFIX}"

do_install_append() {
# rm opengl_30/opengl_31 because we haven't support it yet
    rm -rf ${D}/usr/share/arm/opengles_30
    rm -rf ${D}/usr/share/arm/opengles_31
}

FILES_${PN} = "/usr/share/arm/opengles_20/*"
