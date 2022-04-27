IMAGE_FEATURES = ""
PACKAGE_INSTALL = "vendor-files"
export IMAGE_BASENAME = "vendor-image"
IMAGE_LINGUAS = ""

LICENSE = "MIT"

# don't actually generate an image, just the artifacts needed for one
IMAGE_FSTYPES = "ext4"

inherit core-image

IMAGE_ROOTFS_SIZE = "15360"
IMAGE_ROOTFS_EXTRA_SPACE = "0"
IMAGE_PREPROCESS_COMMAND += "remove_folder;create_dolbyms12_link;"

remove_folder() {
    rm -rf ${IMAGE_ROOTFS}/var
    rm -rf  ${IMAGE_ROOTFS}/run
    rm ${IMAGE_ROOTFS}/etc/timestamp
    rm ${IMAGE_ROOTFS}/etc/version
    rm ${IMAGE_ROOTFS}/etc/ld.so.cache
}

#during system booting up, when decrypting dolby M12 library, audioserver.service need to create soft symbol link dynamically.
#because vendor partition will be changed to read-only, so we create this link here, and audioserver.service only need to create
#the link targart /tmp/ds/0x4d_0x5331_0x32.so
create_dolbyms12_link() {
    mkdir -p ${IMAGE_ROOTFS}/lib
    ln -sf /tmp/ds/0x4d_0x5331_0x32.so ${IMAGE_ROOTFS}/lib/libdolbyms12.so 
}

# For dm-verity
IMAGE_CLASSES += "${@bb.utils.contains('DISTRO_FEATURES', 'dm-verity', 'image_types aml-dm-verity-img', '', d)}"
DM_VERITY_IMAGE = "vendor-image"
DM_VERITY_IMAGE_TYPE = "ext4"
STAGING_VERITY_DIR = "${DEPLOY_DIR_IMAGE}"

