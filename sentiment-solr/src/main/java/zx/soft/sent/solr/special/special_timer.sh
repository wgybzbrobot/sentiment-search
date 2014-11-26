#!/bin/bash

echo "Starting ..."
while (true)
do
    java -jar sentiment-solr-2.2.0-jar-with-dependencies.jar specialTopicRun
    sleep 1h
done
echo "Finishing ..."
