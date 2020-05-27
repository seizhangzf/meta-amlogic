inherit module

SUMMARY = "Arm Mali450 driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://t83x/kernel/license.txt;md5=13e14ae1bd7ad5bff731bba4a31bb510"

include gpu.inc

SRCREV = "1848b13f54bddebd646c35307db669cd0052db48"

do_configure[noexec] = "1"

PROVIDES += "virtual/gpu"
RPROVIDES_${PN} += "gpu"
GPU_ARCH = "utgard"

do_install() {
    GPUDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/arm/gpu
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${GPUDIR}
    install -m 0666 ${S}/${GPU_ARCH}/${PV}/mali.ko ${GPUDIR}
}
FILES_${PN} = "mali.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"
EXTRA_OEMAKE='-C ${S}/${GPU_ARCH}/ KDIR=${STAGING_KERNEL_DIR} GPU_DRV_VERSION=${PV}'
