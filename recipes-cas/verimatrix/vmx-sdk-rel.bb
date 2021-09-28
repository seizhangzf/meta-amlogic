DESCRIPTION = "Verimatrix SDK Release"
SECTION = "vmx-sdk-rel"
LICENSE = "CLOSE"
PV = "git${SRCPV}"
PR = "r0"

#Only enable it in OpenLinux
VMX_SDK_BRANCH_s4 = "m9y4-rel-linux"
VMX_SDK_BRANCH_sc2 = "m9x4-rel-linux"

SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'verimatrix', 'git://${AML_GIT_ROOT_OP}/vendor/vmx/sdk-rel.git;protocol=${AML_GIT_ROOT_PROTOCOL};branch=${VMX_SDK_BRANCH_s4}','', d)}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/vmx/sdk-rel')}"

#PN = 'verimatrix'
SRCREV ?= "${AUTOREV}"
S = "${WORKDIR}/git"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

#VMX SDK Version depends on SoC used
VMX_SDK_VERSION_s4 = "SDK_M9Y4_1_0_0"
VMX_SDK_VERSION_sc2 = "SDK_M9X4_1_0_0"

do_install() {
    install -d -m 0644 ${D}/usr/lib
    install -D -m 0644 ${S}/${VMX_SDK_VERSION}/libs/libvmxca_client.so ${D}/usr/lib
    install -D -m 0644 ${S}/${VMX_SDK_VERSION}/libs/libvmxca_iptvclient.so ${D}/usr/lib
    install -D -m 0644 ${S}/${VMX_SDK_VERSION}/libs/libvmxca_webclient.so ${D}/usr/lib
}

FILES_${PN} = "${libdir}/* /usr/lib/* /lib/teetz/*"
FILES_${PN}-dev = "${includedir}/* "
INSANE_SKIP_${PN} = "dev-so ldflags dev-elf"
