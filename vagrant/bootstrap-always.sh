#!/bin/bash

# start cassandra
./cassandra/bin/cassandra > /dev/null

# start zookeeper & kafka
cd kafka/
nohup ./bin/zookeeper-server-start.sh config/zookeeper.properties > /dev/null &
nohup ./bin/kafka-server-start.sh config/server.properties > /dev/null &

# configure kafka
sleep 5
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic dropbox
