SUMMARY = "amlogic playready"
LICENSE = "CLOSED"
DEPENDS = "optee-userspace bzip2 libxml2 aml-secmem aml-mediahal-sdk"
RDEPENDS_${PN} = "libbz2"

FILESEXTRAPATHS_preppend := "${THISDIR}/files/:"
SRC_URI = "git://${AML_GIT_ROOT_PR}/vendor/playready.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=linux-3.x-amlogic"
#SRC_URI += " file://0001-playready-add-headers-for-build-1-1.patch;patchdir=${WORKDIR}/git"

#For common patches
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/multimedia/libmediadrm/playready-bin')}"

#SRCREV="bb62070629f62c580b32cdfe2cfaa3928611d6f3"
#use head version, ?= conditonal operator can be control revision in external rdk-next.conf like configuration file
SRCREV ?= "${AUTOREV}"

S = "${WORKDIR}/git"

PATCHTOOL="git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"
#do_compile () {
#}
do_install () {
	mkdir -p ${D}/usr/bin
	mkdir -p ${D}/usr/lib/pkgconfig
	mkdir -p ${D}/lib/teetz
	mkdir -p ${D}/usr/include/playready

    install -m 0644 ${S}/prebuilt-v4.4/noarch/ta/${TDK_VERSION}/*.ta ${D}/lib/teetz
    install -m 0644 ${S}/prebuilt-v4.4/noarch/pkgconfig/playready.pc ${D}/usr/lib/pkgconfig
    install -m 0644 ${S}/prebuilt-v4.4/arm.aapcs-linux.hard/libplayready-4.4.so ${D}/usr/lib
    install -m 0755 ${S}/prebuilt-v4.4/arm.aapcs-linux.hard/prtest ${D}/usr/bin
    ln -s libplayready-4.4.so ${D}/usr/lib/libplayready.so
    ln -s libplayready-4.4.so ${D}/usr/lib/libplayready44pk.so
    ln -s libplayready-4.4.so ${D}/usr/lib/libplayready44pritee.so
    cp -rf ${S}/prebuilt-v4.4/noarch/include/* ${D}/usr/include/playready/
    ln -s playready ${D}/usr/include/playready4.4
}

FILES_${PN} += "/lib/teetz/*"
FILES_${PN} += "/video/*"
FILES_${PN} += "${libdir}/*"

FILES_SOLIBSDEV = ""

INSANE_SKIP_${PN} = "ldflags dev-so"
