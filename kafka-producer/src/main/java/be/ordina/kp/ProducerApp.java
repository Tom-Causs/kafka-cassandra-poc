package be.ordina.kp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import be.ordina.kp.service.ProducerService;

@SpringBootApplication
public class ProducerApp {
	
    public static void main(String[] args) throws Exception {
    	ConfigurableApplicationContext ctx = SpringApplication.run(ProducerApp.class, args);
    	
        ProducerService producerService = ctx.getBean(ProducerService.class);
        producerService.produceMessages();
    }
    
}
