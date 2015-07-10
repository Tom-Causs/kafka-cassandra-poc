package be.ordina.kp.service;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import be.ordina.kp.model.Message;
import be.ordina.kp.util.AddressUtil;

@Service
public class KafkaProducerService implements ProducerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);
	
	private static final String KAFKA_URL = "192.168.33.10:9092";
	private static final String TOPIC = "dropbox";
	
	Producer<String, String> producer;
	
	public void produceMessages() {
        Properties props = new Properties();
        
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_URL);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        producer = new KafkaProducer<>(props);
        
        long start = System.currentTimeMillis();
        
        // send start signal so consumer can start the timer
        sendMessage(TOPIC, "[START]");

    	// generate random messages
        for (int i = 0; i < 20000; i++) {
            sendMessage(TOPIC, generateMessage());
		}
        
        // stop the timer
        sendMessage(TOPIC, "[END]");
        
        LOG.trace("closing connection...");
        producer.close();
        
        long end = System.currentTimeMillis();
        
        LOG.trace("elapsed time: {}ms", (end - start));
    }
	
	private void sendMessage(String topic, String message) {
        ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, message);
        
        LOG.debug("sending message: {}", message);
        producer.send(data);
	}

	private static String generateMessage() {
        long timestamp = new Date().getTime();
        
        Message message = new Message();
        message.setTimestamp(timestamp);
        message.setStreet(AddressUtil.generateStreet());
        message.setCity(AddressUtil.generateCity());
        message.setIpAddress(AddressUtil.generateIpAddress());
        
        return message.toString();
	}
}
