DESCRIPTION = "brcm tools: brcm_patchram_plus"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = "file://brcm_patchram_plus.c"

S = "${WORKDIR}"

do_compile() {
    ${CC} brcm_patchram_plus.c ${LDFLAGS} -o brcm_patchram_plus

}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 brcm_patchram_plus ${D}${bindir}
}

FILES_${PN} += "${bindir}/brcm_patchram_plus"