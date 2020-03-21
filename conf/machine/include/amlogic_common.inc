require conf/machine/include/arm/arch-armv8.inc
include conf/machine/include/amlogic_srcrev.inc
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
        media-modules \
        optee-linux-driver \
        gpu \
        "
MACHINE_EXTRA_RRECOMMENDS = " kernel-modules kernel-devicetree"
EXTRA_IMAGEDEPENDS += "u-boot"
IMAGE_FSTYPES += "tar.gz ext4"
IMAGE_FSTYPES_DEBUGFS = ""
SERIAL_CONSOLE = "115200 ttyS0"
PREFERRED_PROVIDER_virtual/kernel ?= "linux-meson"
KERNEL_IMAGETYPE_aarch64 = "Image.gz"
KERNEL_IMAGETYPE_armv7a = "uImage"
UBOOT_SUFFIX = "bin"
MACHINE_FEATURES = "usbgadget usbhost vfat alsa"
IMAGE_BOOT_FILES ?= "u-boot.${UBOOT_SUFFIX}"
INITRAMFS_FSTYPES = "cpio.gz"
KERNEL_MODULE_AUTOLOAD += "mali"

AML_GIT_ROOT ?= "git@openlinux.amlogic.com/yocto"
AML_GIT_PROTOCOL ?= "ssh"
AML_GIT_ROOT_YOCTO_SUFFIX ?= ""
LINUXLIBCVERSION ?= "4.9%"
PREFERRED_VERSION_libdrm = "2.4.100"
