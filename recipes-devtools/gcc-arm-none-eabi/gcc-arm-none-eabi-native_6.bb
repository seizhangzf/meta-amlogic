DESCRIPTION = "Baremetal GCC for arm cortex m3"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://share/doc/gcc-arm-none-eabi/readme.txt;md5=1ec836d03cf50a286d3d62e97b0edb3c"
inherit native
export GCC_ARM_NONE_TOOLCHAIN      = "/gcc-arm-none-elf"
COMPATIBLE_HOST = "(x86_64).*-linux"
INHIBIT_SYSROOT_STRIP = "1"

SRC_URI="https://developer.arm.com/-/media/Files/downloads/gnu-rm/6-2017q2/gcc-arm-none-eabi-6-2017-q2-update-linux.tar.bz2?revision=2cc92fb5-3e0e-402d-9197-bdfc8224d8a5?product=GNU%20Arm%20Embedded%20Toolchain,64-bit,,Linux,6-2017-q2-update;downloadfilename=gcc-arm-none-eabi-6-2017-q2-update-linux.tar.bz2;name=gcc-arm-none-eabi-6-2017-q2_linux"
SRC_URI[gcc-arm-none-eabi-6-2017-q2_linux.md5sum] = "13747255194398ee08b3ba42e40e9465"
SRC_URI[gcc-arm-none-eabi-6-2017-q2_linux.sha256sum] = "e68e4b2fe348ecb567c27985355dff75b65319a0f6595d44a18a8c5e05887cc3"

S = "${WORKDIR}/gcc-arm-none-eabi-6-2017-q2-update/"

do_install() {
    install -d ${D}${STAGING_DIR_NATIVE}${GCC_ARM_NONE_TOOLCHAIN}
    cp -r ${S}/. ${D}${STAGING_DIR_NATIVE}${GCC_ARM_NONE_TOOLCHAIN}
}

FILES_${PN} = "${STAGING_DIR_NATIVE}${GCC_ARM_NONE_TOOLCHAIN}/*"
SYSROOT_DIRS_NATIVE += "${STAGING_DIR_NATIVE}${GCC_ARM_NONE_TOOLCHAIN}/"


