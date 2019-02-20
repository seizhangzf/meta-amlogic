DESCRIPTION = "optee demos"
LICENSE = "Closed"

SRC_URI = "git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh;branch=tdk-v2.4"
SRC_URI += "file://0001-For-compilation-with-arm-rdk-linux-gnueabi-gcc.patch"
SRCREV = "de7d380fa527e929df31f75ffffa656673031403"
do_configure[noexec] = "1"
#do_compile[noexec] = "1"
do_populate_lic[noexec] = "1"

PROVIDES_${PN} += "optee-demos"
DEPENDS += "optee-tee-supplicant"

S = "${WORKDIR}/git"

do_compile_prepend() {
   unset LDFLAGS
}

do_install() {
    mkdir -p ${D}${bindir}
    install -m 0755 ${S}/demos/hello_world/out/ca/tee_helloworld ${D}${bindir}

    mkdir -p ${D}/lib/teetz/
    install -m 0755 ${S}/demos/hello_world/out/ta/8aaaf200-2450-11e4-abe2-0002a5d5c51b.ta ${D}/lib/teetz/8aaaf200-2450-11e4-abe2-0002a5d5c51b.ta
}

EXTRA_OEMAKE='-C ${S}/demos/hello_world TA_CROSS_COMPILE=${TARGET_PREFIX} CROSS_COMPILE=${TARGET_PREFIX}'

FILES_${PN} += "${bindir}/* /lib/teetz/*.ta"
INSANE_SKIP_${PN} = "ldflags"

