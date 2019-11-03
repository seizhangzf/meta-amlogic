do_install_append_tm2 () {

#         part-0 = <&logo>;
#         part-1 = <&recovery>;
#         part-2 = <&misc>;
#         part-3 = <&dtbo>;
#         part-4 = <&cri_data>;
#         part-5 = <&param>;
#         part-6 = <&boot>;
#         part-7 = <&rsv>;
#         part-8 = <&metadata>;
#         part-9 = <&vbmeta>;
#         part-10 = <&tee>;
#         part-11 = <&vendor>;
#         part-12 = <&odm>;
#         part-13 = <&system>;
#         part-14 = <&product>;
#         part-15 = <&cache>;
#         part-16 = <&data>;

mkdir -p ${D}/vendor
mkdir -p ${D}/odm
mkdir -p ${D}/product
mkdir -p ${D}/cache
mkdir -p ${D}/data


cat >> ${D}${sysconfdir}/fstab <<EOF
 /dev/vendor            /vendor                    auto       defaults              0  0
 /dev/odm            /odm                    auto       defaults              0  0
 /dev/cache            /cache                    auto       defaults              0  0
 /dev/data            /data                    auto       defaults              0  0

EOF

}

FILES_${PN}_append_tm2 = " /vendor /odm /product /cache /data"
dirs755_append_tm2 = " /vendor /odm /product /cache /data"
