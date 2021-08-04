DESCRIPTION = "Android Verified Boot 2.0"
HOMEPAGE = "https://android.googlesource.com/platform/external/avb/+/master/README.md"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b8228f2369d92593f53f0a0685ebd3c0"


S = "${WORKDIR}/git"
SRCREV ?= "${AUTOREV}"
SRC_URI = "git://android.googlesource.com/platform/external/avb;protocol=https;branch=master"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/avbtool ${D}${bindir}
}


BBCLASSEXTEND = "native"
