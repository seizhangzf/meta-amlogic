do_install_append() {
    ln -s libbz2.so.1.0.6 ${D}${libdir}/libbz2.so.1.0
}
