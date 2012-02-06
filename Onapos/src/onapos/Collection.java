package onapos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Collection {
	private List<Item> items;
	private String name;
	private String type;
	
	// I see no reason to disallow generic-type lists
	public Collection(String collectionName) {
		name = collectionName;
		type = "Generic";
		items = new ArrayList<Item>();
	}
	
	public Collection(String collectionName,String collectionType) {
		name = collectionName;
		type = collectionType;
		items = new ArrayList<Item>();
	}
	
	/**
	 * Safe (non-modifying) method for sorting collections
	 * @return a sorted list
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
	
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void addItem(Item i) {
		items.add(i);
	}
	
	public void addItems(List<Item> i) {
		items.addAll(i);
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public HashMap<String,Property> getProperties() {
		if(items.isEmpty())
			return new HashMap<String,Property>();
		else
			return items.get(0).getProperties();
	}
	
	public int numItems() {
		return items.size();
	}
	
	public int numProperties() {
		return getProperties().size();
	}
}
