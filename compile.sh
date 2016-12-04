#!/bin/sh

pushd common
mvn install
popd

pushd server
make release
popd

pushd client
make release
popd
