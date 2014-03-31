import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

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
	private ArrayList<String> forbidden;
	private String password;

	@Override
	public void start(int port, String password, List<String> forbidden) throws NetException {
		this.forbidden = (ArrayList<String>) forbidden;
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
	private ArrayList<String> forbidden;
	private String password;
	
	/**
	 * Construct the handler with knowledge about our server implementation
	 * @param server
	 */
	public ServerReceiver(MyServer server) {
		this.main = server;
		this.server = this.main.getServer();
		this.forbidden = (ArrayList<String>) this.main.getForbidden();
		this.password = this.main.getPassword();
	}
	
	@Override
	public void process(Packet packet) {
		
		// Get some information about this packet
		byte[] data  = packet.getData();
		InetAddress addr = packet.getAddr();
		int port = packet.getPort();
		
		System.out.println(new String(data + " from " + addr.getHostAddress() + ":" + port));
		
		if(new String(data).trim().equals("CON")) {
			this.server.send(new Packet("OK".getBytes(), addr, port));
		}
	}
	
}
