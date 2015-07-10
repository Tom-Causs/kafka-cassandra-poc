package be.ordina.kc.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    @Override
    public Executor getAsyncExecutor() {
	//int availableProcessors = Runtime.getRuntime().availableProcessors();
	int availableProcessors = 100;
	ExecutorService fixedThreadPoolExecutor = Executors.newFixedThreadPool(availableProcessors);
	return fixedThreadPoolExecutor;
    }
    
}
