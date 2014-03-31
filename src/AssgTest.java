

import static org.junit.Assert.*;

import org.junit.Test;

public class AssgTest {

	@Test
	public void test() {
		Server s = Task2.makeServer();
		Client c = Task2.makeClient();
		
		try {
			s.start(1337, "hello", null);
			c.start("localhost", 1337);
			
			Response r = c.connect();
			System.out.println(r);
			
		} catch (NetException e) {
			System.out.println(e.msg);
		}
	}

}
