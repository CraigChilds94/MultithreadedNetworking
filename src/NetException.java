/**
 * A network exception
 * @author craig
 *
 */
public class NetException extends Exception {
    public String msg;
    public NetException (String _msg) {
    	msg = _msg; 
    }
}