import java.util.Arrays;


/**
 * This class holds static properties which represent the
 * protocol messages
 * @author Craig
 *
 */
public class Protocol {
	
	/**
	 * Basic messages
	 */
	public static final int CON				= 100,
						  	OK 				= 101,
						  	NOT_OK 			= 102,
						  	ERR 			= 103,
						  	ECHO_S 			= 104,
						  	ECHO_B 			= 105,
						  	EXIT 			= 106;
	
	/**
	 * Password Function
	 */
	public static final int PW 				= 200,
					  	    PW_AUTH_OK 		= 201,
					  	    PW_AUTH_NOT_OK 	= 202;
	
	/**
	 * File sending/receiving Function
	 */
	public static final int DIR_REQ 			= 300,
					  		DIR_LIST	 		= 301,
					  		FILE_SEND		 	= 302,
					  		FILE_REC			= 303;
	
	/**
	 * Server acces stuff
	 */
	public static final int BLOCKED 			= 401;
	
	/**
	 * Extract the protocol header from the given packet data
	 * @param packetData
	 * @return the header, ie. Protocol.PW
	 */
	public static int getPacketHeader(String packetData) {
		return Integer.parseInt(splitPacketByProtocol(packetData)[0]);
	}
	
	/**
	 * Extract the actual data from the packet
	 * @param packetData
	 * @return the data
	 */
	public static String getPacketData(String packetData) {
		String[] p = splitPacketByProtocol(packetData);
		
		if(p.length > 1) {
			return p[1];
		}
		return "";
	}
	
	/**
	 * Make a packet fit the protocol
	 * @param header
	 * @param data
	 * @return the protocol matching packet data
	 */
	public static String makePacket(int header, String data) {
		return "{" + header + ":" + data + "}";
	}
	
	/**
	 * Format the packet to support files
	 * @return the formatted packet data
	 */
	public static String formatFileData(String fileName, String fileData) {
		return "{" + fileName + ":" + fileData + "}";
	}
	
	/**
	 * Read an input of file data, extract the name and file contents
	 * @param fileData
	 * @return array of size 2 holding filename and contents
	 */
	public static String[] readFileData(String fileData) {
		if(!isValid(fileData)) {
			return null;
		}
		
		String msg = fileData.substring(1, fileData.length() - 1);
		return msg.split(":");
	}
	
	private static boolean isValid(String data) {
		if(data.length() < 1 || !data.startsWith("{") || !data.endsWith("}")) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Split the packet up into header and data
	 * @param packetData
	 * @return an array of the two parts
	 */
	private static String[] splitPacketByProtocol(String packetData) {
		if(!isValid(packetData)) {
			return null;
		}
		
		String msg = packetData.substring(1, packetData.length() - 1);
		return msg.split(":");
		
	}
	
}
