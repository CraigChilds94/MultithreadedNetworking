package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import data.Packet;
import data.PacketHandler;

/**
 * A class for handling a multi-threaded instance of a UDP server
 * @author craig
 * @version 0.1
 */
public class ThreadedUDPServer implements Runnable {
	
	/* Server information */
	private int port;
	private DatagramSocket socket;
	private boolean running;
	
	/* Threads */
	private Thread send, receive, process;
	
	/* Client relevant */
	private ArrayList<ClientConnection> clients;
	
	
	/**
	 * Construct a new instance of a multi-threaded udp server
	 * @param port
	 */
	public ThreadedUDPServer(int port) {
		this.port = port;
		this.clients = new ArrayList<ClientConnection>();
		
		try {
			this.init();
		} catch (SocketException e) {
			System.err.println("Unable to initialise the server..." + e.getMessage());
		}
	}
	
	/**
	 * Initialise the server
	 * @throws SocketException 
	 */
	public void init() throws SocketException {
		this.socket = new DatagramSocket(this.port);
		process = new Thread(this, "server_process");
		process.start();
	}
	
	/**
	 * Get the port that the server is binded to
	 * @return port
	 */
	public int getPort() {
		return port;
	}
	
	
	/**
	 * Send a packet to a client
	 * @param packet
	 * @param client
	 */
	public void send(final Packet packet) {
		send = new Thread("send_thread") {
			public void run() {
				DatagramPacket dgpack = new DatagramPacket(
						packet.getData(), 
						packet.getData().length, 
						packet.getDestinationAddr(), 
						packet.getDestinationPort()
				);
	
				try {
					socket.send(dgpack);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		send.start();
	}
	
	/**
	 * Send a packet to all connected clients
	 * @param packet
	 */
	public void broadcast(byte[] data) {
		for(ClientConnection c : clients) {
			send(new Packet(data, c));
		}
	}
	
	/**
	 * Wait for input... and use a PacketHandler to process the packet
	 * @param handler The packet handler 
	 */
	public void receive(final PacketHandler handler) {
		receive = new Thread("receive_thread") {
			public void run() {
				while(running) {
					byte[] buffer = new byte[1024];
					DatagramPacket dgpacket = new DatagramPacket(buffer, buffer.length);
					
					try {
						socket.receive(dgpacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					handler.process(new Packet(dgpacket.getData(), dgpacket.getAddress(), dgpacket.getPort()));
				}
			}
		};
	}

	/**
	 * The run method of this runnable thread
	 * object.
	 */
	public void run() {
		running = true;
		System.out.println("Server started on port " + port);
	}
}