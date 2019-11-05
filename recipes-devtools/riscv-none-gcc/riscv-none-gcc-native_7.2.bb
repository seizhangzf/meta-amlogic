DESCRIPTION = "Baremetal GCC for RISCV"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://README.md;md5=5423408f57224c4b19a45e96e465d1e3"
inherit native
export GCC_RISCV_TOOLCHAIN      = "/riscv-none-gcc"
COMPATIBLE_HOST = "(x86_64).*-linux"
INHIBIT_SYSROOT_STRIP = "1"

SRC_URI="https://github.com/gnu-mcu-eclipse/riscv-none-gcc/releases/download/v7.2.0-4-20180606/gnu-mcu-eclipse-riscv-none-gcc-7.2.0-4-20180606-1631-centos64.tgz;name=riscv-none-gcc-7.2"
SRC_URI[riscv-none-gcc-7.2.md5sum] = "a3d527aa6e4cf348b6d56931a7151935"
SRC_URI[riscv-none-gcc-7.2.sha256sum] = "8f0aab1919fdc950876a9bfb4254e1887864cf0c36e31bd7b12817fe9e52b934"

S = "${WORKDIR}/gnu-mcu-eclipse/riscv-none-gcc/7.2.0-4-20180606-1631"

do_install() {
    install -d ${D}${STAGING_DIR_NATIVE}${GCC_RISCV_TOOLCHAIN}
    cp -r ${S}/. ${D}${STAGING_DIR_NATIVE}${GCC_RISCV_TOOLCHAIN}
}

FILES_${PN} = "${STAGING_DIR_NATIVE}${GCC_RISCV_TOOLCHAIN}/*"
SYSROOT_DIRS_NATIVE += "${STAGING_DIR_NATIVE}${GCC_RISCV_TOOLCHAIN}/"

