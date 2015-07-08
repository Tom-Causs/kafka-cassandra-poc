package be.ordina.kc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraSessionFactoryTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(CassandraSessionFactoryTest.class);

	private CassandraSessionFactory cassandraSessionFactory;
	private Session cassandraSession;
	
	@Before
	public void init(){
		cassandraSessionFactory = new CassandraSessionFactory();
		cassandraSession = cassandraSessionFactory.getInstance();
	}
	
	@After
	public void destroy(){
		cassandraSessionFactory.destroyInstance();
	}
	
	@Test
	public void testConnection(){
		//Successful if no exception is thrown during init.
	}
	
	@Test
	public void testWriteReadDelete() {
		// insert dummy data
		cassandraSession.execute("INSERT INTO users (user_id,  fname, lname) VALUES (1111, 'Luc', 'De Cleene');");
		cassandraSession.execute("INSERT INTO users (user_id,  fname, lname) VALUES (2222, 'Tom', 'Van den Bulck');");
		cassandraSession.execute("INSERT INTO users (user_id,  fname, lname) VALUES (3333, 'Chris', 'De Bruyne');");
		
		ResultSet results = cassandraSession.execute("SELECT * FROM users;");
		List<Row> resultList = results.all();
		
		assertEquals(3, resultList.size());
		
		for (Row row : resultList) {
			assertNotNull(row.getInt("user_id"));
			assertNotNull(row.getString("fname"));
			assertNotNull(row.getString("lname"));
			LOG.debug(row.getInt("user_id") + " : " + row.getString("fname") + " " + row.getString("lname"));
		}
		
		// clean up after test
		cassandraSession.execute("TRUNCATE users;");
		
		results = cassandraSession.execute("SELECT * FROM users;");
		resultList = results.all();
		
		assertEquals(0, resultList.size());
	}
	
}
