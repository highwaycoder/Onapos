package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddItemListener implements ActionListener {

	private OnaposUI context;
	private Item item;
	private Map<JLabel,JTextField> properties;
	
	/**
	 * Constructor for the listener
	 * TODO: rearrange the code so this takes in a Map<String,Property>
	 * @param c the OnaposUI that created this listener ('context')
	 * @param ps the properties of the new item (JLabel,JTextField)
	 */
	public AddItemListener(OnaposUI c,Map<JLabel,JTextField> ps) {
		context = c;
		item = new Item();
		properties = ps;
	}
	
	/**
	 * Adds the item to the currently selected collection
	 * @param e unused
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Collection col = context.getSelectedCollection();
		for(Entry<JLabel,JTextField> entry : properties.entrySet()) {
			String propertyName = entry.getKey().getText();
			if(col.getProperties().containsKey(propertyName)) {
				PropertyType t = col.getProperties().get(propertyName).getType();
				Property p = craftProperty(t,entry.getValue().getText());
				item.addProperty(propertyName, p);
			}
		}
		col.addItem(item);
		context.populateTable(col);
	}
	
	/**
	 * Helper function to create a Property from the PropertyType and String
	 * @param t the type of property to create
	 * @param v the String representation of the value of the new Property
	 * @return the newly-created Property
	 */
	private Property craftProperty(PropertyType t,String v) {
		switch(t) {
		case STRING:
			return new Property(v);
		case INTEGER:
			return new Property(Integer.parseInt(v));
		case DOUBLE:
			return new Property(Double.parseDouble(v));
		case DATE:
			// allows us to keep a uniform date format across everywhere
			try {
				return new Property(CollectionFile.SDF.parse(v));
			} catch (ParseException e) {
				// continue as before, parsing as string (issue warning too)
				System.err.println("WARNING: date object unrecognised, parsing as string");
				return new Property(v);
			}
		case BOOLEAN:
			if(v.toLowerCase().equals("yes") || v.toLowerCase().equals("true")) return new Property(true);
			return new Property(false);
		default:
			// parse it as string if all else fails
			return new Property(v);
		}
	}

}
