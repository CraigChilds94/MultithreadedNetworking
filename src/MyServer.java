import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import data.Connection;
import data.Packet;
import data.PacketHandler;
import server.ThreadedUDPServer;


/**
 * My server implementation
 * @author Craig
 *
 */
public class MyServer implements Server {
	
	private ThreadedUDPServer server;
	private List<String> forbidden;
	private String password;

	@Override
	public void start(int port, String password, List<String> forbidden) throws NetException {
		this.forbidden = forbidden;
		this.password = password;
		
		try {
			// Start the server
			this.server = new ThreadedUDPServer(port);
		} catch (SocketException e) {
			// Catch any errors
			throw new NetException(e.getMessage());
		}
		
		this.server.listen(new ServerReceiver(this));
	}
	
	/**
	 * Get the server connection
	 * @return the server object
	 */
	public ThreadedUDPServer getServer() {
		return this.server;
	}
	
	/**
	 * Get the required password for this server
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Get the list of forbidden connections
	 * @return the forbidden list
	 */
	public List<String> getForbidden() {
		return this.forbidden;
	}
}

/**
 * Extension of the packet handler relevant to this assignment
 * Enables action 
 * @author Craig
 *
 */
class ServerReceiver extends PacketHandler {

	private MyServer main;
	private ThreadedUDPServer server;
	private List<String> forbidden;
	private String password;
	
	private InetAddress clientAddress = null;
	private int clientPort = 0;
	
	/**
	 * Construct the handler with knowledge about our server implementation
	 * @param server
	 */
	public ServerReceiver(MyServer server) {
		this.main = server;
		this.server = this.main.getServer();
		this.forbidden = this.main.getForbidden();
		this.password = this.main.getPassword();
	}
	
	@Override
	public void process(Packet packet) {
		
		// Get some information about this packet
		byte[] pData  = packet.getData();
		InetAddress addr = packet.getAddr();
		int port = packet.getPort();
		String sAddr = addr.getHostAddress();
		String packetData = packet.getDataAsString(true);
		int header = Protocol.getPacketHeader(packetData);
		String data = Protocol.getPacketData(packetData);
		
		// Checked to see if that client is allowed
		if(forbidden.contains(sAddr)) {
			this.server.send(new Packet(Protocol.makePacket(Protocol.BLOCKED, "").getBytes(), addr, port));
			return;
		}
		
		// Echo the string back
		if(header == Protocol.ECHO_S) {
			Packet p = new Packet(Protocol.makePacket(Protocol.ECHO_S, data).getBytes(), addr, port);
			this.server.send(p);
			return;
		}
		
		// See if a connection has been made
		if(header == Protocol.CON) {
			Packet p = new Packet(Protocol.makePacket(Protocol.OK, "").getBytes(), addr, port);
			this.server.send(p);
			return;
		}
		
		// Check for a given password
		if(header == Protocol.PW) {

			System.out.println(data + " : " + this.password);
			
			if(data.equals(this.password)) {
				this.server.send(new Packet(Protocol.makePacket(Protocol.PW_AUTH_OK, "").getBytes(), addr, port));
			} else {
				this.server.send(new Packet(Protocol.makePacket(Protocol.PW_AUTH_NOT_OK, "").getBytes(), addr, port));
			}
			
			return;
		}
		
		// If the user has not yet authenticated then complain
		if(!addr.equals(this.clientAddress) || port != this.clientPort) {
			String msg = "You are not yet authorised to access this server";
			this.server.send(new Packet(Protocol.makePacket(Protocol.ERR,msg).getBytes(), addr, port));
			return;
		}
	}
	
}
