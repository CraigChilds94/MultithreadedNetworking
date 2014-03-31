
/**
 * My task 2
 * @author Craig
 *
 */
public class Task2 {

	/**
	 * Return a new instance of my server
	 * @return my server
	 */
	public static Server makeServer() {
		return new MyServer();
	}
	
	/**
	 * Return a new instance of my client
	 * @return my client
	 */
	public static Client makeClient() {
		return new MyClient();
	}
}
