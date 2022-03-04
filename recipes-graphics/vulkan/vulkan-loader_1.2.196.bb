SUMMARY = "3D graphics and compute API common loader"
DESCRIPTION = "Vulkan is a new generation graphics and compute API \
that provides efficient access to modern GPUs. These packages \
provide only the common vendor-agnostic library loader, headers and \
the vulkaninfo utility."
HOMEPAGE = "https://www.khronos.org/vulkan/"
BUGTRACKER = "https://github.com/KhronosGroup/Vulkan-Loader"
SECTION = "libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=7dbefed23242760aa3475ee42801c5ac \
                    file://loader/loader.c;endline=27;md5=7f8f21320fe92ccc7b1eb65c94dd91ca"

SRC_URI = "https://github.com/KhronosGroup/Vulkan-Loader/archive/v${PV}/${BPN}-${PV}.tar.gz"
SRC_URI[sha256sum] = "fd7bc148ecd75e3437edd25d9f8d53774be444e424079928ab014e968a7a1385"

S = "${WORKDIR}/Vulkan-Loader-${PV}"

#REQUIRED_DISTRO_FEATURES = "vulkan"

inherit cmake python3native lib_package features_check
ANY_OF_DISTRO_FEATURES = "x11 wayland"

DEPENDS += "vulkan-headers"

EXTRA_OECMAKE = "-DVULKAN_HEADERS_INSTALL_DIR=${STAGING_EXECPREFIXDIR}"

# choose x11 or wayland or both
PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '' ,d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '' ,d)}"
PACKAGECONFIG[x11] = "-DBUILD_WSI_XLIB_SUPPORT=OFF -DBUILD_WSI_XCB_SUPPORT=OFF, -DBUILD_WSI_XLIB_SUPPORT=OFF -DBUILD_WSI_XCB_SUPPORT=OFF, libxcb libx11 libxrandr"
PACKAGECONFIG[wayland] = "-DBUILD_WSI_WAYLAND_SUPPORT=OFF, -DBUILD_WSI_WAYLAND_SUPPORT=OFF, wayland"

# Can't be built with ccache
CCACHE_DISABLE = "1"

FILES_${PN} = "${libdir}/lib*${SOLIBS}"
