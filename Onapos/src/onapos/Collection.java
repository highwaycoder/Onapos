package onapos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
	
	/**
	 * Searches for an item in the collection
	 * @param searchField the property to search in
	 * @param searchValue the value to search for
	 * @return the first Item that matches the description
	 */
	public Item findItem(String sf,Property searchValue) {
		String searchField = sf.toLowerCase(); // make sure we're comparing lower-case versions
		if(searchValue == null) {
			System.err.println("Warning: null property in findItem()");
			return null;
		}
		for(Item i : items) {
			if(i.getProperties().containsKey(searchField)) {
				if(i.getProperties().get(searchField).equals(searchValue)) return i;
			}
		}
		System.err.println("Warning: item not found");
		return null;
	}
	
	/**
	 * Removes a specific Item from this Collection
	 * Note: if two or more items have the same UID, this function will delete 
	 * all of them 
	 * @param uid the unique identifier of the item we want to delete
	 */
	public void delItem(int uid) {
		for(int i=0;i<items.size();i++) {
			if(items.get(i).getUID() == uid) items.remove(i);
		}
	}
	
	/**
	 * Generates a Unique ID for an Item (using a Random)
	 * @return an integer unique to a single Item in this Collection
	 */
	public int generateUID() {
		int uid = 0;
		for(Item i : items) {
			Random r = new Random();
			if(i.getUID()==uid) uid = r.nextInt();
		}
		return uid;
	}
	
	/**
	 * Sets the collection's name to the parameter specified
	 * @param to the name to set
	 */
	public void setName(String to) {
		name = to;
	}
	
	/**
	 * Sets the collection's type to the parameter specified
	 * @param to the type to set
	 */
	public void setType(String to) {
		type = to;
	}
}
