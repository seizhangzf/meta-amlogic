FILESEXTRAPATHS_prepend := "${THISDIR}/files/:"
SRC_URI_append = " file://70-keyboard.hwdb"

do_install_append() {
sed -i -e 's/ExecStart=/ExecStart=-/' ${D}/lib/systemd/system/systemd-modules-load.service
cat >> ${D}/lib/systemd/system/systemd-modules-load.service <<EOF
ExecStartPost=-/bin/sh -c '/etc/modules-load.sh'
EOF
install -m 0644 ${WORKDIR}/70-keyboard.hwdb ${D}/lib/udev/hwdb.d/70-keyboard.hwdb
}
