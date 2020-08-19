SUMMARY = "amlogic playready"
LICENSE = "CLOSED"
DEPENDS = "optee-userspace libplayer bzip2 libxml2"
RDEPENDS_${PN} = "libbz2"

SRC_URI = "git://git@openlinux.amlogic.com/yocto/platform/packages/amlogic/playready.git;protocol=ssh;nobranch=1"
SRCREV="44e7f1c15a8ab129c9b44b2e0b3f4935ac0c0658"
SRC_URI +=" file://Disabling_min_max_fun.patch"
S = "${WORKDIR}/git"

EXTRA_OEMAKE = "SYSROOT=${PKG_CONFIG_SYSROOT_DIR}"

do_compile() {
    unset CFLAGS CXXFLAGS
    oe_runmake -C src all
}

do_install () {
    mkdir -p ${D}/lib
    mkdir -p ${D}/usr/bin
    mkdir -p ${D}/usr/lib
    mkdir -p ${D}/lib/teetz
    mkdir -p ${D}/usr/include
    mkdir -p ${D}/video

    ln -s libdashpr.so.1 ${D}/usr/lib/libdashpr.so
    install -m 0755 ${S}/src/out/libdashpr.so.1 ${D}/usr/lib
    install -m 0755 ${S}/src/out/dashpr ${D}/usr/bin

    install -m 0644 prebuilt/ta/9a04f079-9840-4286-ab92e65be0885f95.ta ${D}/lib/teetz
    install -m 0755 prebuilt/libsecmempr.so.1  ${D}/lib
    install -m 0644 prebuilt/libplayready-3.0_tee.a ${D}/usr/lib
    cp -rf include ${D}/usr
    cp -r video/* ${D}/video
    install -m 0644 ${S}/include/inc/*.h ${D}${includedir}
    install -m 0644 ${S}/include/oem/common/inc/*.h ${D}${includedir}
    install -m 0644 ${S}/include/oem/ansi/inc/*.h ${D}${includedir}
    install -m 0644 ${S}/include/results/*.h ${D}${includedir}
}

FILES_${PN} += "/lib/teetz/9a04f079-9840-4286-ab92e65be0885f95.ta"
FILES_${PN} += "/video/*"

INSANE_SKIP_${PN} = "ldflags dev-so"
