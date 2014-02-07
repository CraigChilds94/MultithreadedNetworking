package data;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Handles generation of unique id's to identify each client
 * @author craig
 * @version 0.1
 */
public class UID {
	
	public static int RANGE = 10000;									/* How many ids to generate */
	public static ArrayList<Integer> ids = new ArrayList<Integer>();	/* The list of ids */
	public static int index = 0;										/* The current index in the list */
	
	/**
	 * Fill the array with id's
	 */
	static {
		for(int i = 0; i < RANGE; i++) {
			ids.add(i);
		}
		
		// Shuffle the list
		Collections.shuffle(ids);
	}
	
	/**
	 * Get the next identifier in the list
	 * @return the id
	 */
	public static int getIdentifier() {
		if(index > ids.size() - 1) index = 0;
		return ids.get(index++);
	}
}
