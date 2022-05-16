SUMMARY = "Meson init script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

SRC_URI = "git://${AML_GIT_ROOT}${AML_GIT_ROOT_YOCTO_SUFFIX}/rdk/prebuilt/vendor;protocol=${AML_GIT_PROTOCOL};branch=master"
SRC_URI_append = " ${@get_patch_list_with_path('${COREBASE}/../aml-patches/prebuilt/vendor')}"
SRCREV ?= "${AUTOREV}"
PV = "${SRCPV}"
PR = "r0"

SOC = "Amlogic"
SOC_u212 = "s905x2"
SOC_s905x2 = "s905x2"
SOC_ab301 = "t962x3"
SOC_t962x3 = "t962x3"
SOC_ab311 = "t962e2"
SOC_t962e2 = "t962e2"
SOC_am301 = "t950d4"
SOC_t950d4 = "t950d4"
SOC_aq222 = "s805x2"
SOC_at301 = "t962d4"
SOC_ar321 = "t965d4"
SOC_ap222 = "s905y4"
SOC_ap229 = "s905y4"
SOC_ap223 = "s905y4"
SOC_ah212 = "s905x4"

S = "${WORKDIR}/git/"

do_install() {
        if [ -d ${S}/etc/tvconfig/${SOC} ]; then
			install -d ${D}/etc/tvconfig/pq
			install -d ${D}/lib/modules
			if [ -e ${S}/etc/tvconfig/${SOC}/tvconfig ]; then
				cd ${S}/etc/tvconfig/${SOC}/tvconfig
				for file in $(find -type f); do
					install -m 0644 -D ${file} ${D}/etc/tvconfig/${file}
				done
			fi
			install -m 0644 -D ${S}/etc/tvconfig/${SOC}/PQ/pq.db ${D}/etc/tvconfig/pq/pq.db
			install -m 0644 -D ${S}/etc/tvconfig/${SOC}/PQ/pq_default.ini ${D}/etc/tvconfig/pq/pq_default.ini
			if [ -e ${S}/etc/tvconfig/${SOC}/PQ/overscan.db ]; then
				install -m 0644 -D ${S}/etc/tvconfig/${SOC}/PQ/overscan.db ${D}/etc/tvconfig/pq/overscan.db
			fi
        fi
        ###install -d ${D}/lib
        ###ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${D}/lib/libdolbyms12.so
        if [ -d ${S}/logo_files/${SOC} ]; then
			install -d ${D}/logo_files
			install -m 0644 -D ${S}/logo_files/${SOC}/bootup.bmp ${D}/logo_files/bootup.bmp
        fi
        if [ -d ${S}/recovery_img/${SOC} ]; then
			install -m 0644 -D ${S}/recovery_img/${SOC}/rdk-recovery.img ${DEPLOY_DIR_IMAGE}/recovery.img
        fi
}

FILES_${PN} = " /etc/tvconfig/* /lib/* logo_files/* /etc/cas/irdeto/*"
FILES_${PN}-dev = " "
PACKAGE_ARCH = "${MACHINE_ARCH}"
INSANE_SKIP_${PN} = "dev-so dev-elf already-stripped"
