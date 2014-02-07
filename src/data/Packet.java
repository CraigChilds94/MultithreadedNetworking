package data;

import java.net.InetAddress;

import server.ClientConnection;

public class Packet {

	private byte[] data;
	private InetAddress ip;
	private int port;
	
	/**
	 * Create a new packet
	 * @param data The data to send
	 * @param reciever The packets' reciever
	 */
	public Packet(byte[] data, ClientConnection receiver) {
		this.data = data;
		this.ip = receiver.getAddress();
		this.port = receiver.getPort();
	}
	
	/**
	 * Create a new packet with simple information about the client
	 * @param data
	 * @param ip
	 * @param port
	 */
	public Packet(byte[] data, InetAddress ip, int port) {
		this.data = data;
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * Get the packet data
	 * @return the packet data
	 */
	public byte[] getData() {
		return data;
	}
	
	/**
	 * Get the destination ip
	 * @return the address of the receiver
	 */
	public InetAddress getDestinationAddr() {
		return ip;
	}
	
	/**
	 * Get the destination port
	 * @return the receivers port
	 */
	public int getDestinationPort() {
		return port;
	}

}
