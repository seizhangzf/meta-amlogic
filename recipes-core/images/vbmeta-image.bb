SUMMARY = "create vbmeta.img for AVB"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"


SRC_URI = "file://testkey_rsa2048.pem"


DEPENDS += "avb-native python3-native"

do_compile() {
   install -d ${DEPLOY_DIR_IMAGE}
   #if boot.img already has hash_footer, avbtool won't add again, so don't need erase hash_footer first
   avbtool add_hash_footer --image ${DEPLOY_DIR_IMAGE}/boot.img --partition_size 67108864 --partition_name boot
   avbtool make_vbmeta_image --output ${DEPLOY_DIR_IMAGE}/vbmeta.img --key ${WORKDIR}/testkey_rsa2048.pem --prop dovi_hash:3cd93647bdd864b4ae1712d57a7de3153e3ee4a4dfcfae5af8b1b7d999b93c5a --algorithm SHA256_RSA2048 --include_descriptors_from_image ${DEPLOY_DIR_IMAGE}/boot.img --padding_size 4096 --rollback_index 0
}

do_compile[depends]="core-image-minimal:do_image_complete"
deltask do_package
deltask do_packagedata
deltask do_package_write_ipk
