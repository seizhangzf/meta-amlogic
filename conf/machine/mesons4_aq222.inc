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
        "

KERNEL_DEVICETREE = "s4_s805x2_aq222_linux_1g.dtb"
UBOOT_MACHINE = "s4_aq222_config"

KERNEL_MODULE_AUTOLOAD += " snd-soc-dummy_codec"
KERNEL_MODULE_AUTOLOAD += " snd-soc-aml_t9015"
KERNEL_MODULE_AUTOLOAD += "dvb_demux"
KERNEL_MODULE_AUTOLOAD += "meson_ir"
KERNEL_MODULE_AUTOLOAD += " btqca"
KERNEL_MODULE_PROBECONF += "btqca"
KERNEL_MODULE_AUTOLOAD += " hci_uart"
KERNEL_MODULE_PROBECONF += "hci_uart"
KERNEL_MODULE_AUTOLOAD += " dolby_fw"
KERNEL_MODULE_AUTOLOAD += "meson-cpufreq"
KERNEL_MODULE_AUTOLOAD += " media_sync"

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
OVERRIDES .= ":aq222:s4:onepass"
MACHINEOVERRIDES .= ":client"
PREFERRED_PROVIDER_widevine = "aml-mediadrm-widevine"
DISTRO_FEATURES_remove_dunfell = " virtualization"

TDK_VERSION_s4 = "v3.8/dev/S805X2"

# dm-verity
#DISTRO_FEATURES_append = " dm-verity"
#DISTRO_FEATURES_NATIVE_append = " dm-verity"