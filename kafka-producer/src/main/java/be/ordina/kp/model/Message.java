package be.ordina.kp.model;

public class Message {

	private long timestamp;
	private String street;
	private String city;
	private String ipAddress;
	
	public Message() {
		super();
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "Message [timestamp=" + timestamp + ", street=" + street
				+ ", city=" + city + ", ipAddress=" + ipAddress + "]";
	}

}
