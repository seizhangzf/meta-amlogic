DESCRIPTION = "Baremetal GCC for AARCH64"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://share/doc/binutils.html/GNU-Free-Documentation-License.html;md5=c03da3e7ebbe44843e96b24182bb0f60"
inherit native
export GCC_LINARO_AARCH64_TOOLCHAIN      = "/gcc-linaro-aarch64-elf"
COMPATIBLE_HOST = "(i.86|x86_64).*-linux"
INHIBIT_SYSROOT_STRIP = "1"

SRC_URI="https://releases.linaro.org/components/toolchain/binaries/7.3-2018.05/aarch64-elf/gcc-linaro-7.3.1-2018.05-i686_aarch64-elf.tar.xz;name=gcc-linaro-7.3.1-2018.05-i686_aarch64-elf"

SRC_URI[gcc-linaro-7.3.1-2018.05-i686_aarch64-elf.md5sum] = "d81b02482cc0c7db82511506e7909010"
SRC_URI[gcc-linaro-7.3.1-2018.05-i686_aarch64-elf.sha256sum] = "214a40c93a8641609853efb05ddc2e3e7ebd9c76ec7f3a45cd980c39b2f69d1b"

S = "${WORKDIR}/gcc-linaro-7.3.1-2018.05-i686_aarch64-elf"

do_install() {
    install -d ${D}${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_TOOLCHAIN}
    cp -r ${S}/. ${D}${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_TOOLCHAIN}
}

FILES_${PN} = "${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_TOOLCHAIN}/*"
SYSROOT_DIRS_NATIVE += "${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_TOOLCHAIN}/"


