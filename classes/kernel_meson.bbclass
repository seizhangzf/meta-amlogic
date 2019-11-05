inherit kernel
#inherit more-new

kernel_do_compile() {
	echo "inside kernel_meson"
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
	# The $use_alternate_initrd is only set from
	# do_bundle_initramfs() This variable is specifically for the
	# case where we are making a second pass at the kernel
	# compilation and we want to force the kernel build to use a
	# different initramfs image.  The way to do that in the kernel
	# is to specify:
	# make ...args... CONFIG_INITRAMFS_SOURCE=some_other_initramfs.cpio
	if [ "$use_alternate_initrd" = "" ] && [ "${INITRAMFS_TASK}" != "" ] ; then
		# The old style way of copying an prebuilt image and building it
		# is turned on via INTIRAMFS_TASK != ""
		copy_initramfs
		use_alternate_initrd=CONFIG_INITRAMFS_SOURCE=${B}/usr/${INITRAMFS_IMAGE}-${MACHINE}.cpio
	fi
         for typeformake in ${KERNEL_IMAGETYPE_FOR_MAKE} ; do
                oe_runmake ${typeformake} CC="${KERNEL_CC}" LD="${KERNEL_LD}" ${KERNEL_EXTRA_ARGS} $use_alternate_initrd
                for type in ${KERNEL_IMAGETYPES} ; do
                                if test "${typeformake}.gz" = "${type}"; then
                                cp "${KERNEL_OUTPUT_DIR}/${typeformake}" .
                                gzip -9c < "${typeformake}" > "${KERNEL_OUTPUT_DIR}/${type}"
                                break;
                                fi
                done
        done
	cd ${KERNEL_OUTPUT_DIR}
	gzip -9c Image> Image.gz
	cd -
}
