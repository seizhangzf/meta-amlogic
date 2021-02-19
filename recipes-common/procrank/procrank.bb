# If not stated otherwise in this file or this component's Licenses.txt file the
# following copyright and licenses apply:
#
# Copyright 2016 RDK Management
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "procrank is a tool commonly used by Android platform. \ndevelopers to find out how much memory is really being used."
HOMEPAGE = "https://github.com/csimmonds/procrank_linux"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://MODULE_LICENSE_APACHE2;md5=d41d8cd98f00b204e9800998ecf8427e"
SECTION = "apps"
DEPENDS = ""

SRC_URI = "git://github.com/csimmonds/procrank_linux.git file://make.patch"
SRCREV = "21c30ab4514a5b15ac6e813e21bee0d3d714cb08"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} CC='${CC}'"

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${B}/procrank ${D}${bindir}
}

FILES_${PN} = "${bindir}/*"
INSANE_SKIP_${PN} = "ldflags"
