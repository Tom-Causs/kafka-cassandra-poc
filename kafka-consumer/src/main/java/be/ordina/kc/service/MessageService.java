package be.ordina.kc.service;

import org.springframework.scheduling.annotation.Async;

public interface MessageService {

	@Async
	void saveMessage(long id, String message);
	
}
