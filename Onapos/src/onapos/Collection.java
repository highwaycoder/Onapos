package onapos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Collection {
	private List<Item> items;
	private String name;
	private String type;
	
	/**
	 * Constructor for creating collections without a type
	 * I see no reason to disallow generic-type lists
	 * @param collectionName
	 */
	public Collection(String collectionName) {
		name = collectionName;
		type = "Generic";
		items = new ArrayList<Item>();
	}
	
	/**
	 * Constructor for creating collections with a type
	 * @param collectionName the name of the new collection (eg 'Favourite Movies', 'Books I own')
	 * @param collectionType the type of collection (eg 'Movies', 'Books', etc.)
	 */
	public Collection(String collectionName,String collectionType) {
		name = collectionName;
		type = collectionType;
		items = new ArrayList<Item>();
	}
	
	/**
	 * Safe (non-modifying) method for sorting collections
	 * @return a sorted list of items
	 */
	public List<Item> sortItemsSafe(String by) {
		List<Item> sorted = new ArrayList<Item>();
		for(Item item : items) {
			if(sorted.isEmpty())
				sorted.add(item);
			if(item.compareTo(sorted.get(0),by) < 0) {
				sorted.add(0, item);
			} else {
				sorted.add(sorted.size(),item);
			}
		}
		return sorted;
	}
	
	/**
	 * Gets the name of the collection (eg 'My Movie Collection')
	 * @return the name of the collection as a String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the type of the collection (eg 'Movies')
	 * @return the type of collection as a String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Adds an item to this collection
	 * @param i the item to add
	 */
	public void addItem(Item i) {
		items.add(i);
	}
	
	/**
	 * Adds a bunch of items to this collection at once
	 * @param i the List of items to add
	 */
	public void addItems(List<Item> i) {
		items.addAll(i);
	}
	
	/**
	 * Gets the complete list of items in the collection
	 * @return a List of Items
	 */
	public List<Item> getItems() {
		return items;
	}
	
	/**
	 * Gets a map of the properties that items in this collection have
	 * @return a Map of String,Property
	 */
	public Map<String,Property> getProperties() {
		if(items.isEmpty())
			return new HashMap<String,Property>();
		else
			return items.get(0).getProperties();
	}
	
	/**
	 * Gets the number of items in this collection
	 * @return the number of items in this collection
	 */
	public int numItems() {
		return items.size();
	}
	
	/**
	 * Gets the number of properties in this collection
	 * @return the number of properties in this collection
	 */
	public int numProperties() {
		return getProperties().size();
	}
}
