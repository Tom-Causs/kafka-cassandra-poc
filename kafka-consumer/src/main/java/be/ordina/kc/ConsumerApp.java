package be.ordina.kc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConsumerExample.class, args);
        
        ConsumerExample consumerExample = new ConsumerExample();
        consumerExample.runConsumer();
    }
    
}
