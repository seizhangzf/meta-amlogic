inherit module

SUMMARY = "Arm G31(dvalin) graphic driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://t83x/kernel/license.txt;md5=13e14ae1bd7ad5bff731bba4a31bb510"
include gpu.inc

SRCREV = "${AUTOREV}"
VER = "r25p0"
PV = "${VER}git${SRCPV}"

PROVIDES += "dma-buf-test-exporter"
DEPENDS += "virtual/gpu"

GPU_ARCH = "bifrost"
DMABUF_EXPORTER_DRV_SRC = "${S}/${GPU_ARCH}/${VER}/kernel/drivers/base/dma_buf_test_exporter"

do_configure[noexec] = "1"

do_install() {
    GPUDIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/arm/gpu
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${GPUDIR}
    install -m 0666 ${DMABUF_EXPORTER_DRV_SRC}/dma-buf-test-exporter.ko ${GPUDIR}/dma-buf-test-exporter.ko
}

FILES_${PN} = "dma-buf-test-exporter.ko"
# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git"
EXTRA_OEMAKE='KDIR=${STAGING_KERNEL_DIR} -C ${DMABUF_EXPORTER_DRV_SRC} \
              EXTRA_CFLAGS="-I${S}/${GPU_ARCH}/${VER}/kernel/include \
              -DCONFIG_MALI_PLATFORM_DEVICETREE -DCONFIG_MALI_MIDGARD_DVFS -DCONFIG_MALI_BACKEND=gpu" \
              CONFIG_MALI_MIDGARD=m CONFIG_MALI_PLATFORM_DEVICETREE=y CONFIG_MALI_MIDGARD_DVFS=y CONFIG_MALI_BACKEND=gpu'
