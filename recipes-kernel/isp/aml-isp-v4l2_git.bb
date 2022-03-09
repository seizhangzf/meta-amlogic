inherit module

SUMMARY = "Amlogic ISP driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../license/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

MBRANCH = "android-p"
SRC_URI = "git://${AML_GIT_ROOT}/platform/hardware/arm/isp;protocol=${AML_GIT_PROTOCOL};branch=${MBRANCH};"

DEPENDS += " aml-isp-iq aml-isp-lens aml-isp-sensor"
#For common patches
MDIR = "isp"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/hardware/aml-5.4/amlogic/${MDIR}')}"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/vendor/amlogic/arm_isp/')}"
SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

COMPATIBLE_MACHINE="(mesontm2-5.4*|mesonsc2-5.4*|mesont5d-5.4*|mesont7-*|mesons4-*)"

do_configure[noexec] = "1"


do_install() {
    MEDIADIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/media
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    mkdir -p ${MEDIADIR}
    find ${S}/ -name *.ko | xargs -i install -m 0666 {} ${MEDIADIR}
}


# Header file provided by a separate package
DEPENDS += ""

S = "${WORKDIR}/git/linux_54_t7/kernel/v4l2_dev/"
EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M="${S}" \
                EXTRA_CFLAGS="-I${S}/app -I${S}/inc -I${S}/app/control \
                -I${S}/inc/api -I${S}/inc/isp -I${S}/inc/sys \
                -I${S}/src/platform -I${S}/src/fw \
                -I${S}/src/fw_lib -I${S}/src/calibration  \
                -I${S}/src/driver/sensor -I${S}/src/driver/lens"\
                modules'

KERNEL_MODULE_AUTOLOAD += "iv009_isp"
KERNEL_MODULE_PROBECONF += "iv009_isp"
module_conf_iv009_isp = "softdep iv009_isp pre: iv009_isp_sensor"
