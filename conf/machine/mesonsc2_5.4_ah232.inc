#@TYPE: Machine
#@NAME: meson

#@DESCRIPTION: Machine configuration for meson systems

include conf/machine/include/mesonsc2_5.4.inc

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

KERNEL_DEVICETREE = "sc2_s905x4_ah212_linux.dtb"
KERNEL_MODULE_AUTOLOAD += " btqca"
KERNEL_MODULE_PROBECONF += "btqca"
KERNEL_MODULE_AUTOLOAD += " hci_uart"
KERNEL_MODULE_PROBECONF += "hci_uart"
KERNEL_MODULE_AUTOLOAD += "cxd2856_fe_64"
KERNEL_MODULE_AUTOLOAD += "aml_aucpu"
KERNEL_MODULE_AUTOLOAD += "dvb_demux"
KERNEL_MODULE_AUTOLOAD += "meson_ir"
KERNEL_MODULE_AUTOLOAD += "snd-soc-dummy_codec"
KERNEL_MODULE_AUTOLOAD += "snd-soc-aml_t9015"
KERNEL_MODULE_AUTOLOAD += "dolby_fw"
KERNEL_MODULE_AUTOLOAD += "meson-cpufreq"

UBOOT_MACHINE = "sc2_ah232_config"
DISTRO_FEATURES_append = " drm"
DISTRO_FEATURES_append = " onepass"
DISTRO_FEATURES_append = " secure-u-boot optee "
DISTRO_FEATURES_append = " absystem"
DISTRO_FEATURES_append = " opencdm "
DISTRO_FEATURES_append = " widevine "
DISTRO_FEATURES_append = " playready "
DISTRO_FEATURES_append = " amlogic-dvb "
DISTRO_FEATURES_append = " bluetooth bluez5 "
DISTRO_FEATURES_append = " ptest "
DISTRO_FEATURES_remove = " alsa "

OVERRIDES .= ":ah212:sc2-5.4:onepass"
MACHINEOVERRIDES .= ":client"
PREFERRED_PROVIDER_widevine = "aml-mediadrm-widevine"

PREFERRED_VERSION_wayland = "1.17.0"
PREFERRED_VERSION_wayland-protocol = "1.18.0"

DISTRO_FEATURES_remove_dunfell = " virtualization"
TDK_VERSION_sc2 = "v3.8/dev/S905C2"
TDK_VERSION_sc2-5.4 = "v3.8/dev/S905C2"
