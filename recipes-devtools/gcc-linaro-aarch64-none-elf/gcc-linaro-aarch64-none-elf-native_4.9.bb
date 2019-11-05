DESCRIPTION = "Baremetal GCC for AARCH64"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://share/doc/gcc-linaro-aarch64-none-elf/html/binutils.html/GNU-Free-Documentation-License.html;md5=d50d69c5437545d207a4a3bbb7af5711"
inherit native
export GCC_LINARO_AARCH64_NONE_TOOLCHAIN      = "/gcc-linaro-aarch64-none-elf"
COMPATIBLE_HOST = "(i.86|x86_64).*-linux"
INHIBIT_SYSROOT_STRIP = "1"

SRC_URI="https://releases.linaro.org/archive/14.09/components/toolchain/binaries/gcc-linaro-aarch64-none-elf-4.9-2014.09_linux.tar.bz2;name=gcc-linaro-aarch64-none-elf-4.9-2014.09_linux"
SRC_URI[gcc-linaro-aarch64-none-elf-4.9-2014.09_linux.md5sum] = "90d8cfb601fa3d81a9ff629327b07a9c"
SRC_URI[gcc-linaro-aarch64-none-elf-4.9-2014.09_linux.sha256sum] = "ca96e224a8f5d022b18ed512a4e08b62a6f230b799fa96d75b8607f1e09e36ef"

S = "${WORKDIR}/gcc-linaro-aarch64-none-elf-4.9-2014.09_linux"

do_install() {
    install -d ${D}${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_NONE_TOOLCHAIN}
    cp -r ${S}/. ${D}${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_NONE_TOOLCHAIN}
}

FILES_${PN} = "${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_NONE_TOOLCHAIN}/*"
SYSROOT_DIRS_NATIVE += "${STAGING_DIR_NATIVE}${GCC_LINARO_AARCH64_NONE_TOOLCHAIN}/"


