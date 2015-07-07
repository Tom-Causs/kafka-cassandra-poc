package be.ordina.kc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerExample {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsumerExample.class);
	
	private static final String TOPIC = "dropbox";
	
	private ConsumerConnector consumer;

    public ConsumerExample() {
    	consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
    }
    
    public void runConsumer() throws InterruptedException {
        Map<String, Integer> topicCountMap = new HashMap<>();
        StringDecoder decoder = new StringDecoder(new VerifiableProperties());
        topicCountMap.put(TOPIC, 1);
        Map<String, List<KafkaStream<String, String>>> consumerMap = 
        		consumer.createMessageStreams(topicCountMap, decoder, decoder);
        KafkaStream<String, String> stream = consumerMap.get(TOPIC).get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        
        while(it.hasNext()) {
        	Thread.sleep(1000);
        	
        	String message = it.next().message();
        	LOG.info("message: {}", message);
        	
        	if (message.equals("SHUTDOWN")) {
        		LOG.warn("Received shutdown signal. Shutting down...");
        		consumer.shutdown();
        		return;
        	}
        }

        if (consumer != null) {
            consumer.shutdown();
        }
    }
    
    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "192.168.33.10:2181");
        props.put("group.id", "ordina");

        return new ConsumerConfig(props);
    }
    
}
