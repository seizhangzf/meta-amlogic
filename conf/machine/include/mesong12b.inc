# common configuration
require conf/machine/include/amlogic_common.inc
PREFERRED_PROVIDER_virtual/gpu ?= "gondul"
OVERRIDES .= ":g12b"
