SUMMARY = "amlogic playready prebuilt"
LICENSE = "CLOSED"
DEPENDS = "optee-userspace libplayer bzip2 libxml2"
RDEPENDS_${PN} = "libbz2"

SRC_URI = "git://git@openlinux.amlogic.com/yocto/platform/packages/amlogic/playready.git;protocol=ssh;nobranch=1"
SRCREV="04be80d9187136add2b358def3f130972ee4aa69"

S = "${WORKDIR}/git"

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
	mkdir -p ${D}/usr/include
	mkdir -p ${D}/video

    install -m 0644 prebuilt/ta/9a04f079-9840-4286-ab92e65be0885f95.ta ${D}/lib/teetz
    install -m 0755 prebuilt/libsecmempr.so.1  ${D}/lib
    install -m 0644 prebuilt/libplayready-3.0_tee.a ${D}/usr/lib
    cp -rf include ${D}/usr
    install -m 0755 prebuilt/dashpr/libdashpr.so.1  ${D}/usr/lib
    install -m 0755 prebuilt/dashpr/dashpr  ${D}/usr/bin
    cp -r video/* ${D}/video
}

FILES_${PN} += "/lib/teetz/9a04f079-9840-4286-ab92e65be0885f95.ta"
FILES_${PN} += "/video/*"

INSANE_SKIP_${PN} = "ldflags dev-so"
