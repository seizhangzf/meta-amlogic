inherit kernel_meson
require recipes-kernel/linux/linux-yocto.inc
require recipes-kernel/linux/linux-yocto_amlogic.inc

do_copy_dtb() {
	cp -f ${B}/arch/${ARCH}/boot/dts/amlogic/*.dtb ${B}/arch/${ARCH}/boot/
}

addtask copy_dtb before do_install after do_compile
#addtask install_headers after do_install before do_deploy

do_deploy_append () {
    DTB_PATH=`get_real_dtb_path_in_kernel ${KERNEL_DEVICETREE}`
    install -m 0644 ${DTB_PATH} ${DEPLOYDIR}/dtb.img
}

do_install_append () {
    oe_runmake -C ${B} CC="${KERNEL_CC}" headers_install INSTALL_HDR_PATH=${D}/usr/include/linux-meson-4.9/
}

sysroot_stage_all_append() {
    sysroot_stage_dir ${D}/usr/include/linux-meson-4.9 ${SYSROOT_DESTDIR}/usr/include/linux-meson-4.9

}
${PN}-dev += " /usr/include/linux-meson-4.9/* "
