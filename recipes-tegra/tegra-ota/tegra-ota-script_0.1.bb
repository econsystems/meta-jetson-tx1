CRIPTION = "Interface test for beagle bone black board"
LICENSE = "Proprietary"
SECTION="beagle bone black"

LIC_FILES_CHKSUM = "file://ostree-conf-parser.sh;md5=9a04b5fee0e5b751a5dcb6a169bc00d4"
PR = "r3"

#INSANE_SKIP_${PN}-dev = "ldflags"
RDEPENDS_${PN} = "bash"

SRC_URI = "\
	file://ostree-conf-parser.sh    \
	file://stopwatchdog.sh		\
"

S = "${WORKDIR}"

do_install () {
	install -d ${D}/home
        install -d ${D}/home/root
	install -m 0755 ${WORKDIR}/stopwatchdog.sh ${D}/home/root
        install -m 0755 ${WORKDIR}/ostree-conf-parser.sh ${D}/home/root
}

FILES_${PN} += "\
	 /home/root/stopwatchdog.sh          \
        /home/root/ostree-conf-parser.sh \
"
