DESCRIPTION = "optee and tee-supplicant"
LICENSE = "Closed"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRCREV = "2b4fb5c1dcef812b296194d8362d6c891b05cb34"
SRC_URI = "git://git.myamlogic.com/vendor/amlogic/tdk.git;nobranch=1"
SRC_URI += "file://tee-supplicant.service"
SRC_URI += "file://0001-For-compilation-with-arm-rdk-linux-gnueabi-gcc.patch"

MIRRORS_prepend += "git://git.myamlogic.com/vendor/amlogic/tdk.git git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh; \n"

do_configure[noexec] = "1"
do_populate_lic[noexec] = "1"

DEPENDS += " coreutils-native python-native python-pycrypto-native"
PROVIDES = "optee-userspace-demos"
export PYTHONPATH="${STAGING_DIR_NATIVE}/usr/lib/python2.7/site-packages/"
PACKAGES =+ "\
    ${PN}-demos \
"

S = "${WORKDIR}/git"

do_compile_prepend() {
   unset LDFLAGS
}

do_install() {
    mkdir -p ${D}${bindir}
    install -m 0755 ${S}/ca_export_arm/bin/tee-supplicant ${D}${bindir}

    mkdir -p ${D}${libdir}
    install -m 0755 ${S}/ca_export_arm/lib/libteec.so ${D}${libdir}/libteec.so.1.0

    ln -s libteec.so.1 ${D}${libdir}/libteec.so
    ln -s libteec.so.1.0 ${D}${libdir}/libteec.so.1

    # systemd service file
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_unitdir}/system/

    # optee-demos
    install -m 0755 ${S}/demos/hello_world/out/ca/tee_helloworld ${D}${bindir}
    mkdir -p ${D}/lib/teetz/
    install -m 0755 ${S}/demos/hello_world/out/ta/8aaaf200-2450-11e4-abe2-0002a5d5c51b.ta ${D}/lib/teetz/

}

FILES_${PN} += " ${libdir}/libteec.so.1.0 \
                ${libdir}/libteec.so.1"

FILES_${PN} += "${bindir}/tee-supplicant"

FILES_${PN}-dev += "${libdir}/libteec.so"

INSANE_SKIP_${PN} = "ldflags dev-so"

FILES_${PN} += "${systemd_unitdir}/system/tee-supplicant.service"
SYSTEMD_SERVICE_${PN} = "tee-supplicant.service"
inherit systemd

# optee-demos
EXTRA_OEMAKE='-C ${S}/demos/hello_world TA_CROSS_COMPILE=${TARGET_PREFIX} CROSS_COMPILE=${TARGET_PREFIX}'
FILES_${PN}-demos += "${bindir}/tee_helloworld \
                      /lib/teetz/8aaaf200-2450-11e4-abe2-0002a5d5c51b.ta"
RDEPENDS_${PN}-demos += "${PN}"
