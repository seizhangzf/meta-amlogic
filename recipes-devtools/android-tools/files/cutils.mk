# Makefile for adb

SRCDIR ?= $(S)

VPATH += $(SRCDIR)/system/core/libcutils
libcutils_SRC_FILES += atomic.c
libcutils_SRC_FILES += hashmap.c
libcutils_SRC_FILES += native_handle.c
libcutils_SRC_FILES += config_utils.c
libcutils_SRC_FILES += cpu_info.c
libcutils_SRC_FILES += load_file.c
libcutils_SRC_FILES += str_parms.c
libcutils_SRC_FILES += fs.c
libcutils_SRC_FILES += multiuser.c
libcutils_SRC_FILES += socket_inaddr_any_server.c
libcutils_SRC_FILES += socket_local_client.c
libcutils_SRC_FILES += socket_local_server.c
libcutils_SRC_FILES += socket_loopback_client.c
libcutils_SRC_FILES += socket_loopback_server.c
libcutils_SRC_FILES += socket_network_client.c
libcutils_SRC_FILES += sockets.c
libcutils_SRC_FILES += ashmem-host.c
libcutils_SRC_FILES += dlmalloc_stubs.c
libcutils_OBJS := $(libcutils_SRC_FILES:.c=.o)

CFLAGS += -DANDROID
CFLAGS += -DWORKAROUND_BUG6558362
CFLAGS += -DADB_HOST=1
CFLAGS += -D_XOPEN_SOURCE -D_GNU_SOURCE
CFLAGS += -DANDROID_SMP=0
CFLAGS += -I$(SRCDIR)/system/core/adb
CFLAGS += -I$(SRCDIR)/system/core/include
CFLAGS += -include $(SRCDIR)/build/core/combo/include/arch/$(android_arch)/AndroidConfig.h
CFLAGS += -fPIC -g

all: libcutils.so

libcutils.so: $(libcutils_OBJS) 
	$(CC) $(CFLAGS) $(libcutils_OBJS) -o $@ $(LDFLAGS) -shared 

clean:
	$(RM) $(libcutils_OBJS)
