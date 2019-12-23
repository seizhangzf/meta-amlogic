DESCRIPTION = "optee and tee-supplicant"
LICENSE = "Closed"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


SRC_URI = "git://${AML_GIT_ROOT}/vendor/amlogic/tdk.git;protocol=${AML_GIT_PROTOCOL};branch=tdk-v2.4"
SRC_URI += "file://tee-supplicant.service"
SRC_URI += "file://0001-For-compilation-with-arm-rdk-linux-gnueabi-gcc.patch"
SRC_URI += "file://0001-compile-fix.patch"

SRCREV ?= "${AUTOREV}"
PV = "git${SRCPV}"

do_configure[noexec] = "1"
do_populate_lic[noexec] = "1"

DEPENDS += " coreutils-native python-native python-pycrypto-native"
PROVIDES = "optee-userspace-demos"
export PYTHONPATH="${STAGING_DIR_NATIVE}/usr/lib/python2.7/site-packages/"
PACKAGES =+ "\
    ${PN}-demos \
"

S = "${WORKDIR}/git"

do_compile() {
    unset LDFLAGS
    KERNEL_A32_SUPPORT=true oe_runmake -C ${S}/demos/hello_world TA_CROSS_COMPILE=${TARGET_PREFIX} CROSS_COMPILE=${TARGET_PREFIX}
    KERNEL_A32_SUPPORT=true oe_runmake -C ${S}/demos/optee_test CROSS_COMPILE_TA=${TARGET_PREFIX} CROSS_COMPILE=${TARGET_PREFIX}
}

TDK_SECUROS_TA_FILE =  "e626662e-c0e2-485c-b8c8-09fbce6edf3d \
                       e13010e0-2ae1-11e5-896a-0002a5d5c51b \
                       5ce0c432-0ab0-40e5-a056-782ca0e6aba2 \
                       c3f6e2c0-3548-11e1-b86c-0800200c9a66 \
                       cb3e5ba0-adf1-11e0-998b-0002a5d5c51b \
                       5b9e0e40-2636-11e1-ad9e-0002a5d5c51b \
                       d17f73a0-36ef-11e1-984a-0002a5d5c51b \
                       614789f2-39c0-4ebf-b235-92b32ac107ed \
                       e6a33ed4-562b-463a-bb7e-ff5e15a493c8 \
                       873bcd08-c2c3-11e6-a937-d0bf9c45c61c \
                       b689f2a7-8adf-477a-9f99-32e90c0ad0a2 \
                       731e279e-aafb-4575-a771-38caa6f0cca6 \
                       f157cda0-550c-11e5-a6fa-0002a5d5c51b \
                       8aaaf200-2450-11e4-abe2-0002a5d5c51b"



do_install() {
    mkdir -p ${D}${bindir}
    install -m 0755 ${S}/ca_export_arm/bin/tee-supplicant ${D}${bindir}

    mkdir -p ${D}${libdir}
    install -m 0755 ${S}/ca_export_arm/lib/libteec.so ${D}${libdir}/libteec.so.1.0

    ln -s libteec.so.1 ${D}${libdir}/libteec.so
    ln -s libteec.so.1.0 ${D}${libdir}/libteec.so.1

    # systemd service file
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_unitdir}/system/

    # optee-demos
    install -m 0755 ${S}/demos/hello_world/out/ca/tee_helloworld ${D}${bindir}
    install -m 0755 ${S}/demos/optee_test/out/xtest/tee_xtest ${D}${bindir}
    mkdir -p ${D}/lib/teetz/

    if ${@bb.utils.contains('DISTRO_FEATURES','secure-u-boot','true','false',d)}; then
         install -d ${D}/target_ta_dir
         install -m 0644  ${S}/demos/hello_world/out/ta/8aaaf200-2450-11e4-abe2-0002a5d5c51b.ta ${D}/target_ta_dir
         find ${S}/demos/optee_test/out/ta  -name *.ta | xargs -i install -m 0644 {} ${D}/target_ta_dir/
        for f in ${TDK_SECUROS_TA_FILE}; do 
            #install -m 0644 ${S}/demos/optee_test/out/ta/${f}.ta ${D}/target_ta_dir
            ${S}/ta_export/scripts/gen_cert_key.py \
                --root_rsa_key=${S}/ta_export/keys/root_rsa_prv_key.pem \
                --ta_rsa_key=${S}/ta_export/keys/ta_rsa_pub_key.pem \
                --uuid=${f} \
                --ta_rsa_key_sig=${S}/ta_rsa_key.sig \
                --root_aes_key=${S}/ta_export/keys/root_aes_key.bin \
                --ta_aes_key=${S}/ta_export/keys/ta_aes_key.bin \
                --ta_aes_iv=${S}/ta_export/keys/ta_aes_iv.bin \
                --ta_aes_key_iv_enc=${S}/ta_aes_key_enc.bin; 

            ${S}/ta_export/scripts/sign_ta.py \
                --ta_rsa_key=${S}/ta_export/keys/ta_rsa_prv_key.pem \
                --ta_rsa_key_sig=${S}/ta_rsa_key.sig \
                --ta_aes_key=${S}/ta_export/keys/ta_aes_key.bin \
                --ta_aes_iv=${S}/ta_export/keys/ta_aes_iv.bin \
                --ta_aes_key_iv_enc=${S}/ta_aes_key_enc.bin \
                --in=${D}/target_ta_dir/${f}.ta  \
                --out=${D}/lib/teetz/${f}.ta ; \
            rm -rf ${S}/ta_aes_key_enc.bin ${S}/ta_rsa_key.sig; 
            done
        else
            install -m 0755 ${S}/demos/hello_world/out/ta/8aaaf200-2450-11e4-abe2-0002a5d5c51b.ta ${D}/lib/teetz/
        fi
        rm -rf ${D}/target_ta_dir

}

FILES_${PN} += " ${libdir}/libteec.so.1.0 \
                ${libdir}/libteec.so.1"

FILES_${PN} += "${bindir}/tee-supplicant"

FILES_${PN}-dev += "${libdir}/libteec.so"

INSANE_SKIP_${PN} = "ldflags dev-so"
INSANE_SKIP_${PN}-demos = "ldflags dev-so"

FILES_${PN} += "${systemd_unitdir}/system/tee-supplicant.service"
SYSTEMD_SERVICE_${PN} = "tee-supplicant.service"
inherit systemd

# optee-demos
FILES_${PN}-demos += "${bindir}/* \
                      /lib/teetz/*"
RDEPENDS_${PN}-demos += "${PN}"
