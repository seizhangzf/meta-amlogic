# common configuration
require conf/machine/include/amlogic_common.inc
PREFERRED_VERSION_linux-meson = "5.4.%"
PREFERRED_PROVIDER_virtual/gpu ?= "dvalin"
OVERRIDES .= ":t5d"
