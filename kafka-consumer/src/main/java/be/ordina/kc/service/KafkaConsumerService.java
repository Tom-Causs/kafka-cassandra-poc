package be.ordina.kc.service;

import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService implements ConsumerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	private static final String ZOOKEEPER_HOST = "192.168.33.10:2181";
	private static final String GROUP_ID = "ordina";
	private static final String TOPIC = "dropbox";
	
	private static long startTime;
	private static long endTime;
	
	@Autowired
	private MessageService messageService;
	
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
        	LOG.debug("received message: {}", message);
        	
        	switch (message) {
				case "[START]": // signal to start timer
					startTime = System.currentTimeMillis();
					LOG.debug("starting timer...");
					break;
				case "[END]": // stops the timer
					endTime = System.currentTimeMillis();
					LOG.debug("elapsed time: {}ms", (endTime - startTime));
					break;
				case "[SHUTDOWN]": // shuts down the consumer
					LOG.warn("Received shutdown signal. Shutting down...");
					consumer.shutdown();
					break;
				default: // SAVE THE MESSAGE
					saveMessage(message);
					break;
        	}
        }

        if (consumer != null) {
            consumer.shutdown();
        }
    }
    
    private void saveMessage(String message) {
    	long id = new Date().getTime();
    	messageService.saveMessage(id, message);
    	LOG.trace("saving message with id: {}, text: {}", id, message);
    }
    
    private ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", ZOOKEEPER_HOST);
        props.put("group.id", GROUP_ID);
        props.put("zookeeper.session.timeout.ms", "500");

        return new ConsumerConfig(props);
    }
    
}
