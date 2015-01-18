#!/bin/bash

echo "Starting ..."

bin/ctl.sh start sentimentIndexServer

while (true)
do
    sleep 1h
    bin/ctl.sh restart sentimentIndexServer
done

echo "Finishing ..."
