SUMMARY = "Linux Bluetooth Stack Userland V4"
DESCRIPTION = "Linux Bluetooth stack V4 userland components.  These include a system configurations, daemons, tools and system libraries."
HOMEPAGE = "http://www.bluez.org"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "GPLv2+ & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e \
                    file://COPYING.LIB;md5=fb504b67c50331fc78734fed90fb0e09 \
                    file://src/main.c;beginline=1;endline=24;md5=9bc54b93cd7e17bf03f52513f39f926e \
                    file://sbc/sbc.c;beginline=1;endline=25;md5=1a40781ed30d50d8639323a184aeb191"
DEPENDS = "gst-plugins-base alsa-lib libusb dbus-glib libnl"
RDEPENDS_${PN}-dev = "bluez-hcidump"

ASNEEDED = ""

PR = "r9"

# to find bluez patches from oe-core
FILESPATH =. "${COREBASE}/meta/recipes-connectivity/bluez/bluez4-4.82/:"
SRC_URI = "\
  ${KERNELORG_MIRROR}/linux/bluetooth/bluez-${PV}.tar.gz \
  file://fix-dfutool-usb-declaration-mismatch.patch \
  file://bluetooth.conf \
"

SRC_URI[md5sum] = "3059b7ef5168c84cd0c6a67034ce79f9"
SRC_URI[sha256sum] = "11e9279e2669db996afd464b96d2c68f41f157f6eb9b8842a0bbcad8a4eac18d"
S = "${WORKDIR}/bluez-${PV}"

inherit autotools

EXTRA_OECONF = "\
  --enable-gstreamer \
  --enable-alsa \
  --enable-usb \
  --enable-tools \
  --enable-bccmd \
  --enable-hid2hci \
  --enable-dfutool \
  --enable-hidd \
  --enable-pand \
  --enable-dund \
  --disable-cups \
  --enable-test \
  --enable-configfiles \
"

do_install_append() {
	install -m 0644 ${S}/audio/audio.conf ${D}/${sysconfdir}/bluetooth/
	install -m 0644 ${S}/network/network.conf ${D}/${sysconfdir}/bluetooth/
	install -m 0644 ${S}/input/input.conf ${D}/${sysconfdir}/bluetooth/
	# at_console doesn't really work with the current state of OE, so punch some more holes so people can actually use BT
	install -m 0644 ${WORKDIR}/bluetooth.conf ${D}/${sysconfdir}/dbus-1/system.d/
}

PACKAGES =+ "gst-plugin-bluez libasound-module-bluez"

FILES_gst-plugin-bluez = "${libdir}/gstreamer-0.10/lib*.so"
FILES_libasound-module-bluez = "${libdir}/alsa-lib/lib*.so"
FILES_${PN} += "${libdir}/bluetooth/plugins/*.so"
FILES_${PN}-dev += "\
  ${libdir}/bluetooth/plugins/*.la \
  ${libdir}/alsa-lib/*.la \
  ${libdir}/gstreamer-0.10/*.la \
"

FILES_${PN}-dbg += "\
  ${libdir}/bluetooth/plugins/.debug \
  ${libdir}/*/.debug \
"
