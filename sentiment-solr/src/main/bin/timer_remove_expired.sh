#!/bin/bash

echo "Starting ..."

bin/ctl.sh start removeExpiredData

while (true)
do
    sleep 7d
    bin/ctl.sh start removeExpiredData
done

echo "Finishing ..."
