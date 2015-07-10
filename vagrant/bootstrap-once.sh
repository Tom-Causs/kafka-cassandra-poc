#!/bin/bash

echo "updating packages..."
apt-get update -qq

echo "setting up java..."
apt-get install -y -qq openjdk-7-jre-headless > /dev/null

echo "setting up Cassandra..."
wget -q http://apache.cu.be/cassandra/2.1.7/apache-cassandra-2.1.7-bin.tar.gz
tar zxf apache-cassandra-2.1.7-bin.tar.gz
mv apache-cassandra-2.1.7 cassandra
cd cassandra/
mkdir logs/
chmod 777 logs/
mkdir data/
chmod 777 data/

# configure cassandra
sed -i "s|^rpc_address: localhost|rpc_address: 0.0.0.0|;s|^# broadcast_rpc_address: 1.2.3.4|broadcast_rpc_address: 1.2.3.4|" conf/cassandra.yaml

cd ..

echo "setting up Kafka..."
wget -q http://apache.cu.be/kafka/0.8.2.0/kafka_2.10-0.8.2.0.tgz
tar xf kafka_2.10-0.8.2.0.tgz
mv kafka_2.10-0.8.2.0 kafka
cd kafka/
mkdir logs/
chmod 777 logs/

# configure kafka/zookeeper
sed -i "s|^zookeeper.connect=localhost:2181|zookeeper.connect=192.168.33.10:2181|;s|^#host.name=localhost|host.name=192.168.33.10|" config/server.properties
