DEPENDS += "xz-native bc-native aml-hosttools-native"
DEPENDS_append_aarch64 = " libgcc"
KERNEL_CC_append_aarch64 = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD_append_aarch64 = " ${TOOLCHAIN_OPTIONS}"
#KERNEL_IMAGE_SYMLINK_NAME = "Image-${MACHINE}"

############from linux-dtb.inc
do_compile_append() {
        for DTB in ${KERNEL_DEVICETREE}; do
                 if echo ${DTB} | grep -q '/dts/'; then
                                bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
                                DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
                  fi
                  echo "${DTB}"
              oe_runmake ${DTB}
        done
}
