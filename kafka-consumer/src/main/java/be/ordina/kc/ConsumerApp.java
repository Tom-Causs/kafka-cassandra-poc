package be.ordina.kc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import be.ordina.kc.service.KafkaConsumerService;

@SpringBootApplication
public class ConsumerApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(KafkaConsumerService.class, args);
        
        KafkaConsumerService kafkaConsumerService = new KafkaConsumerService();
        kafkaConsumerService.consumeMessages();
    }
    
}
