# Assign it here, since the hal->udev transition happens post 1.7 in angstrom
DISTRO_XORG_CONFIG_MANAGER_angstrom = "udev"

require xorg-xserver-common.inc
LIC_FILES_CHKSUM = "file://COPYING;md5=74df27b6254cc88d2799b5f4f5949c00"

DESCRIPTION = "the X.Org X server"
DEPENDS += "pixman libpciaccess openssl dri2proto glproto xorg-minimal-fonts font-util-native"
PE = "2"
PR = "${INC_PR}.0"

SRC_URI += " \
            file://hack-fbdev-ignore-return-mode.patch \
            file://hack-assume-pixman-supports-overlapped-blt.patch \
           "
SRC_URI[md5sum] = "75f117c74f2ecaf9dd167f6a66ac98de"
SRC_URI[sha256sum] = "143c7c3d7d4428352e1153dffa34fd64af391f72d30b2a03e911e54e36f00b5d"

do_install_prepend() {
        mkdir -p ${D}/${libdir}/X11/fonts
}

# The NVidia driver requires Xinerama support in the X server. Ion uses it.
PACKAGE_ARCH_ion = "${MACHINE_ARCH}"
XINERAMA = "${@['--disable-xinerama','--enable-xinerama'][bb.data.getVar('MACHINE',d) in ['ion']]}"

EXTRA_OECONF += " ${CONFIG_MANAGER_OPTION} ${XINERAMA} --disable-kdrive --disable-xephyr --disable-xsdl --disable-xfake --disable-xfbdev --disable-dmx"
EXTRA_OECONF += " --enable-dri2 --disable-unit-tests --disable-docs --disable-devel-docs"

export LDFLAGS += " -ldl "
