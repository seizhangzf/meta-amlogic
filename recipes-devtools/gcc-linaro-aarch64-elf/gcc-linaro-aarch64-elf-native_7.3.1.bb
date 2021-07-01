DESCRIPTION = "Baremetal GCC for AARCH64"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://share/doc/binutils.html/GNU-Free-Documentation-License.html;md5=c03da3e7ebbe44843e96b24182bb0f60"
inherit native
export GCC_LINARO_AARCH64_TOOLCHAIN      = "/gcc-linaro-aarch64-elf"
COMPATIBLE_HOST = "(x86_64).*-linux"
INHIBIT_SYSROOT_STRIP = "1"

SRC_URI="https://releases.linaro.org/components/toolchain/binaries/7.3-2018.05/aarch64-elf/gcc-linaro-7.3.1-2018.05-x86_64_aarch64-elf.tar.xz;name=gcc-linaro-7.3.1-2018.05-x86_64_aarch64-elf"

SRC_URI[gcc-linaro-7.3.1-2018.05-x86_64_aarch64-elf.md5sum] = "89b6b05c557243fb5d029f3916023121"
SRC_URI[gcc-linaro-7.3.1-2018.05-x86_64_aarch64-elf.sha256sum] = "e73d1886fb67d5a03c0ca5be14aeab6b7b7be3fce46ad1473ba864d5eb1d2545"

S = "${WORKDIR}/gcc-linaro-7.3.1-2018.05-x86_64_aarch64-elf"

do_install() {
    install -d ${D}${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_TOOLCHAIN}
    cp -r ${S}/. ${D}${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_TOOLCHAIN}
}

FILES_${PN} = "${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_TOOLCHAIN}/*"
SYSROOT_DIRS_NATIVE += "${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_TOOLCHAIN}/"


