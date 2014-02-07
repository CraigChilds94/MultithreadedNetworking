package server;

import java.net.InetAddress;

public class ClientConnection {
	
	private InetAddress addr;
	private int port, id;
	
	/**
	 * Create a new client connection object with the address and port of the connected client
	 * @param addr
	 * @param port
	 */
	public ClientConnection(InetAddress addr, int port, int id) {
		this.addr = addr;
		this.port = port;
		this.id = id;
	}
	
	/**
	 * Get the ip of the client
	 * @return the ip address
	 */
	public InetAddress getAddress() {
		return addr;
	}

	/**
	 * Get the port of the client
	 * @return port
	 */
	public int getPort() {
		return port;
	}
	
}
