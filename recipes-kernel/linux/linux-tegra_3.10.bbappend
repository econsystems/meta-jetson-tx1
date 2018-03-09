SRC_URI += "https://releases.linaro.org/archive/14.03/components/toolchain/binaries/gcc-linaro-arm-linux-gnueabihf-4.8-2014.03_linux.tar.bz2  \
	   file://jetson_tx1_fix_build_issue.patch "

SRC_URI[md5sum] = "0bbf37c6e26f0859be9bfa9fa86dd43a"
do_compile_prepend () {
	echo compiling 
	export CROSS32CC=${WORKDIR}/gcc-linaro-arm-linux-gnueabihf-4.8-2014.03_linux/bin/arm-linux-gnueabihf-gcc
	echo $CROSS32CC
	echo $PATH
	export PATH=$PATH:${WORKDIR}/gcc-linaro-arm-linux-gnueabihf-4.8-2014.03_linux/bin/
}
