PACKAGECONFIG = "drm initrd"

do_install_append() {
  sed -i \
  's@\(ExecStart=\).*@\1/sbin/plymouthd --kernel-command-line="splash plymouth.ignore-serial-consoles" --mode=boot --pid-file=/run/plymouth/pid --attach-to-session@' \
  ${D}/lib/systemd/system/plymouth-start.service
  sed -i 's@\(ShowDelay=\).*@\10@' ${D}/usr/share/plymouth/plymouthd.defaults
  echo "[Daemon]" >> ${D}${sysconfdir}/plymouth/plymouthd.conf
  echo "Theme=tribar" >> ${D}${sysconfdir}/plymouth/plymouthd.conf
}
