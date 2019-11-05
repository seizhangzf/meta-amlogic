FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Only apply this workaround for dvalin and gondul GPUs
# Once arm has fixed shader garbage issue in mali side, this patch can be removed
WORK_AROUND = ""
WORK_AROUND_g12a = "file://0006-Let-Cairo-not-use-GL_BGRA-on-cairogl_surface_map_to_.patch"
WORK_AROUND_g12b = "file://0006-Let-Cairo-not-use-GL_BGRA-on-cairogl_surface_map_to_.patch"
WORK_AROUND_tm2 = "file://0006-Let-Cairo-not-use-GL_BGRA-on-cairogl_surface_map_to_.patch"
SRC_URI += "${WORK_AROUND}"

