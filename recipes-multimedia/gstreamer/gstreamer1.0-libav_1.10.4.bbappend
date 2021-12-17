FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append =" file://0001-Add-sbr-buffer-flushing-for-aac-decoder-to-avoid-dir.patch "

EXTRA_OECONF_append = " --with-libav-extra-configure='\
             --disable-decoder=h264 \
             --disable-decoder=hevc \
             --disable-decoder=vp9 \
             --disable-decoder=mpeg2video \
             --disable-decoder=ac3 \
             --disable-encoder=ac3 \
             --disable-decoder=ac3_at \
             --disable-decoder=ac3_fixed \
             --disable-encoder=ac3_fixed \
             --disable-decoder=dca \
             --disable-encoder=dca \
             --disable-decoder=dolby_e \
             --disable-decoder=eac3 \
             --disable-encoder=eac3 \
             --disable-decoder=eac3_at \
             --disable-decoder=mlp \
             --disable-encoder=mlp \
             --disable-decoder=truehd \
             --disable-encoder=truehd \
             '"

