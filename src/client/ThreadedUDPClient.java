package client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * A class for handling a multi-threaded instance of a UDP client
 * @author craig
 *
 */
public class ThreadedUDPClient implements Runnable {
	
	private ServerConnection connection;
	private boolean running;
	
	private DatagramSocket socket;
	private Thread process, send;
	
	public ThreadedUDPClient(String addr, int port) {
		try {
			socket = new DatagramSocket();
			connection = new ServerConnection(socket, InetAddress.getByName(addr), port);
			this.init();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialise the client
	 */
	private void init() {
		process = new Thread(this, "server_process");
		process.start();
	}
	
	/**
	 * Send some data
	 * @param the data
	 */
	public void send(final byte[] data) {
		send = new Thread("Sending Thread") {
			public void run() {
				connection.send(data);
			}
		};
		
		send.start();
	}
	
	/**
	 * Receive data on the given server connection
	 * @return
	 */
	public byte[] receive() {
		return connection.receive();
	}
	
	/**
	 * Close the current connection for this client
	 */
	public void close() {
		connection.close();
		running = false;
	}
	
	@Override
	public void run() {
		running = true;
	}

}
