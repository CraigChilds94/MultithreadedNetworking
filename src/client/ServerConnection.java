package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This class represents a connection to the server
 * @author craig
 *
 */
public class ServerConnection {

	private InetAddress addr;
	private int port;
	private DatagramSocket clientSocket;
	
	/**
	 * Create a new server connection with the given details
	 * @param addr
	 * @param port
	 */
	public ServerConnection(DatagramSocket clientSocket, InetAddress addr, int port) {
		this.addr = addr;
		this.port = port;
		this.clientSocket = clientSocket;
	}
	
	/**
	 * Send some data on this server connection
	 * @param the data
	 */
	public void send(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
		
		try {
			clientSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recieve data on this connection
	 * @return the data
	 */
	public byte[] receive() {
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		try {
			clientSocket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] data =  packet.getData();
		return data;
	}
	
	/**
	 * Get the port number of the server
	 * @return port number
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Close the connection to the server
	 */
	public void close() {
		new Thread() {
			public void run() {
				synchronized(clientSocket) {
					clientSocket.close();
				}
			}
		}.start();
	}

}
