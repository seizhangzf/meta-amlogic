SUMMARY = "Package Group For AMLOGIC Components"

LICENSE = "AMLOGIC"
LIC_FILES_CHKSUM = "file://${COREBASE}/../meta-meson/license/AMLOGIC;md5=6c70138441c57c9e1edb9fde685bd3c8"

inherit packagegroup

PACKAGES = "\
    packagegroup-amlogic-common \
    "

RDEPENDS_packagegroup-amlogic-common = "\
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'optee', 'optee-userspace', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'optee', 'tee-supplicant', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'optee', 'optee-video-firmware', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'optee', 'aml-provision', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'widevine', 'aml-mediadrm-widevine', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'widevine', 'gst-aml-drm-plugins', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'widevine', 'wpeframework-ocdm-widevine', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'playready', 'playready', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'playready', 'wpeframework-ocdm-playready', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'wpeframework-ocdm-clearkey', '', d)}\
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'alsa-utils', '', d)}\
                                    tinyalsa \
                                    dolby-ms12 \
                                    libamavutils \
                                    aml-amaudioutils \
                                    grpc \
                                    aml-audio-hal \
                                    aml-audio-service   \
                                    aml-audio-service-testapps   \
                                    initramfs-meson-boot \
                                    e2fsprogs \
                                    fbscripts \
                                    ntfsprogs \
                                    ntfs-3g \
                                    iw \
                                    wpa-supplicant \
                                    packagegroup-rdk-gstreamer1 \
                                    playscripts \
                                    wireless-tools \
                                    tinyalsa-tools \
                                    libdrm-tests \
                                    gst-aml-plugins \
                                    android-tools-adbd \
                                    android-tools-logcat \
                                    liblog \
                                    aml-hdcp \
                                    "

