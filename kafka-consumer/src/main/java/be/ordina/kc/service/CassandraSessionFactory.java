package be.ordina.kc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;

@Service
public class CassandraSessionFactory {

	private static final String HOST_URL = "192.168.33.10";
	private static final String DATABASE = "ordina";
	private static final String USERS_TABLE = "users";
	private static final String MESSAGES_TABLE = "messages";

	private static final Logger LOG = LoggerFactory.getLogger(CassandraSessionFactory.class);
	
	private static Cluster cluster;
	private static Session session;

	public static Session getInstance() {
		if (session == null) {
			session = startConnection();
		}
		return session;
	}

	private static Session startConnection() {
		try {
			LOG.info("Start connection to cassandra.");
			cluster = Cluster.builder().addContactPoint(HOST_URL).build();
			
			Metadata metadata = cluster.getMetadata();
			LOG.info("Connected to cluster: {}", metadata.getClusterName());
			
			try {
				session = cluster.connect(DATABASE);
			} catch (InvalidQueryException iqe) {
				session = cluster.connect();
				createKeyspace();
				createUsersTable();
				createMessagesTable();
			}
			
			LOG.info("Succesfully connected to Cassandra.");
			
			return session;
		} catch (NoHostAvailableException cassandraException) {
			LOG.error("Can't connect to Cassandra on : " + HOST_URL, cassandraException);
			throw cassandraException;
		}
	}
	
	private static void createKeyspace() {
		LOG.info("Creating keyspace: {}", DATABASE);
		session.execute("CREATE KEYSPACE " + DATABASE + " WITH replication " + 
			      "= {'class':'SimpleStrategy', 'replication_factor':3};");
		
		session.execute("USE " + DATABASE + ";");
	}
	
	private static void createMessagesTable() {
		LOG.info("Creating table - {}", MESSAGES_TABLE);
		session.execute("CREATE TABLE " + MESSAGES_TABLE + " (id bigint PRIMARY KEY, message text);");
	}
	
	private static void createUsersTable() {
		LOG.info("Creating table - {}", USERS_TABLE);
		session.execute("CREATE TABLE " + USERS_TABLE + " (user_id int PRIMARY KEY,fname text,lname text);");
	}
	
	public static void destroyInstance(){
		if (!cluster.isClosed()) {
			cluster.close();
		}
	}
	
}
