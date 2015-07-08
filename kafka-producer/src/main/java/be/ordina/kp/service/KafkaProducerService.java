package be.ordina.kp.service;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService implements ProducerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);
	
	private static final String KAFKA_URL = "192.168.33.10:9092";
	private static final String TOPIC = "dropbox";

	public void produceMessages() {
        Properties props = new Properties();
        
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_URL);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        Producer<String, String> producer = new KafkaProducer<>(props);
        
        String msg = generateMessage();
        
        ProducerRecord<String, String> data = new ProducerRecord<String, String>(TOPIC, msg);
        
        LOG.debug("sending message: {}", msg);
        producer.send(data);
        
        LOG.trace("closing connection...");
        producer.close();
    }

	private static String generateMessage() {
		Random rnd = new Random();
        long runtime = new Date().getTime();
        String ip = "192.168.2." + rnd.nextInt(255);
        String message = runtime + ",www.example.com," + ip;
        
        return message;
	}
}
