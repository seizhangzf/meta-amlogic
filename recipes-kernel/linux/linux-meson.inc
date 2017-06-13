inherit kernel_meson
require recipes-kernel/linux/linux-yocto.inc
require recipes-kernel/linux/linux-yocto_amlogic.inc

do_copy_dtb() {
	cp -f ${B}/arch/${ARCH}/boot/dts/amlogic/*.dtb ${B}/arch/${ARCH}/boot/
}

do_install_mkbootimg() {
	install -m 0755 ${FILE_DIRNAME}/linux-meson/mkbootimg ${STAGING_BINDIR_NATIVE}/
}

addtask copy_dtb before do_install after do_compile
