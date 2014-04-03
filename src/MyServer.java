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
		byte[] data  = packet.getData();
		InetAddress addr = packet.getAddr();
		int port = packet.getPort();
		
		System.out.println(new String(data + " from " + addr.getHostAddress() + ":" + port));
		
		String d = packet.getDataAsString(true);
		String sAddr = addr.getHostAddress();
		
		// Checked to see if that client is allowed
		if(forbidden.contains(sAddr)) {
			this.server.send(new Packet("BLOCKED".getBytes(), addr, port));
			return;
		}
		
		// Echo the string back
		if(d.startsWith("ECHO_S:")) {
			this.server.send(new Packet(("ECHO_S:" + d.split(":")[1]).getBytes(), addr, port));
			return;
		}
		
		// See if a connection has been made
		if(d.equals("CON")) {
			this.server.send(new Packet("OK".getBytes(), addr, port));
			return;
		}
		
		// Check for a given password
		if(d.startsWith("PW:")) {
			String pass = d.split(":")[1];
			
			System.out.println(pass + " : " + this.password);
			
			if(pass.equals(this.password)) {
				this.server.send(new Packet("PW_AUTH_OK".getBytes(), addr, port));
			} else {
				this.server.send(new Packet("PW_AUTH_NOT_OK".getBytes(), addr, port));
			}
			
			return;
		}
		
		// If the user has not yet authenticated then complain
		if(!addr.equals(this.clientAddress) || port != this.clientPort) {
			this.server.send(new Packet("ERROR: You are not yet authorised to access this server".getBytes(), addr, port));
			return;
		}
	}
	
}
