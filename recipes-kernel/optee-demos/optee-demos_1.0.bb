DESCRIPTION = "optee demos"
LICENSE = "Closed"

SRC_URI = "git://git@openlinux.amlogic.com/yocto/optee-tdk;protocol=ssh;branch=tdk-v2.4"
SRCREV = "8fe3d0ce9a57dcd35b6feb35d4df79a4ab425d0c"

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

EXTRA_OEMAKE='-C ${S}/demos/'

FILES_${PN} += "${bindir}/* /lib/teetz/*.ta"
INSANE_SKIP_${PN} = "ldflags"

