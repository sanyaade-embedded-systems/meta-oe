DEPENDS += "dri2proto expat libdrm makedepend-native"

#not supported in oe-core base.bbclass
#FILESPATHPKG =. "mesa-${PV}:mesa:"
FILESPATH =. "${FILE_DIRNAME}/mesa:${FILE_DIRNAME}/mesa-${PV}:"

# most of our targets do not have DRI so will use mesa-xlib
DEFAULT_PREFERENCE = "-1"

EXTRA_OECONF += " --with-driver=dri --with-dri-drivers=swrast,${MACHINE_DRI_MODULES}"
