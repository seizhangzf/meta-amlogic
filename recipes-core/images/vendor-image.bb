IMAGE_FEATURES = ""
PACKAGE_INSTALL = "vendor-files"
export IMAGE_BASENAME = "vendor-image"
IMAGE_LINGUAS = ""

LICENSE = "MIT"

# don't actually generate an image, just the artifacts needed for one
IMAGE_FSTYPES = "ext4"

inherit core-image

IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"
IMAGE_PREPROCESS_COMMAND += "remove_folder;"

remove_folder() {
    rm -rf ${IMAGE_ROOTFS}/var
    rm -rf  ${IMAGE_ROOTFS}/run
    rm ${IMAGE_ROOTFS}/etc/timestamp
    rm ${IMAGE_ROOTFS}/etc/version
    rm ${IMAGE_ROOTFS}/etc/ld.so.cache
}
