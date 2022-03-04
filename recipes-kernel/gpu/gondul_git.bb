inherit module

SUMMARY = "Arm G53(gondul) graphic driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://t83x/kernel/license.txt;md5=13e14ae1bd7ad5bff731bba4a31bb510"

include gpu.inc

SRCREV = "4cf054626175aa511f9c765205f8b141ae169f70"

PROVIDES += "virtual/gpu"
RPROVIDES_${PN} += "gpu"
GPU_ARCH = "bifrost"
GPU_DRV_SRC = "${S}/${GPU_ARCH}/${PV}/kernel/drivers/gpu/arm/midgard"
GPU_LOW_MEM ?= "0"

do_configure[noexec] = "1"

do_install() {
    GPUDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/arm/gpu
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${GPUDIR}
    install -m 0666 ${GPU_DRV_SRC}/mali_kbase.ko ${GPUDIR}/mali.ko
}

FILES_${PN} = "mali.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"
EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M=${GPU_DRV_SRC} \
              EXTRA_CFLAGS="-DCONFIG_MALI_PLATFORM_DEVICETREE -DCONFIG_MALI_MIDGARD_DVFS -DCONFIG_MALI_BACKEND=gpu -DCONFIG_MALI_GATOR_SUPPORT=y -DCONFIG_MALI_LOW_MEM=${GPU_LOW_MEM}" \
              CONFIG_MALI_MIDGARD=m CONFIG_MALI_PLATFORM_DEVICETREE=y CONFIG_MALI_MIDGARD_DVFS=y CONFIG_MALI_BACKEND=gpu CONFIG_MALI_GATOR_SUPPORT=y modules'

