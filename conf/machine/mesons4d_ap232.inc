#@TYPE: Machine
#@NAME: meson

#@DESCRIPTION: Machine configuration for meson systems

include conf/machine/include/mesons4.inc
require conf/machine/include/amlogic_64b.inc

PREFERRED_PROVIDER_virtual/egl = "libgles-eabihf-dvalin-wayland-drm"
PREFERRED_PROVIDER_virtual/libgl = "libgles-eabihf-dvalin-wayland-drm"
PREFERRED_PROVIDER_virtual/libgles1 = "libgles-eabihf-dvalin-wayland-drm"
PREFERRED_PROVIDER_virtual/libgles2 = "libgles-eabihf-dvalin-wayland-drm"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
        wififw-qca6174 \
        qca6174 \
        aucpu-fw \
        tuner-prebuilt \
        "

KERNEL_DEVICETREE = "s4d_s905c3_ap232_linux.dtb"

KERNEL_MODULE_AUTOLOAD += " cxd2856_fe_64 r836_fe_64 av2018_fe_64 "
KERNEL_MODULE_AUTOLOAD += " snd-soc-dummy_codec"
KERNEL_MODULE_AUTOLOAD += " snd-soc-aml_t9015"
KERNEL_MODULE_AUTOLOAD += "aml_aucpu"
KERNEL_MODULE_AUTOLOAD += "meson_ir"
KERNEL_MODULE_AUTOLOAD += " btqca"
KERNEL_MODULE_PROBECONF += "btqca"
KERNEL_MODULE_AUTOLOAD += " hci_uart"
KERNEL_MODULE_PROBECONF += "hci_uart"
KERNEL_MODULE_AUTOLOAD += " dolby_fw"
KERNEL_MODULE_AUTOLOAD += "meson-cpufreq"
KERNEL_MODULE_AUTOLOAD += " media_sync"

UBOOT_MACHINE = "s4_ap232_config"
DISTRO_FEATURES_append = " drm"
DISTRO_FEATURES_append = " onepass"
DISTRO_FEATURES_append = " secure-u-boot optee "
DISTRO_FEATURES_append = " absystem"
DISTRO_FEATURES_append = " opencdm "
DISTRO_FEATURES_append = " widevine "
DISTRO_FEATURES_append = " playready "
DISTRO_FEATURES_append = " amlogic-dvb "
DISTRO_FEATURES_append = " bluetooth bluez5 "
DISTRO_FEATURES_remove = " alsa "
OVERRIDES .= ":ap222:ap232:s4:onepass"
MACHINEOVERRIDES .= ":client"
OVERRIDES .= ":dtv"
DISTRO_FEATURES_append = " dvb"
DISTRO_FEATURES_append = " aml-iptv"
PREFERRED_PROVIDER_widevine = "aml-mediadrm-widevine"
DISTRO_FEATURES_remove_dunfell = " virtualization"
TDK_VERSION_s4 = "v3.8/dev/S905C3"
# dm-verity
#DISTRO_FEATURES_append = " dm-verity"
#DISTRO_FEATURES_NATIVE_append = " dm-verity"
