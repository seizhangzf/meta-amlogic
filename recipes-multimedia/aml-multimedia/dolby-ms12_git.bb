DESCRIPTION = "Dolby MS12 decryption utility"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-amlogic/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

include aml-multimedia.inc

do_configure[noexec] = "1"
do_compile[noexec] = "1"

inherit autotools pkgconfig

S = "${WORKDIR}/git/dolby_ms12_release/src"


def get_arch(tunefeatures, datastore):
    if bb.utils.contains("TUNE_FEATURES", "aarch64", True, False, datastore) or \ 
       bb.utils.contains("TUNE_FEATURES", "aarch64_be", True, False, datastore):
        return "aarch64 lp64"
    else:
        return "arm aapcs-linux"

#FLOAT_ABI = "${@d.getVar("TARGET_FPU", True)}"
FLOAT_ABI = "${@bb.utils.contains('TARGET_FPU', 'hard', 'hard', 'softfp', d)}"
ARCH = "${@get_arch("${TUNE_FEATURES}", d).split()[0]}"
CC_ABI = "${@get_arch("${TUNE_FEATURES}", d).split()[1]}"

python () {
    if d.getVar('ARCH', True) == 'aarch64':
        d.setVar('FLOAT_ABI', '')
}


do_install() {
    install -d -m 0644 ${D}${bindir}
    install -d -m 0644 ${D}${libdir}

    install -m 0755 ${S}/${ARCH}.${CC_ABI}.${FLOAT_ABI}/dolby_fw_dms12 ${D}${bindir}
    mkdir -p ${STAGING_DIR_TARGET}/vendor/lib/
    cp -P ${S}/libdolbyms12.so.symbol_link ${STAGING_DIR_TARGET}/vendor/lib/libdolbyms12.so
    install -m 0644 ${S}/libdolbyms12.so ${D}${libdir}
}

FILES_SOLIBSDEV = ""

FILES_${PN} += "${libdir}/*.so ${bindir}/*" 
#FILES_${PN}-dev = "${includedir} ${libdir}/pkgconfig/*"
INSANE_SKIP_${PN} = "ldflags dev-so dev-elf"
