#!/bin/bash

echo "Starting ..."

bin/ctl.sh start indexApiServer

while (true)
do
    sleep 1h
    bin/ctl.sh restart indexApiServer
done

echo "Finishing ..."
