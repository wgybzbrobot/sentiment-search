#!/bin/bash

echo "Starting ..."

bin/ctl.sh start specialTopicRun

while (true)
do
    sleep 30m
    bin/ctl.sh start specialTopicRun
done

echo "Finishing ..."