inherit module

SUMMARY = "Arm Mali driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://t83x/kernel/license.txt;md5=13e14ae1bd7ad5bff731bba4a31bb510"

SRC_URI = "git://git.myamlogic.com/platform/hardware/arm/gpu.git;nobranch=1"

SRCREV = "1848b13f54bddebd646c35307db669cd0052db48"

MIRRORS_prepend += "git://git.myamlogic.com/platform/hardware/arm/gpu.git git://git@openlinux.amlogic.com/yocto/platform/hardware/arm/gpu.git;protocol=ssh; \n"

do_configure[noexec] = "1"

do_install() {
    GPUDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/arm/gpu
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${GPUDIR}
    install -m 0666 ${S}/utgard/r7p0/mali.ko ${GPUDIR}
}
FILES_${PN} = "mali.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"
EXTRA_OEMAKE='-C ${S}/utgard/ KDIR=${STAGING_KERNEL_DIR} GPU_DRV_VERSION=r7p0'
