include brcm-bsa.inc
inherit module
KERNEL_MODULE_AUTOLOAD += "bthid"
do_populate_lic[noexec] = "1"
do_configure[noexec] = "1"
FILES_${PN} += "bthid.ko"

S = "${WORKDIR}/git/3rdparty/embedded/brcm/linux/bthid"

EXTRA_OEMAKE='-C ${STAGING_KERNEL_DIR} M=${S} modules'

do_install() {
    MODULE_DIR=${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/broadcom/bluetooth
    install -m 0755 -d ${MODULE_DIR}
    install -D -m 755 ${S}/bthid.ko ${MODULE_DIR}
}
