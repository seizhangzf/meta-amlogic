DESCRIPTION = "Amlogic DTV Demod"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-amlogic/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"
SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/dtvdemod.git;protocol=${AML_GIT_PROTOCOL};branch=master;"
SRCREV = "${AUTOREV}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${WORKDIR}/git"
DTV_DEMOD_BIN = "${S}/firmware/dtvdemod_t2.bin"

do_install() {
	FIRMWAREDIR=${D}/lib/firmware/dtvdemod/
	mkdir -p ${FIRMWAREDIR}

	install -m 0666 ${DTV_DEMOD_BIN} ${FIRMWAREDIR}
}

FILES_${PN} = " /lib/firmware/dtvdemod/dtvdemod_t2.bin "
