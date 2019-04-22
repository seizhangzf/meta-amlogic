SUMMARY = "apitrace for amlogic platform"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=aeb969185a143c3c25130bc2c3ef9a50"
DEPENDS = "zlib libpng procps"

SRC_URI = "git://github.com/apitrace/apitrace.git;protocol=git;branch=master"
SRCREV = "26966134f15d28f6b4a9a0a560017b3ba36d60bf"

SRC_URI += "file://0001-apitrace-Porting-to-amlogic-platform.patch"

S = "${WORKDIR}/git"

inherit cmake pythonnative

EXTRA_OECMAKE += "-DENABLE_X11=OFF \
                  -DCMAKE_SYSROOT=${STAGING_DIR_TARGET} \
                  -DCMAKE_BUILD_TYPE=Release"

FILES_${PN} += "${bindir} ${libdir}"
