#!/bin/bash

echo "Starting ..."

bin/ctl.sh start removeExpiredData

while (true)
do
    sleep 1d
    bin/ctl.sh start removeExpiredData
done

echo "Finishing ..."
