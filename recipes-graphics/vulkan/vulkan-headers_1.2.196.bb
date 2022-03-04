SUMMARY = "Vulkan API headers"
DESCRIPTION = "Vulkan spec API headers"
HOMEPAGE = "https://www.khronos.org/vulkan/"
BUGTRACKER = "https://github.com/KhronosGroup/Vulkan-Headers"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "https://github.com/KhronosGroup/Vulkan-Headers/archive/v${PV}/${BPN}-${PV}.tar.gz"
SRC_URI[sha256sum] = "33cb99194b5ab082beb00bda1e96311dfe2cb20b0037b6d4c8ae926a50f5a750"

S = "${WORKDIR}/Vulkan-Headers-${PV}"

#REQUIRED_DISTRO_FEATURES = "vulkan"

inherit cmake features_check

PACKAGES += "vulkan-registry"
FILES_vulkan-registry = "${datadir}/vulkan/registry"

