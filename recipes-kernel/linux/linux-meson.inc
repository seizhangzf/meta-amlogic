inherit kernel_meson
require recipes-kernel/linux/linux-yocto.inc
require recipes-kernel/linux/linux-yocto_amlogic.inc

do_copy_dtb() {
	cp -f ${B}/arch/${ARCH}/boot/dts/amlogic/*.dtb ${B}/arch/${ARCH}/boot/
}

addtask copy_dtb before do_install after do_compile

do_deploy_append () {
    DTB_PATH=`get_real_dtb_path_in_kernel ${KERNEL_DEVICETREE}`
    install -m 0644 ${DTB_PATH} ${DEPLOY_DIR_IMAGE}/dtb.img
}
