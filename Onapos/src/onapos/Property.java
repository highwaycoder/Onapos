package onapos;

import java.text.ParseException;
import java.util.Date;

// FIXME: this class currently abuses the PropertyException class.

public class Property implements Comparable<Property> {
	// in future, this whole class should be re-implemented as one that's a lot
	// less finicky, like so:
	private Object value;
	private PropertyType type;
	private int priority;
	
	/**
	 * Preferred constructor, creates a property of the given type with the given value
	 * @param type the type of the property
	 * @param value the value of the property
	 */
	public Property(PropertyType type,Object v) {
		this.type = type;
		switch(type) {
		case BOOLEAN:
			if(v instanceof Boolean) value = (Boolean) v;
			else if(v instanceof String) value = Boolean.parseBoolean((String) v);
			break;
		case DATE:
			if(v instanceof Date) value = (Date) v;
			else if(v instanceof String)
				try {
					value = Onapos.SDF.parse((String) v);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case DOUBLE:
			if(v instanceof Double) value = (Double) v;
			else if (v instanceof String) value = Double.parseDouble((String) v);
			break;
		case INTEGER:
			if(v instanceof Integer) value = (Integer) v;
			else if (value instanceof String) value = Integer.parseInt((String) v);
			break;
		case STRING:
			if(v instanceof String) value = (String) v;
			break;
		default:
			break;
		}
	}
	
	/**
	 * Creates a new property of type STRING
	 * @param v the value of the new property
	 * @deprecated use Property(PropertyType,Object) instead
	 */
	public Property(String v) {
		type = PropertyType.STRING;
		value = v;
	}
	
	/**
	 * Creates a new property of type INTEGER
	 * @param v the value of the new property
	 * @deprecated use Property(PropertyType,Object) instead
	 */
	public Property(int v) {
		type = PropertyType.INTEGER;
		value = v;
	}
	
	/**
	 * Creates a new property of type DOUBLE
	 * @param v the value of the new property
	 * @deprecated use Property(PropertyType,Object) instead
	 */
	public Property(double v) {
		type = PropertyType.DOUBLE;
		value = v;
	}
	
	/**
	 * Creates a new property of type DATE
	 * @param v the value of the new property
	 * @deprecated use Property(PropertyType,Object) instead
	 */
	public Property(Date v) {
		type = PropertyType.DATE;
		value = v;
	}
	
	/**
	 * Creates a new property of type BOOLEAN
	 * @param value the value of the new property
	 * @deprecated use Property(PropertyType,Object) instead
	 */
	public Property(boolean v) {
		type = PropertyType.BOOLEAN;
		value = v;
	}
	
	/**
	 * @deprecated should never have existed (don't know why I made it)
	 */
	@Deprecated
	public Property() {
		// That's a BAD programmer!  BAD!
		System.err.println("Error: naked property created (this would cause massive problems later)");
		System.exit(0);
	}
	
	/**
	 * Compares this property to another property
	 * TODO: allow comparison between different types of property
	 * @param arg0 the property to compare this one to
	 */
	@Override
	public int compareTo(Property arg0) {
		switch(type) {
		case STRING:
			try {
				return arg0.getString().compareTo((String)value);
			} catch(PropertyException e) {
				System.err.println("WARNING: not a string (comparison failed)");
			}
		case INTEGER:
			try {
				return (Integer) value - arg0.getInt(); // returns + if this int is higher, - otherwise (0 if equal)
			} catch(PropertyException e) {
				System.err.println("WARNING: not an int (comparison failed)");
			}
		case DOUBLE:
			try {
				return (int)Math.floor((Double) value - arg0.getDouble());
			} catch(PropertyException e) {
				System.err.println("WARNING: not a double (comparison failed)");
			}
		case DATE:
			try {
				return ((Date)value).compareTo(arg0.getDate());
			} catch(PropertyException e) {
				System.err.println("WARNING: not a date (comparison failed)");
			}
		case BOOLEAN:
			try {
				return ((Boolean)value).compareTo(arg0.getBoolean());
			} catch(PropertyException e) {
				System.err.println("WARNING: not a boolean (comparison failed)");
			}
		default:
			System.err.println("WARNING: compared a broken Property");
			return 0;
		}
	}
	
	/**
	 * Gets the property value as a boolean
	 * @return the value as a boolean
	 * @throws PropertyException if this property isn't a boolean
	 */
	public boolean getBoolean() throws PropertyException {
		if(type != PropertyType.BOOLEAN) throw new PropertyException();
		else return (Boolean)value;
	}

	/**
	 * Gets the property value as a string
	 * @return the value as a string
	 * @throws PropertyException if this property isn't a string
	 */
	public String getString() throws PropertyException {
		if(type != PropertyType.STRING) throw new PropertyException();
		else return (String)value;
	}
	
	/**
	 * Gets the property value as an integer
	 * @return the value as an integer
	 * @throws PropertyException if this property isn't an integer
	 */
	public int getInt() throws PropertyException {
		if(type != PropertyType.INTEGER) throw new PropertyException();
		else return (Integer)value;
	}
	
	/**
	 * Gets the property value as a double
	 * @return the value as a double
	 * @throws PropertyException if this property isn't a double
	 */
	public double getDouble() throws PropertyException {
		if(type != PropertyType.DOUBLE) throw new PropertyException();
		else return (Double)value;
	}
	
	/**
	 * Gets the property as a date
	 * @return the value as a date
	 * @throws PropertyException if this property isn't a date
	 */
	public Date getDate() throws PropertyException {
		if(type != PropertyType.DATE) throw new PropertyException();
		else return (Date)value;
	}
	
	/**
	 * Gets the type of property this is (as a PropertyType)
	 * @return the PropertyType of this property
	 */
	public PropertyType getType() {
		return type;
	}
	
	/**
	 * Gets the type of property this is (as a String)
	 * @return the String version of the PropertyType
	 */
	public String getTypeAsString() {
		switch(type) {
		case STRING:
			return "string";
		case INTEGER:
			return "integer";
		case DOUBLE:
			return "double";
		case DATE:
			return "date";
		case BOOLEAN:
			return "boolean";
		default:
			return null;
		}
	}
	
	/**
	 * Overridden toString() method allows any Property to be represented as a String
	 * @return this Property represented as a String
	 */
	@Override
	public String toString() {
		// this should work for pretty much any Object
		return value.toString();
	}
	
	/**
	 * Returns the type of this property as a PropertyType, based on the string passed in
	 * @param name the String representation of the PropertyType
	 * @return the PropertyType represented by 'name'
	 */
	public static PropertyType getTypeByName(String name) {
		if(name.equalsIgnoreCase("string")) return PropertyType.STRING;
		if(name.equalsIgnoreCase("integer") || name.equalsIgnoreCase("int")) return PropertyType.INTEGER;
		if(name.equalsIgnoreCase("double") || name.equalsIgnoreCase("float")) return PropertyType.DOUBLE;
		if(name.equalsIgnoreCase("date")) return PropertyType.DATE;
		if(name.equalsIgnoreCase("boolean")) return PropertyType.BOOLEAN;
		// default to string
		return PropertyType.STRING;
	}
	
	
	public boolean equals(String compareTo) {
		switch(type) {
		case STRING:
			return compareTo.equalsIgnoreCase((String)value);
		case INTEGER:
			return Integer.parseInt(compareTo) == (Integer)value;
		case DOUBLE:
			return Double.parseDouble(compareTo) == (Double)value;
		case DATE:
			try {
				return Onapos.SDF.parse(compareTo).equals((Date)value);
			} catch (ParseException e) {
				// default to comparing as if they're both strings
				return compareTo.equalsIgnoreCase(compareTo);
			}
		case BOOLEAN:
			if((Boolean)value == true) {
				if(compareTo.equalsIgnoreCase("yes") ||
						Boolean.parseBoolean(compareTo)) return true;
			} else {
				if(compareTo.equalsIgnoreCase("no") ||
						!Boolean.parseBoolean(compareTo)) return true;
			} return false;
		default:
			return false; // this property is broken
		}
	}

	/**
	 * How far left does this property go?
	 * @return an integer, the lower the integer, the further left it goes
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets how far left this property should go
	 * @param priority the lower this number is, the further left it will be
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Creates a template property of the given type and returns it
	 * @param type
	 * @return a template property
	 */
	public static Property getTemplateOfType(PropertyType type) {
		// FIXME: this might present problems when the code is more mature
		return new Property(type,null);
	}
}
