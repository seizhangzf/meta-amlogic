SUMMARY = "aml libplayer code"
LICENSE = "LGPL-2.0+"
DEPENDS = "curl bzip2 alsa-lib virtual/gettext"
SRC_URI = "git://git.myamlogic.com/platform/packages/amlogic/LibPlayer.git;protocol=git;nobranch=1"
MIRRORS_prepend += "git://git.myamlogic.com/platform/packages/amlogic/LibPlayer.git git://git@openlinux.amlogic.com/yocto/platform/packages/amlogic/LibPlayer.git;protocol=ssh; \n"

DEFAULT_PREFERENCE = "-1"
LIC_FILES_CHKSUM = "file://Makefile;md5=2e513777cb96aec3d7397dc4fac9b2ad"
PLBANCH = "yocto-libplayer"
SRCREV="28bb064d8cf43556c1dada977ca55d00dbe45605"
S = "${WORKDIR}/git"

SRC_URI += "\
           file://audio_codec_Makfile_changes.patch \
           "

EXTRA_OEMAKE = "LIBPLAYER_STAGING_DIR=${D} CROSS=${TARGET_PREFIX} TARGET_DIR=${D} STAGING_DIR=${D} DESTDIR=${D}"
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
    --extra-cflags="${TARGET_CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS} -I${D}/usr/include/" \
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
       cd ${S}
}

do_compile () {
	install -m 0755 -d ${D}/bin
	install -m 0755 -d ${D}/usr/lib
	install -m 0755 -d ${D}/usr/bin 
	install -m 0755 -d ${D}/usr/include
	install -m 0755 -d ${D}/usr/include/sys
	oe_runmake ${EXTRA_OEMAKE} all
	install ${S}/examples/kplayer ${D}/usr/bin
}

pkg_postinst_${PN} () {
    ln -s libadpcm.so.1 $D/usr/lib/libadpcm.so
    ln -s libamadec.so.1 $D/usr/lib/libamadec.so
    ln -s libamavutils.so.1 $D/usr/lib/libamavutils.so
    ln -s libamcodec.so.1 $D/usr/lib/libamcodec.so
    ln -s libamffmpegdec.so.1 $D/usr/lib/libamffmpegdec.so
    ln -s libamplayer.so.1 $D/usr/lib/libamplayer.so
    ln -s libamr.so.1 $D/usr/lib/libamr.so
    ln -s libamstreaming.so.1 $D/usr/lib/libamstreaming.so
    ln -s libape.so.1 $D/usr/lib/libape.so
    ln -s libcook.so.1 $D/usr/lib/libcook.so
    ln -s libfaad.so.1 $D/usr/lib/libfaad.so
    ln -s libflac.so.1 $D/usr/lib/libflac.so
    ln -s libmad.so.1 $D/usr/lib/libmad.so
    ln -s libpcm.so.1 $D/usr/lib/libpcm.so
    ln -s libpcm_wfd.so.1 $D/usr/lib/libpcm_wfd.so
    ln -s libraac.so.1 $D/usr/lib/libraac.so
}
inherit  pkgconfig distro_features_check

do_install[noexec] = "1"

#INHIBIT_PACKAGE_STRIP = "1"
#INHIBIT_SYSROOT_STRIP = "1"
#INHIBIT_PACKAGE_DEBUG_SPLIT = "1" 
FILES_${PN} += "/usr/share/lib* /usr/lib/amplayer/*.so /bin /usr/bin/"
INSANE_SKIP_${PN} = "ldflags dev-so"
