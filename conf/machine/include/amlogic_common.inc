require conf/machine/include/arm/arch-armv8.inc
include conf/machine/include/amlogic_externalsrc.inc
include conf/machine/include/amlogic_patches.inc
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
        media-modules \
        optee-linux-driver \
        gpu \
        "
MACHINE_EXTRA_RRECOMMENDS = " kernel-modules kernel-devicetree"
EXTRA_IMAGEDEPENDS += "u-boot"
IMAGE_FSTYPES += "tar.gz ext4"
IMAGE_FSTYPES_DEBUGFS = ""
SERIAL_CONSOLES = "115200;ttyS0"
SERIAL_CONSOLES_sc2-5.4 = "921600;ttyS0"
SERIAL_CONSOLES_sc2 = "921600;ttyS0"
SERIAL_CONSOLES_t5d = "921600;ttyS0"
SERIAL_CONSOLES_t5d-5.4 = "921600;ttyS0"
SERIAL_CONSOLES_s4 = "921600;ttyS0"
PREFERRED_PROVIDER_virtual/kernel ?= "linux-meson"
KERNEL_IMAGETYPE_aarch64 = "Image.gz"
KERNEL_IMAGETYPE_armv7a = "uImage"
UBOOT_SUFFIX = "bin"
MACHINE_FEATURES = "usbgadget usbhost vfat "
IMAGE_BOOT_FILES ?= "u-boot.${UBOOT_SUFFIX}"
INITRAMFS_FSTYPES = "cpio.gz"
KERNEL_MODULE_AUTOLOAD += "mali"
TDK_VERSION = "v2.4"
TDK_VERSION_g12a = "v3.8"
TDK_VERSION_sc2 = "v3.8/dev/S905X4"
TDK_VERSION_sc2-5.4 = "v3.8/dev/S905X4"
TDK_VERSION_s4 = "v3.8/dev/S905Y4"

AML_GIT_ROOT ?= "git@openlinux.amlogic.com/yocto"
AML_GIT_PROTOCOL ?= "ssh"

AML_GIT_ROOT_YOCTO_SUFFIX ?= ""
AML_GIT_ROOT_PR ?= "git@openlinux.amlogic.com"
AML_GIT_ROOT_WV ?= "git@openlinux.amlogic.com/yocto"
AML_GIT_ROOT_PROTOCOL ?= "ssh"
AML_META_LAYER ?= "meta-meson"

#check connectivity using bing
CONNECTIVITY_CHECK_URIS = "https://www.bing.com/"

LINUXLIBCVERSION ?= "4.9%"
PREFERRED_VERSION_libdrm = "2.4.100"
PREFERRED_VERSION_u-boot = "v2015.%"
WIDEVINE_VERSION = "16"
