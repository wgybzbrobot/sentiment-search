#!/bin/bash

echo "Starting ..."

bin/ctl.sh start importRedisToSC

while (true)
do
    sleep 1h
    bin/ctl.sh restart importRedisToSC
done

echo "Finishing ..."
