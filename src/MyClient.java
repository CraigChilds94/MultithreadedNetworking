import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;

import client.ThreadedUDPClient;
import data.Packet;
import data.PacketHandler;


/**
 * My implementation of the client class
 * @author Craig
 *
 */
public class MyClient implements Client {

	private ThreadedUDPClient client;
	public static Response lastResponse;
	
	public void resetResponse() {
		MyClient.lastResponse = null;
	}
	
	@Override
	public void start(String serverAddress, int serverPort) throws NetException {
		try {
			// Start the client
			this.client = new ThreadedUDPClient(serverAddress, serverPort);
		} catch (SocketException | UnknownHostException e) {
			// Catch any errors
			throw new NetException(e.getMessage());
		}		
	}

	@Override
	public Response connect() throws NetException {
		this.resetResponse();
		this.client.send(Protocol.makePacket(Protocol.CON, "").getBytes());
		this.client.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				// Extract the data from the packet received
				String packetData = packet.getDataAsString(true);
				int header = Protocol.getPacketHeader(packetData);
				String data = Protocol.getPacketData(packetData);

				// Check for the headers
				switch(header) {
					case Protocol.OK:     MyClient.lastResponse = new OK();
									 	  break;
					case Protocol.NOT_OK: MyClient.lastResponse = new CannotConnect();
			 	  	  					  break;
					case Protocol.ERR:    MyClient.lastResponse = new Problem(data);
				 	  					  break;
					case Protocol.BLOCKED:MyClient.lastResponse = new ClientBlocked();
										  break;
				 	default:			  MyClient.lastResponse = new Problem(data);
				 						  break;
				}
				
			}
			
		});
		
		return MyClient.lastResponse;
	}

	@Override
	public Response sendPassword(String pw) throws NetException {
		this.resetResponse();
		this.client.send(Protocol.makePacket(Protocol.PW, pw).getBytes());
		this.client.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				// Extract the data from the packet received
				String packetData = packet.getDataAsString(true);
				int header = Protocol.getPacketHeader(packetData);
				String data = Protocol.getPacketData(packetData);
				
				// Check for the headers
				switch(header) {
					case Protocol.PW_AUTH_OK:     MyClient.lastResponse = new OK();
									 	  	      break;
					case Protocol.PW_AUTH_NOT_OK: MyClient.lastResponse = new AuthenticationFailed();
			 	  	  						      break;
					case Protocol.ERR:   	      MyClient.lastResponse = new Problem(data);
				 	  					          break;
					case Protocol.BLOCKED:        MyClient.lastResponse = new ClientBlocked();
					  							  break;
				 	default:			          MyClient.lastResponse = new Problem(data);
				 						          break;
				}
			}
			
		});
		
		return MyClient.lastResponse;
	}

	@Override
	public void clientExit() throws NetException {
		this.resetResponse();
		this.client.send(Protocol.makePacket(Protocol.EXIT, "").getBytes());
		this.client.close();
	}

	@Override
	public Response serverExit() throws NetException {
		return null;
	}

	@Override
	public Response listDirectory() throws NetException {
		this.resetResponse();
		this.client.send(Protocol.makePacket(Protocol.DIR_REQ, "").getBytes());
		this.client.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				// Extract the data from the packet received
				String packetData = packet.getDataAsString(true);
				int header = Protocol.getPacketHeader(packetData);
				String data = Protocol.getPacketData(packetData);
				
				// Check for the headers
				switch(header) {
					case Protocol.DIR_LIST: MyClient.lastResponse = new DirectoryListing(data);
									 	  break;
					case Protocol.ERR:    MyClient.lastResponse = new CannotRecieveFile();
				 	  					  break;
					case Protocol.BLOCKED:MyClient.lastResponse = new ClientBlocked();
					  					  break;
				 	default:			  MyClient.lastResponse = new Problem(data);
				 						  break;
				}
			}
			
		});
		return null;
	}

	@Override
	public Response sendFile(String fileName, String fileContent) throws NetException {
		this.resetResponse();
		String packet = Protocol.makePacket(Protocol.FILE_SEND, Protocol.formatFileData(fileName, fileContent));
		this.client.send(packet.getBytes());
		
		this.client.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				// Extract the data from the packet received
				String packetData = packet.getDataAsString(true);
				int header = Protocol.getPacketHeader(packetData);
				String data = Protocol.getPacketData(packetData);
				
				// Check for the headers
				switch(header) {
					case Protocol.FILE_SEND:MyClient.lastResponse = new FileContent(data);
									 	  break;
					case Protocol.ERR:    MyClient.lastResponse = new CannotSendFile();
				 	  					  break;
					case Protocol.BLOCKED:MyClient.lastResponse = new ClientBlocked();
					  					  break;
				 	default:			  MyClient.lastResponse = new Problem(data);
				 						  break;
				}
			}
			
		});
		
		return MyClient.lastResponse;
	}

	@Override
	public Response receiveFile(String fileName) throws NetException {
		this.resetResponse();
		this.client.send(Protocol.makePacket(Protocol.FILE_REC, fileName).getBytes());
		this.client.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				// Extract the data from the packet received
				String packetData = packet.getDataAsString(true);
				int header = Protocol.getPacketHeader(packetData);
				String data = Protocol.getPacketData(packetData);
				
				// Check for the headers
				switch(header) {
					case Protocol.OK:     MyClient.lastResponse = new OK();
									 	  break;
					case Protocol.ERR:    MyClient.lastResponse = new Problem(data);
				 	  					  break;
					case Protocol.BLOCKED:MyClient.lastResponse = new ClientBlocked();
					  					  break;
				 	default:			  MyClient.lastResponse = new Problem(data);
				 						  break;
				}
			}
			
		});
		
		return MyClient.lastResponse;
	}

	@Override
	public Response echoByteArray(byte[] ba) throws NetException {
		this.resetResponse();
		this.client.send(Protocol.makePacket(Protocol.ECHO_B, new String(ba)).getBytes());
		this.client.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				// Extract the data from the packet received
				String packetData = packet.getDataAsString(true);
				int header = Protocol.getPacketHeader(packetData);
				String data = Protocol.getPacketData(packetData);
				
				// Check for the headers
				switch(header) {
					case Protocol.ECHO_B: MyClient.lastResponse = new ByteArrayContent(data.getBytes());
									 	  break;
					case Protocol.ERR:    MyClient.lastResponse = new Problem(data);
				 	  					  break;
					case Protocol.BLOCKED:MyClient.lastResponse = new ClientBlocked();
					  					  break;
				 	default:			  MyClient.lastResponse = new Problem(data);
				 						  break;
				}
			}
			
		});
		
		return MyClient.lastResponse;
	}

	@Override
	public Response echoString(String s) throws NetException {
		this.resetResponse();
		this.client.send(Protocol.makePacket(Protocol.ECHO_S, s).getBytes());
		this.client.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				
				// Extract the data from the packet received
				String packetData = packet.getDataAsString(true);
				int header = Protocol.getPacketHeader(packetData);
				String data = Protocol.getPacketData(packetData);
				
				// Check for the headers
				switch(header) {
					case Protocol.ECHO_S: MyClient.lastResponse = new StringContent(data);
									 	  break;
					case Protocol.ERR:    MyClient.lastResponse = new Problem(data);
				 	  					  break;
					case Protocol.BLOCKED:MyClient.lastResponse = new ClientBlocked();
					  					  break;
				 	default:			  MyClient.lastResponse = new Problem(data);
				 						  break;
				}
			}
			
		});
		
		return MyClient.lastResponse;
	}

}
