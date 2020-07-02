do_install_append() {
sed -i -e 's/ExecStart=/ExecStart=-/' ${D}/lib/systemd/system/systemd-modules-load.service
cat >> ${D}/lib/systemd/system/systemd-modules-load.service <<EOF
ExecStartPost=-/bin/sh -c '/etc/modules-load.sh'
EOF

}
