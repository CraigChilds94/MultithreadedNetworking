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
		this.client.send("CON".getBytes());
		this.client.receive(new PacketHandler() {

			@Override
			public void process(Packet packet) {
				String data = new String(packet.getData()).trim();
				//System.out.println(data);
				if(data.equals("OK")) {
					MyClient.lastResponse = new OK();
				} else {
					MyClient.lastResponse = new CannotConnect();
				}
			}
			
		});
		
		//System.out.println(lastResponse);
		
		return MyClient.lastResponse;
	}

	@Override
	public Response sendPassword(String pw) throws NetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clientExit() throws NetException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Response serverExit() throws NetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response listDirectory() throws NetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response sendFile(String fileName, String fileContent)
			throws NetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response receiveFile(String fileName) throws NetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response echoByteArray(byte[] ba) throws NetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response echoString(String s) throws NetException {
		// TODO Auto-generated method stub
		return null;
	}

}
