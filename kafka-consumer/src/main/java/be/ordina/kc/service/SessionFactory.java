package be.ordina.kc.service;

import com.datastax.driver.core.Session;

public interface SessionFactory {

	Session getInstance();
	
	void destroyInstance();
	
}
