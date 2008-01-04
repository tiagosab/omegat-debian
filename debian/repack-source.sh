#!/bin/bash

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

rm -r "$BASEDIR"/lib
rm "$BASEDIR"/release/win32-specific/*exe 
# find "$BASEDIR" -type f -exec file {} \; | grep "CRLF, CR" | sed 's/:.*//' | xargs fromdos 
# find "$BASEDIR" -type f -exec file {} \; | grep CRLF | sed 's/:.*//' | xargs fromdos
GZIP=-9 tar -C "$TMPDIR" -czf ../omegat_${VERSION}.dfsg0.orig.tar.gz "omegat-${VERSION}.dfsg0"
rm -rf "$TMPDIR"

if [ $1 = --upstream-version ] ;
then
    uupdate --upstream-version $2 $3
fi
