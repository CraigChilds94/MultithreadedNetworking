

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AssgTest {

	@Test
	public void test() {
		Server s = Task2.makeServer();
		Client c = Task2.makeClient();
		
		try {
			ArrayList<String> f = new ArrayList<String>();
			f.add("127.0.0.1");
			
			s.start(1337, "hello", f);
			c.start("localhost", 1337);
			
			Response r = c.connect();
			System.out.println(r);
			
			r = c.sendPassword("hello");
			System.out.println(r);
			
			r = c.echoString("testing echo");
			System.out.println(r);
			
		} catch (NetException e) {
			System.out.println(e.msg);
		}
	}

}
