package be.ordina.kc.service;

import org.springframework.stereotype.Service;

import com.datastax.driver.core.Session;

@Service
public class CassandraMessageService implements MessageService {

	private final Session session = CassandraSessionFactory.getInstance();
	
	@Override
	public void saveMessage(long id, String message) {
		session.execute("INSERT INTO messages (id,  message) VALUES (" + id + ", '" + message + "');");
	}

}
