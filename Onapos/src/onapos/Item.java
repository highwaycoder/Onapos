package onapos;

import java.util.HashMap;
import java.util.Map;


public class Item implements Comparable<Item> {
	private HashMap<String,Property> properties;
	int uid;
	
	/**
	 * Constructor, creates a blank Item
	 * @param uid a unique identifier for this item (used to delete items)
	 */
	public Item(int uid) {
		properties = new HashMap<String,Property>();
	}
	
	/**
	 * Gets the Unique IDentifier for this Item
	 * @return the UID int
	 */
	public int getUID() {
		return uid;
	}
	
	/**
	 * Compare to some other item by some default method
	 * @deprecated because there's no sensible way to choose what to sort by 'by default'
	 */
	@Deprecated
	@Override
	public int compareTo(Item arg0) {
		// TODO: some default way of sorting, perhaps?
		System.err.println("Warning (deprecation): compareTo(Item arg0) is deprecated");
		return 0;
	}
	
	/**
	 * Comparison between this item and an appropriate 'other' item
	 * @param arg0 the item to compare to
	 * @param as the property with which to make the comparison
	 * @return the 'difference' between this Item and arg0 (see Property.compareTo())
	 */
	public int compareTo(Item arg0,String as) {
		return properties.get(as).compareTo(arg0.getProperty(as));
	}
	
	/**
	 * Grab a property of this item
	 * @param propertyName the property to grab
	 * @return the Property we grabbed
	 */
	public Property getProperty(String propertyName) {
		return properties.get(propertyName);
	}
	
	/**
	 * Adds a new property to this item
	 * Danger, will robinson!  Using this willy-nilly may introduce bugs
	 * @param propertyName the name of the new property
	 * @param propertyValue the value of the new property
	 */
	public void addProperty(String propertyName,Property propertyValue) {
		System.out.println(propertyName + ":" + propertyValue);
		properties.put(propertyName, propertyValue);
	}
	
	/**
	 * Deletes a property from this item
	 * Danger, will robinson!  Using this willy-nilly <i>will</i> introduce bugs
	 * @param propertyName the name of the property to remove
	 */
	public void delProperty(String propertyName) {
		properties.remove(propertyName);
	}
	
	/**
	 * Gets all the properties at once in a handy Map of String,Property
	 * @return a Map of String,Property
	 */
	public Map<String,Property> getProperties() {
		return properties;
	}
	
	public void printAll() {
		for(Property p : properties.values()) {
			System.out.println(p.toString());
		}
	}
}
