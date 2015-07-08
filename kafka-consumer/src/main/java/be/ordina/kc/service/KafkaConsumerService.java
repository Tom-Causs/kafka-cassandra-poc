package be.ordina.kc.service;

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
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	private static final String ZOOKEEPER_HOST = "192.168.33.10:2181";
	private static final String GROUP_ID = "ordina";
	private static final String TOPIC = "dropbox";
	
	private ConsumerConnector consumer;

    public KafkaConsumerService() {
    	consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
    }
    
    public void consumeMessages() {
        Map<String, Integer> topicCountMap = new HashMap<>();
        StringDecoder decoder = new StringDecoder(new VerifiableProperties());
        topicCountMap.put(TOPIC, 1);
        Map<String, List<KafkaStream<String, String>>> consumerMap = 
        		consumer.createMessageStreams(topicCountMap, decoder, decoder);
        KafkaStream<String, String> stream = consumerMap.get(TOPIC).get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        
        while(it.hasNext()) {
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
        props.put("zookeeper.connect", ZOOKEEPER_HOST);
        props.put("group.id", GROUP_ID);
        props.put("zookeeper.session.timeout.ms", "500");

        return new ConsumerConfig(props);
    }
    
}
