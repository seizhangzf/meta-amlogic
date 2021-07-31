SUMMARY = "create vbmeta.img for AVB"
LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../${AML_META_LAYER}/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"


SRC_URI = "file://testkey_rsa2048.pem"


DEPENDS += "avb-native python3-native"

do_compile() {
   install -d ${DEPLOY_DIR_IMAGE}
   avbtool make_vbmeta_image --output ${DEPLOY_DIR_IMAGE}/vbmeta.img --key ${WORKDIR}/testkey_rsa2048.pem --prop dovi_hash:3cd93647bdd864b4ae1712d57a7de3153e3ee4a4dfcfae5af8b1b7d999b93c5a --algorithm SHA256_RSA2048 --padding_size 4096 --rollback_index 0
}
