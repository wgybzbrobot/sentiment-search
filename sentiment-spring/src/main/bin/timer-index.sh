#!/bin/bash

echo "Starting ..."

bin/ctl.sh start indexApiServer

while (true)
do
    sleep 30m
    bin/ctl.sh restart indexApiServer
done

echo "Finishing ..."
