package be.ordina.kc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;

@Service
public class CassandraSessionFactory {

	private static final String HOST_URL = "192.168.33.10";
	private static final String DATABASE = "messages";

	private static final Logger LOG = LoggerFactory.getLogger(CassandraSessionFactory.class);
	
	private Cluster cluster;
	private Session session;

	public Session getInstance() {
		if (session == null) {
			session = startConnection();
		}
		return session;
	}

	private Session startConnection() {
		try {
			LOG.info("Start connection to cassandra.");
			cluster = Cluster.builder().addContactPoint(HOST_URL).build();
			
			Metadata metadata = cluster.getMetadata();
			LOG.info("Connected to cluster: {}", metadata.getClusterName());
			for (Host host : metadata.getAllHosts()) {
				LOG.info("Datacenter: " + host.getDatacenter() + "; Host: " + host.getAddress() + "; Rack: " + host.getRack());
			}
			
			try {
				session = cluster.connect(DATABASE);
			} catch (InvalidQueryException iqe) {
				session = cluster.connect();
				createKeyspace();
				createUsertable();
			}
			
			LOG.info("Succesfully connected to Cassandra.");
			
			return session;
		} catch (NoHostAvailableException cassandraException) {
			LOG.error("Can't connect to Cassandra on : " + HOST_URL, cassandraException);
			throw cassandraException;
		}
	}
	
	private void createKeyspace() {
		LOG.info("Creating keyspace: {}", DATABASE);
		session.execute("CREATE KEYSPACE " + DATABASE + " WITH replication " + 
			      "= {'class':'SimpleStrategy', 'replication_factor':3};");
		
		session.execute("USE " + DATABASE + ";");
		
	}
	
	private void createUsertable() {
		LOG.info("Creating table for users.");
		session.execute("CREATE TABLE users (user_id int PRIMARY KEY,fname text,lname text);");
		
	}
	
	public void destroyInstance(){
		if (!cluster.isClosed()) {
			cluster.close();
		}
	}
	
}
