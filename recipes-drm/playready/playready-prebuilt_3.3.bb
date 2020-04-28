SUMMARY = "amlogic playready prebuilt"
LICENSE = "CLOSED"
DEPENDS = "optee-userspace bzip2 libxml2"
RDEPENDS_${PN} = "libbz2"

SRC_URI = "git://${AML_GIT_ROOT_PR}/vendor/playready.git;protocol=${AML_GIT_PROTOCOL};branch=linux-3.x-amlogic"
#SRCREV="bb62070629f62c580b32cdfe2cfaa3928611d6f3"
#use head version, ?= conditonal operator can be control revision in external rdk-next.conf like configuration file
SRCREV ?= "${AUTOREV}"

S = "${WORKDIR}/git/prebuilt-v3.3"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"
#do_compile () {
#}
do_install () {
	mkdir -p ${D}/lib
	mkdir -p ${D}/usr/bin
	mkdir -p ${D}/usr/lib
	mkdir -p ${D}/lib/teetz
	mkdir -p ${D}/usr/include/playready
	mkdir -p ${D}/video

    install -m 0644 noarch/ta/9a04f079-9840-4286-ab92e65be0885f95.ta ${D}/lib/teetz
    install -m 0644 arm.aapcs-linux.hard/libplayready.so.3.3 ${D}/usr/lib
    cp -rf noarch/include/* ${D}/usr/include/playready/
}

FILES_${PN} += "/lib/teetz/9a04f079-9840-4286-ab92e65be0885f95.ta"
FILES_${PN} += "/video/*"

INSANE_SKIP_${PN} = "ldflags dev-so"
