#!/bin/bash

echo "Starting ..."

bin/ctl.sh start firstPageRun

while (true)
do
    sleep 5m
    bin/ctl.sh start firstPageRun
done

echo "Finishing ..."
