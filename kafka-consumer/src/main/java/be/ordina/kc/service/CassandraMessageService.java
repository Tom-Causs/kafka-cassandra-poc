package be.ordina.kc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.Session;

@Service
public class CassandraMessageService implements MessageService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CassandraMessageService.class);
	
	private final Session session = CassandraSessionFactory.getInstance();
	
	@Override
	public void saveMessage(long id, String message) {
		session.execute("INSERT INTO messages (id,  message) VALUES (" + id + ", '" + message + "');");
    	LOG.trace("saving message with id: {}, text: {}", id, message);
	}

}
