FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

PROVIDES_remove = "virtual/libgl virtual/libgles1 virtual/libgles2 virtual/egl virtual/mesa"
