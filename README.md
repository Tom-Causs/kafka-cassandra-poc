Kafka - Cassandra demo
==========

The app consists of two parts: 

   - Kafka Producer - generates random messages and sends them to Kafka
   - Kafka Consumer - reads messages from Kafka topic and stores them in Cassandra

### Requirements

   - Java 8
   - Git
   - Maven
   - [VirtualBox]
   - [Vagrant]

### Setup

  To run the app, first execute the following cmd from the project root:
  ```sh
  vagrant up
  ```
  This will start up the Vagrant box. The first time will take a while as it has to download the OS image and other dependencies.
  
  To start-up the producer app, run the following cmd from the **kafka-producer** folder: 
  ```sh
  $ mvn spring-boot:run
  ```
  _Note: the producer will terminate after the messages have been sent_
  
  To start-up the consumer app, run the following cmd from the **kafka-consumer** folder: 
  ```sh
  $ mvn spring-boot:run
  ```
  _Note: the consumer will keep listening for new messages until manually terminated_
  
  Shutting down the vagrant box can be done by typing
  ```sh
  vagrant halt
  ```


### Misc. commands

Creating a Kafka topic: 
```sh
./kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic myTopic
```

List all Kafka topics:
```sh
./kafka/bin/kafka-topics.sh --list --zookeeper localhost:2181
```

Run the console producer:
```sh
./kafka/bin/kafka-console-producer.sh --broker-list 192.168.33.10:9092 --topic myTopic
```

Run the console consumer:
```sh
./kafka/bin/kafka-console-consumer.sh --zookeeper 192.168.33.10:2181 --topic myTopic --from-beginning
```

Run Cassandra terminal client: 
```sh
./cassandra/bin/cqlsh
```

### About

A simple demo to show how Kafka works together with Cassandra

Used technologies: 

 - Spring Boot
 - Apache Kafka
 - Cassandra


[VirtualBox]:https://www.virtualbox.org/
[Vagrant]:https://www.vagrantup.com/
