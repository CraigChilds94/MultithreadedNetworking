public interface Response {}

/**
 * Represents an OK response
 * @author craig
 */
class OK implements Response {}

/**
 * Returned when the client cannot connect to the server
 * @author craig
 */
class CannotConnect implements Response {}

/**
 * Returned when the client is denied permission to the server
 * @author craig
 */
class ClientBlocked implements Response {}

/**
 * Returned when a client is unable to authenticate a connection to the server
 * @author craig
 */
class AuthenticationFailed implements Response {}

/**
 * A request for the list of directories
 * @author craig
 */
class DirectoryListing implements Response {
    public String dir_;
    public DirectoryListing(String dir) {
    	dir_ = dir;
    }
}

/**
 * Represents a response detailing a file send was not possible
 * @author craig
 */
class CannotSendFile implements Response {}

/**
 * Represents a response detailing a file receive was not possible
 * @author craig
 */
class CannotRecieveFile implements Response {}

/**
 * A response that returns the content of a file as a string
 * @author craig
 */
class FileContent implements Response {
    public String fileData_;
    public FileContent(String fileData) { 
    	fileData_ = fileData; 
   	} 
}

/**
 * The contents of a response as a byte array
 * @author craig
 */
class ByteArrayContent implements Response {
    public byte [] ba_;
    public ByteArrayContent(byte [] ba) {
    	ba_ = ba;
    }
}

/**
 * A response that returns the content of a response as a string
 * @author craig
 */
class StringContent implements Response {
    public String s_;
    public StringContent(String s) {
    	s_ = s;
    }
}

/**
 * A response which represents a case where something went wrong
 * or something unexpected happened.
 * @author craig
 *
 */
class Problem implements Response {
    public String msg_;
    public Problem(String msg) {
    	msg_ = msg;
    } 
}