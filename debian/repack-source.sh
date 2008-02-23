#!/bin/sh

if [ $# -ne 3 ]
then
    echo "Usage: $0 option version filename"
    echo "If option=--upstream-version, run uupdate after repacking sources."
    exit
fi

VERSION=$2
FILENAME=$3

TMPDIR=`mktemp -d omegat-tmp`
BASEDIR=$TMPDIR/omegat-${VERSION}.dfsg0
mkdir "$BASEDIR"
unzip -d "$BASEDIR" "$FILENAME"

# Remove third-party libraries
rm -r "$BASEDIR"/lib

# Remove win32 executables
rm "$BASEDIR"/release/win32-specific/*exe

# Repack
GZIP=-9 tar -C "$TMPDIR" -czf ../omegat_${VERSION}.dfsg0.orig.tar.gz "omegat-${VERSION}.dfsg0"

# Clean temporary files
rm -rf "$TMPDIR"
rm -rf "$BASEDIR"
rm -f "$FILENAME"

if [ $1 = --upstream-version ] ;
then
    uupdate --upstream-version $2 $3
fi
