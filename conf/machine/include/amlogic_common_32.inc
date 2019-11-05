require conf/machine/include/tune-cortexa8.inc
PREFERRED_PROVIDER_virtual/kernel = "linux-dummy"
IMAGE_FSTYPES += "tar.gz ext4 "
SERIAL_CONSOLE = "115200 ttyS0"
MACHINE_FEATURES = "usbgadget usbhost vfat alsa"
IMAGE_FSTYPES_DEBUGFS = "" 
