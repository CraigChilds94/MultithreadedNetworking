import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	
	public boolean CONN = false, AUTH = false;

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
	private String password, clientPassword = "";
	
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
		String sAddr = addr.getCanonicalHostName();
		String packetData = packet.getDataAsString(true);
		int header = Protocol.getPacketHeader(packetData);
		String data = Protocol.getPacketData(packetData);
		
		// Checked to see if that client is allowed
		if(forbidden.contains(sAddr)) {
			handleBlocked(data, addr, port);
			return;
		}
		
		if(header == Protocol.CON) {
			handleConnect(data, addr, port); 
			return;
		}
		
		if(main.CONN) {
			
			
			if(header == Protocol.PW) {
				handleAuth(data, addr, port);
				return;
			}
			
			// If the user has not yet authenticated then complain
			if(!password.equals(clientPassword)) {
				handleNonAuth(data, addr, port);
				return;
			} else {
			
				// Deal with each request appropriately
				switch(header) {
					case Protocol.ECHO_S 		: handleEchoS(data, addr, port); return;
					case Protocol.ECHO_B 		: handleEchoB(data, addr, port); return;
					case Protocol.DIR_REQ		: handleDirList(data, addr, port); return;
					case Protocol.FILE_REC		: handleFileSend(data, addr, port); return;
					case Protocol.FILE_SEND		: handleFileRec(data, addr, port); return;
					case Protocol.EXIT_C		: handleClientExit(data, addr, port); return;
					case Protocol.EXIT_S		: handleServerExit(data, addr, port); return;
					default						: handleError(data, addr, port); return;
				}
			}
		}
		
		// There's no connection tell them
		handleNoConn(data, addr, port);
	}
	
	/**
	 * Handle the case where the client forces the server to close
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleServerExit(String data, InetAddress addr, int port) {
		this.server.send(new Packet(Protocol.makePacket(Protocol.OK, "").getBytes(), addr, port));
		//this.server.close();
	}

	/**
	 * Handle a case where the client exits
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleClientExit(String data, InetAddress addr, int port) {
		this.server.send(new Packet(Protocol.makePacket(Protocol.OK, "").getBytes(), addr, port));
		closeConnection();
	}
	
	/**
	 * Handle a case where the client hasn't connected
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleNoConn(String data, InetAddress addr, int port) {
		this.server.send(new Packet(Protocol.makePacket(Protocol.NOT_OK, "You have not yet connected to the server!").getBytes(), addr, port));
	}
	
	/**
	 * Handle the case where an invalid message header is sent
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleError(String data, InetAddress addr, int port) {
		this.server.send(new Packet(Protocol.makePacket(Protocol.ERR, "You have sent an invalid message type.").getBytes(), addr, port));
	}
	
	/**
	 * Handle client authentication
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleAuth(String data, InetAddress addr, int port) {
		if(data.equals(this.password)) {
			this.clientPassword = this.password;
			this.server.send(new Packet(Protocol.makePacket(Protocol.PW_AUTH_OK, this.clientPassword + ":" + data).getBytes(), addr, port));
		} else {
			this.server.send(new Packet(Protocol.makePacket(Protocol.PW_AUTH_NOT_OK, "").getBytes(), addr, port));
		}
		
	}
	
	/**
	 * Handle blocked clients
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleBlocked(String data, InetAddress addr, int port) {
		this.server.send(new Packet(Protocol.makePacket(Protocol.BLOCKED, "You are not allowed access to this server").getBytes(), addr, port));
	}
	
	/**
	 * Handle a clients connection
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleConnect(String data, InetAddress addr, int port) {
		Packet p = new Packet(Protocol.makePacket(Protocol.OK, "").getBytes(), addr, port);
		this.server.send(p);
		main.CONN = true;
	}
	
	/**
	 * Handle non authorised and non-blocked users
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleNonAuth(String data, InetAddress addr, int port) {
		String msg = "You are not yet authorised to access this server";
		this.server.send(new Packet(Protocol.makePacket(Protocol.PW_AUTH_NOT_OK,msg).getBytes(), addr, port));
	}
	
	/**
	 * Handle the echo string
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleEchoS(String data, InetAddress addr, int port) {
		Packet p = new Packet(Protocol.makePacket(Protocol.ECHO_S, data).getBytes(), addr, port);
		this.server.send(p);
	}
	
	/**
	 * Handle the echo byte array
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleEchoB(String data, InetAddress addr, int port) {
		Packet p = new Packet(Protocol.makePacket(Protocol.ECHO_B, data).getBytes(), addr, port);
		this.server.send(p);
	}
	
	/**
	 * Handle a file receive
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleFileRec(String data, InetAddress addr, int port) {
		String[] parts = Protocol.readFileData(data);
		
		String filename = parts[0];
		
		String content = parts[1];
		String path = getCurrentDir() + "\\" + filename;
		
		File file = new File(path);
		FileWriter fw;
		try {
			if (file.exists()) {
				this.server.send(new Packet(Protocol.makePacket(Protocol.ERR, path).getBytes(), addr, port));
				return;
			} else {
				file.createNewFile();
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
				this.server.send(new Packet(Protocol.makePacket(Protocol.OK, path).getBytes(), addr, port));
			}
		} catch (IOException e) {
			this.server.send(new Packet(Protocol.makePacket(Protocol.ERR, e.getMessage()).getBytes(), addr, port));
		}
	}
	
	/**
	 * Handle a file send
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleFileSend(String data, InetAddress addr, int port) {
		String path = getCurrentDir() + "\\" + data;
		File f = new File(path);
		
		if(!f.getAbsoluteFile().exists()) {
			this.server.send(new Packet(Protocol.makePacket(Protocol.ERR, "File does not exist, " + path).getBytes(), addr, port));
			return;
		}
		
		if(!f.canRead()) {
			this.server.send(new Packet(Protocol.makePacket(Protocol.ERR, "File is not readable").getBytes(), addr, port));
			return;
		}
		
		if(!f.isFile()) {
			this.server.send(new Packet(Protocol.makePacket(Protocol.ERR, data + " is not a valid file").getBytes(), addr, port));
			return;
		}
		
		try {
			f = f.getAbsoluteFile();
			FileReader reader = new FileReader(f);
			char[] characters = new char[(int)f.length()];
   			reader.read(characters);
   			String content = new String(characters);
   			reader.close();
   			this.server.send(new Packet(Protocol.makePacket(Protocol.FILE_SEND, content).getBytes(), addr, port));
		} catch(IOException e) {
			this.server.send(new Packet(Protocol.makePacket(Protocol.ERR, "Unable to read file due to error: " + e.getMessage()).getBytes(), addr, port));
		}
	}
	
	/**
	 * Handle a dir listing
	 * @param data
	 * @param addr
	 * @param port
	 */
	private void handleDirList(String data, InetAddress addr, int port) {
		File folder = new File(getCurrentDir());

		if(!folder.isDirectory() || !folder.canRead() || !folder.canWrite()) {
			this.server.send(new Packet(Protocol.makePacket(Protocol.ERR, "The folder the server was started in is not valid, " + getCurrentDir()).getBytes(), addr, port));
			return;
		}
		
		File[] dirlist = folder.listFiles();
		String packetData = Protocol.makePacket(Protocol.DIR_LIST, dirlist.toString());
		this.server.send(new Packet(packetData.getBytes(), addr, port));
	}
	
	/**
	 * Close the client connection
	 */
	private void closeConnection() {
		clientPassword = "";
		main.CONN = false;
		main.AUTH = false;
	}
	
	/**
	 * Get the dir the server was started in
	 * @return
	 */
	private String getCurrentDir() {
		Path p = Paths.get("");
		String s = p.toAbsolutePath().toString();
		return s;
	}
}
