/**
 * Represents a client in this application
 * @author craig
 *
 */
interface Client {

    public void start ( String serverAddress, int serverPort ) throws NetException;
    // Creates a connection with the given server. If you are
    // using TCP as underlying transport prototol (you don't have
    // to, it's up to you), then this would mean starting a client
    // socket.

    public Response connect () throws NetException;
    // Creates a connection to the server (but does not yet send a
	// password). The method returns OK if connection succeeds. If
	// the client is from an IP address that is blocked by the
	// server, ClientBlocked is returned. If the connection
	// attempt is refused for other reasons, CannotConnect is
	// returned. If connect is called before start, then a
	// suitable exception should be thrown.

    public Response sendPassword ( String pw ) throws NetException;
	// Returns OK if password passing is successful, else
	// AuthenticationFailed is returned.  There is no need to do
	// anything sophisticated like refusing further connection
	// attempts or sent passwords for 10 seconds attempts after
	// three failures. If sendPassword is called before connect,
	// then a suitable exception should be thrown.

    public void clientExit () throws NetException;
	// Sends a message to the server indicating the wish to
	// terminate the connection, and exits the client
	// unconditionally (i.e. works even if the client has not
	// successfully been authenticated by the server). The server
	// acknowledges the disconnection message by sending OK to the
	// client, and then the server closes the socket. When the
	// client receives the OK, it terminates. The server itself
	// does NOT terminate.  If clientExit is called before
	// connect, then a suitable exception should be thrown.

    // All methods below require that the client has been password
    // authenticated before. They will always fail with
    // AuthenticationFailed if they had not been already password
    // authenticated. We will not mention the requirement for
    // authentication below, but it is in place.

    public Response serverExit () throws NetException;
	// Sends a message to the server indicating the wish to
	// terminate the connection and also the server.  The server
	// acknowledges the termination request using OK, then closes
	// the connection with the client, and finally terminates
	// itself (as mentioned above, the request will fail if the
	// client has not previously been authenticated).  When the
	// client receives the acknowledgment, it will also terminate.

    public Response listDirectory () throws NetException;
	// Returns an instance of DirectoryListing with the local
	// directory stored in dir_.

    public Response sendFile ( String fileName, String fileContent ) throws NetException;
	// Takes a file, here represented by fileContent, sends it to
	// the server, which tries to store it in it's local directory
	// under the name fileName. If this is not possible,
	// e.g. because a file with that name already exists, it
	// returns CannotSendFile to the client, otherwise it returns
	// OK.

    public Response receiveFile ( String fileName ) throws NetException;
	// If the server has a file with the name given in fileName in
	// its local directory, it returns the file content using the
	// FileContent class, with the data being stored in public
	// member fileData_. Otherwise it returns a suitable error
	// message using the CannotRecieveFile class.

    public Response echoByteArray ( byte [] ba ) throws NetException;
	// The server sends simply sends back to the client the
	// byte array ba using the ByteArrayContent class.

    public Response echoString ( String s ) throws NetException;
	// The server sends simply sends back to the client the
	// string ba using the StringContent class.

}
