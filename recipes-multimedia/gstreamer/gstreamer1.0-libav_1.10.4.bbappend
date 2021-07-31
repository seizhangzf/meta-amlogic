FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

EXTRA_OECONF_append = " --with-libav-extra-configure='\
             --disable-decoder=ac3 \
             --disable-decoder=eac3 \
             --disable-decoder=ac3_fixed \
             --disable-decoder=h264 \
             --disable-decoder=hevc \
             --disable-decoder=vp9 \
             --disable-decoder=mpeg2video \
             '"

