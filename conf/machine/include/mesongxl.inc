# common configuration

require conf/machine/include/amlogic_common.inc
PREFERRED_PROVIDER_virtual/gpu ?= "mali450"
OVERRIDES .= ":gxl"
