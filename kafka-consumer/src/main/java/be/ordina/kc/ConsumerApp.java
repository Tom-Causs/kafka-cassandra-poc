package be.ordina.kc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import be.ordina.kc.service.ConsumerService;

@SpringBootApplication
@EnableAsync
public class ConsumerApp {

    public static void main(String[] args) throws Exception {
    	ConfigurableApplicationContext ctx = SpringApplication.run(ConsumerApp.class, args);
    	
        ConsumerService consumerService = ctx.getBean(ConsumerService.class);
        consumerService.consumeMessages();
    }
    
}
