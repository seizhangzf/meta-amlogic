DESCRIPTION = "dsp-util for RTOS"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

#SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/dsp_util;protocol=${AML_GIT_PROTOCOL};branch=projects/amlogic-dev;"

#For common patches
#SRC_URI_append = " ${@get_patch_list_with_path('${AML_PATCH_PATH}/vendor/amlogic/rtos/dsp_util')}"

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"
PR = "r0"
S = "${WORKDIR}/git/"

DEPENDS += " alsa-lib"

do_compile() {
  ${MAKE} -C ${S}
}

do_install() {
  install -d  ${D}/${bindir}
  install -d  ${D}/${libdir}
  install -m 0755 ${S}/hifi4_media_tool      ${D}/${bindir}/
  install -m 0755 ${S}/hifi4_rpc_test        ${D}/${bindir}/
  install -m 0755 ${S}/hifi4rpc_client_test  ${D}/${bindir}/
  install -m 0755 ${S}/dsp_util              ${D}/${bindir}/

  install -m 0644 ${S}/libhifi4rpc.so        ${D}/${libdir}/
  install -m 0644 ${S}/libhifi4rpc_client.so ${D}/${libdir}/
  install -m 0644 ${S}/libmp3tools.so        ${D}/${libdir}/
}

FILES_${PN} = "${libdir}/* ${bindir}/*"
FILES_${PN}-dev = "${includedir}/* "

INSANE_SKIP_${PN}-dev = "dev-so"
