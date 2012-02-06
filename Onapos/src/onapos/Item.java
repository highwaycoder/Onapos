package onapos;

import java.util.HashMap;


public class Item implements Comparable<Item> {
	private HashMap<String,Property> properties;
	
	public Item() {
		properties = new HashMap<String,Property>();
	}
	
	@Override
	public int compareTo(Item arg0) {
		// TODO: some default way of sorting, perhaps?
		System.err.println("WARNING: comparing without choosing what to compare to is BROKEN");
		return 0;
	}
	
	public int compareTo(Item arg0,String as) {
		return properties.get(as).compareTo(arg0.getProperty(as));
	}
	
	public Property getProperty(String propertyName) {
		return properties.get(propertyName);
	}
	
	public void addProperty(String propertyName,Property propertyValue) {
		System.out.println(propertyName + ":" + propertyValue);
		properties.put(propertyName, propertyValue);
	}
	
	public void delProperty(String propertyName) {
		properties.remove(propertyName);
	}
	
	public HashMap<String,Property> getProperties() {
		return properties;
	}
	
	public void printAll() {
		for(Property p : properties.values()) {
			System.out.println(p.toString());
		}
	}
}
