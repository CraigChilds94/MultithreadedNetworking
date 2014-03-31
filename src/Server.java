import java.util.List;

/**
 * Interface for the server of the application
 * @author craig
 *
 */
public interface Server {
	
	/**
	 * Start the server with the provided details
	 * @param port - The port the server will run on
	 * @param password - The required password to connect to the server
	 * @param forbidden - A list of clients that are forbidden from connecting to this server
	 * @throws NetException - The exception that represents a network issue
	 */
    public void start(int port, String password, List<String> forbidden) throws NetException; 
}


	/** The "port" variable holds the port number at which the
	   server will run. The variable "forbidden" implements basic
	   client-address based access control (firewall). It contains
	   a list (possibly empty) of IP addresses and/or
	   FQDNs. Servers connecting from an IP address that is in
	   this list, or if there is a FQDN in "forbidden" that resolves
	   to that IP address, have their connection request refused
	   and an appropriate message is returned to the client (see
	   below). If the list contains strings that are neither valid
	   IP addresses or valid FQDNs, these are simply ignored.
	
	   Once the client has connected, the client must supply a
	   password (so connecting is different from the password
	   exchange).  If the client successfully returns the correct
	   password, it gains full access to all the servers
	   functionality. Otherwise the server sends a suitable error
	   message back to the client. If the client requests server
	   functionality without being successfully authenticated, the
	   server returns suitable error messages (see below). **/