require tegra-binaries-${PV}.inc
require tegra-shared-binaries.inc

DEPENDS = "\
	gstreamer1.0-plugins-base \
	${@bb.utils.contains('DISTRO_FEATURES', ['x11', 'alsa'], 'virtual/libx11 alsa-lib', '', d)} \
"

do_configure() {
    tar -C ${B} -x -f ${S}/nv_tegra/nv_sample_apps/nvgstapps.tbz2
}

do_compile[noexec] = "1"

LIBROOT = "${B}/usr/lib/aarch64-linux-gnu"

NVGSTCAPTURE = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'nvgstcapture', '', d)}"
NVGSTPLAYER = "${@bb.utils.contains('DISTRO_FEATURES', ['x11', 'alsa'], 'nvgstplayer', '', d)}"

do_install() {
    if [ -n "${NVGSTCAPTURE}" ]; then
        install -d ${D}${bindir}
        install -m 0755 ${B}/usr/bin/nvgstcapture-1.0 ${D}${bindir}
    fi
    if [ -n "${NVGSTPLAYER}" ]; then
        install -d ${D}${bindir}
        install -m 0755 ${B}/usr/bin/nvgstplayer-1.0 ${D}${bindir}
    fi
    install -d ${D}${libdir}/gstreamer-1.0
    install -m 0644 ${LIBROOT}/libgstnvegl-1.0.so.0 ${D}${libdir}
    install -m 0644 ${LIBROOT}/libgstnvivameta.so ${D}${libdir}
    install -m 0644 ${LIBROOT}/libgstnvexifmeta.so ${D}${libdir}
    install -m 0644 ${LIBROOT}/libnvsample_cudaprocess.so ${D}${libdir}
    for f in ${LIBROOT}/gstreamer-1.0/lib*; do
        install -m 0644 $f ${D}${libdir}/gstreamer-1.0/
    done
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnveglglessink.so*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstomx.so*
}

PACKAGES = "${NVGSTCAPTURE} ${NVGSTPLAYER} ${PN}"
FILES_nvgstcapture = "${bindir}/nvgstcapture-1.0"
RDEPENDS_nvgstcapture = "${PN} libgstpbutils-1.0"
FILES_nvgstplayer = "${bindir}/nvgstplayer-1.0"
RDEPENDS_nvgstplayer = "${PN}"
FILES_${PN} = "${libdir}"
DEBIAN_NOAUTONAME_${PN} = "1"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP_${PN} = "dev-so ldflags build-deps"
INSANE_SKIP_${PN}-dev = "ldflags build-deps"
INSANE_SKIP_nvgstcapture = "ldflags build-deps"
INSANE_SKIP_nvgstplayer = "ldflags build-deps"
RDEPENDS_${PN} = "gstreamer1.0 gstreamer1.0-plugins-good-jpeg tegra-libraries"
