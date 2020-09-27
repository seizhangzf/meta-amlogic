# common configuration
require conf/machine/include/amlogic_common.inc
PREFERRED_VERSION_u-boot = "v2019.%"
PREFERRED_PROVIDER_virtual/gpu ?= "dvalin"
OVERRIDES .= ":sc2"
