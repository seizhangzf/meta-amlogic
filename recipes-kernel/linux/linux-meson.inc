inherit kernel_meson
require recipes-kernel/linux/linux-yocto.inc
require recipes-kernel/linux/linux-yocto_amlogic.inc

do_copy_dtb() {
	cp -f ${B}/arch/${ARCH}/boot/dts/amlogic/*.dtb ${B}/arch/${ARCH}/boot/
}

addtask copy_dtb before do_install after do_compile
