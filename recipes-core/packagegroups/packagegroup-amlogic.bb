SUMMARY = "Package Group For AMLOGIC Components"

LICENSE = "CLOSED"

inherit packagegroup

PACKAGES = "\
    packagegroup-amlogic-common \
    packagegroup-amlogic-tv \
    packagegroup-amlogic-dvb \
    "

RDEPENDS_packagegroup-amlogic-common = "\
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'optee', 'optee-userspace', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'optee', 'optee-video-firmware', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'optee', 'aml-provision', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'widevine', 'aml-mediadrm-widevine', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'widevine', 'gst-aml-drm-plugins', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'widevine', 'wpeframework-ocdm-widevine', '', d)} \
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'wpeframework-ocdm-clearkey', '', d)}\
                                    ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'alsa-utils', '', d)}\
                                    tinyalsa \
                                    aml-amaudioutils \
                                    grpc \
                                    aml-audio-hal \
                                    aml-audio-service   \
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
                                    "
RDEPENDS_packagegroup-amlogic-tv = "\
                                    aml-tvserver \
                                    "
#RDEPENDS_packagegroup-amlogic-dvb = "\
#                                    aml-dvb \
#                                    "
