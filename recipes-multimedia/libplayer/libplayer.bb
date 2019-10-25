SUMMARY = "aml libplayer code"
LICENSE = "LGPL-2.0+"
DEPENDS = "curl bzip2 alsa-lib virtual/gettext libxml2"
SRC_URI = "git://git.myamlogic.com/platform/packages/amlogic/LibPlayer.git;protocol=git;nobranch=1"
MIRRORS_prepend += "git://git.myamlogic.com/platform/packages/amlogic/LibPlayer.git git://git@openlinux.amlogic.com/yocto/platform/packages/amlogic/LibPlayer.git;protocol=ssh; \n"

SRC_URI += "file://0001-fix-libplayer-compilation-on-yocto.patch\
            file://0002-PD138385-fix-yocto-alsa-hw-set-issue.patch\
            file://0003-fix-compilation-on-audioplayer.patch\
            "
PROVIDES += "libamcodec.so"
DEFAULT_PREFERENCE = "-1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2f61b7eacf1021ca36600c8932d215b9"

SRCREV ?="${AUTOREV}"

#SRC_URI += "\
#           file://0001-PD-151901-set-drmmode-flag-before-codec_init-for-MUL.patch \
#           "

EXTRA_OECONF_remove += "-fno-omit-frame-pointer"

EXTRA_OEMAKE = "LIBPLAYER_STAGING_DIR=${D} CROSS=${TARGET_PREFIX} TARGET_DIR=${D} STAGING_DIR=${D} DESTDIR=${D} INSTALL_DIR=${D}/usr/lib"
EXTRA_OECONF = " \
    --disable-stripping \
    --enable-shared \
    --enable-pthreads \
    --disable-yasm \
    --enable-debug \
    --disable-ffplay \
    --disable-ffmpeg \
    --enable-cross-compile \ 
    --disable-librtmp \
    --disable-static \
    --disable-ffserver \
    --disable-doc \
    --cross-prefix=${TARGET_PREFIX} \
    --ld="${CCLD}" \
    --cc="${CC} " \
    --arch=${TARGET_ARCH} \
    --target-os="linux" \
    --extra-cflags="${TARGET_CFLAGS} ${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -I${D}/usr/include/" \
    --extra-ldflags="-L${D}/usr/lib -lamavutils -ldl" \
    --extra-libs="-lamavutils -ldl -lpthread" \ 
    --incdir=${includedir} \ 
    --sysroot="${STAGING_TARGET_DIR}" \
    --libdir=${libdir} \
    --shlibdir=${libdir} \
    --datadir=${datadir} \
    --bindir=${bindir} \
"

do_configure(){
       cd ${S}/amffmpeg
       ./configure ${EXTRA_OECONF}
}

do_compile () {
	install -m 0755 -d ${D}/bin
	install -m 0755 -d ${D}/usr/lib
	install -m 0755 -d ${D}/usr/bin
	install -m 0755 -d ${D}/usr/include
	install -m 0755 -d ${D}/usr/include/sys
	cd ${S}
	oe_runmake -j1 ${EXTRA_OEMAKE} all
	install ${S}/examples/kplayer ${D}/usr/bin
        install ${S}/amffmpeg/ffprobe ${D}/usr/bin
}

inherit  pkgconfig distro_features_check
do_install[noexec] = "1"

FILES_${PN} += "/usr/share/lib* /usr/lib/amplayer/*.so /bin/ /usr/bin/"
FILES_${PN} += "/usr/lib/libadpcm.so \
                /usr/lib/libamadec.so \
                /usr/lib/libamavutils.so \
                /usr/lib/libamcodec.so \
                /usr/lib/libamffmpegdec.so \
                /usr/lib/libammad.so \
                /usr/lib/libamplayer.so \
                /usr/lib/libamr.so \
                /usr/lib/libamstreaming.so \
                /usr/lib/libape.so \
                /usr/lib/libcook.so \
                /usr/lib/libfaad.so \
                /usr/lib/libflac.so \
                /usr/lib/liblibpcm_wfd.so \
                /usr/lib/libpcm.so \
                /usr/lib/libraac.so \
                /usr/lib/libdash_mod.so \
                /usr/lib/libcurl_mod.so \
                /usr/lib/libavcodec.so \
                /usr/lib/libavutil.so \
                /usr/lib/libswscale.so \
                /usr/lib/libavfilter.so \
                /usr/lib/libavdevice.so \
                /usr/lib/libavformat.so \
                /usr/lib/libamvdec.so \
                "

FILES_${PN}-dev = "/usr/include/* \
        /usr/lib/pkgconfig/* \
        "
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1" 
S = "${WORKDIR}/git"


INSANE_SKIP_${PN} = "ldflags dev-so"
