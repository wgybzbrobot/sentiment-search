# !/bin/bash

echo "Starting ..."
bin/ctl.sh start sinaPublicWeibosSpider
while (true)
do
    bin/ctl.sh restart sinaPublicWeibosSpider
    sleep 30m
done
echo "Finishing ..."
