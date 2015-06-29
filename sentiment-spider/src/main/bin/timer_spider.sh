# !/bin/bash

echo "Starting ..."

bin/ctl.sh start sinaPublicWeibosSpider

while (true)
do
    sleep 30m
    bin/ctl.sh restart sinaPublicWeibosSpider
done

echo "Finishing ..."
