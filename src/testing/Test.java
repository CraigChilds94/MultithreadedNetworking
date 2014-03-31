package testing;

import static org.junit.Assert.*;

import java.net.SocketException;
import java.net.UnknownHostException;

import client.ThreadedUDPClient;
import data.Connection;
import data.Packet;
import data.PacketHandler;
import data.UID;
import server.ThreadedUDPServer;

public class Test {

	private ThreadedUDPServer server;
	private ThreadedUDPClient client;
	
	@org.junit.Test
	public void test() {
		// Create our server and client intances
		try {
			server = new ThreadedUDPServer(1337);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		try {
			client = new ThreadedUDPClient("localhost", 1337);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		
		// Set up a handler for receiving the packets
		server.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				String data = new String(packet.getData()).trim();
		
				if(data.equals("CON")) {
					ThreadedUDPServer.CLIENTS.add(packet.getConnection());
					reply(new Packet("OK".getBytes(), packet.getAddr(), packet.getPort()));
				}
			}
			
		});
		
		client.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				String data = new String(packet.getData()).trim();
				System.out.println(data.trim());
			}
			
		});
		
		client.send("CON".getBytes());
	}
	
	public void reply(Packet packet) {
		server.broadcast(new String(packet.toString()).getBytes());
	}

}
