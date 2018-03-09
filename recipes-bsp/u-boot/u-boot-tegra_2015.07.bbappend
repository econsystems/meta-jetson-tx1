SRC_URI += "file://u-boot-tegra_econota_support.patch"
do_compile_append() {
	oe_runmake p2371-2180_defconfig
	oe_runmake
}
