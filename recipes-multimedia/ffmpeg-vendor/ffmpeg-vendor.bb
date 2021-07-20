DESCRIPTION = "ffmpeg-vendor in yocto"
SECTION = "linuxdrm"
LICENSE = "CLOSED"
PV = "1"
PR = "r0"

INSANE_SKIP_${PN} += " ldflags"
INSANE_SKIP_${PN}-dev += "dev-elf"
EXTRA_OEMAKE = "STAGING_DIR=${STAGING_DIR_TARGET} \
		  TARGET_DIR=${D} "

inherit autotools pkgconfig

DISABLE_STATIC = ""

#inherit externalsrc
#EXTERNALSRC = "${TOPDIR}/aml-comp/multimedia/ffmpeg-vendor"
#EXTERNALSRC_BUILD = "${WORKDIR}/build"

SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"
SRC_URI = " git://${AML_GIT_ROOT}/platform/external/ffmpeg-aml;protocol=${AML_GIT_PROTOCOL};branch=n-amlogic"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " file://0001-do-not-install-pkgconfig.patch;patch=1;"
SRC_URI_append = " file://0002-do-remove-config-headfile.patch;patch=2;"


EXTRA_FFCONF = " \
	--disable-ffmpeg \
	--disable-ffprobe \
	--disable-ffserver \
	--disable-swscale \
	--disable-swresample \
	--disable-postproc \
	--disable-symver \
	--disable-doc \
	--disable-everything \
	--disable-small \
	--disable-shared \
	--disable-extra-warnings \
	--enable-optimizations \
	--enable-static \
	--enable-pic \
	--enable-demuxer=mpegts \
	--enable-protocol=http \
	--enable-protocol=https \
	--enable-protocol=hls \
	--enable-protocol=file \
	--enable-protocol=udp \
	--enable-decoder=h264 \
	--enable-decoder=hevc \
	--enable-decoder=vp9 \
	--enable-decoder=mpegvideo \
"

EXTRA_OECONF = " \
    \
    --cross-prefix=${TARGET_PREFIX} \
    \
    --ld="${CCLD}" \
    --cc="${CC}" \
    --cxx="${CXX}" \
    --arch=${TARGET_ARCH} \
    --target-os="linux" \
    --enable-cross-compile \
    --extra-cflags="${CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}" \
    --extra-ldflags="${LDFLAGS}" \
    --sysroot="${STAGING_DIR_TARGET}" \
    ${EXTRA_FFCONF} \
    --libdir=${libdir}/ffmpeg-vendor \
    --shlibdir=${libdir}/ffmpeg-vendor \
	--incdir=${includedir}/ffmpeg-vendor \
    --datadir=${datadir}/ffmpeg \
    --pkg-config=pkg-config \
"

do_configure() {
	${S}/configure ${EXTRA_OECONF}
}

do_compile () {
	autotools_do_compile
	${CC} --shared -Wl,--whole-archive \
		libavutil/libavutil.a \
		libavformat/libavformat.a \
		libavcodec/libavcodec.a \
		-Wl,--no-whole-archive -lpthread \
		-o libffmpeg-vendor.so

}

do_install() {
    make DESTDIR=${D} install-headers
    mkdir -p ${D}${libdir}/
	install -d -m 0644 ${D}${libdir}/
    install -D -m 0644 libffmpeg-vendor.so ${D}${libdir}/
	${STRIP} -s ${D}${libdir}/libffmpeg-vendor.so
}

do_strip() {
    ${STRIP} -s ${D}${libdir}/libffmpeg-vendor.so
}

addtask strip

FILES_${PN} = "${bindir}/ffmpeg-vendor"
FILES_${PN} += " ${bindir}/* ${libdir}/*.so"
FILES_${PN}-dev = "${includedir}/* "


