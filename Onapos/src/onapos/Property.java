package onapos;

import java.util.Date;

// FIXME: this class currently abuses the PropertyException class.

public class Property implements Comparable<Property> {
	private String asString;
	private int asInt;
	private double asDouble;
	private Date asDate;
	private PropertyType type;
	private boolean asBoolean;
	
	/**
	 * Creates a new property of type STRING
	 * @param value the value of the new property
	 */
	public Property(String value) {
		type = PropertyType.STRING;
		asString = value;
	}
	
	/**
	 * Creates a new property of type INTEGER
	 * @param value the value of the new property
	 */
	public Property(int value) {
		type = PropertyType.INTEGER;
		asInt = value;
	}
	
	/**
	 * Creates a new property of type DOUBLE
	 * @param value the value of the new property
	 */
	public Property(double value) {
		type = PropertyType.DOUBLE;
		asDouble = value;
	}
	
	/**
	 * Creates a new property of type DATE
	 * @param value the value of the new property
	 */
	public Property(Date value) {
		type = PropertyType.DATE;
		asDate = value;
	}
	
	/**
	 * Creates a new property of type BOOLEAN
	 * @param value the value of the new property
	 */
	public Property(boolean value) {
		type = PropertyType.BOOLEAN;
		asBoolean = value;
	}
	
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
				return arg0.getString().compareTo(asString);
			} catch(PropertyException e) {
				System.err.println("WARNING: not a string (comparison failed)");
			}
		case INTEGER:
			try {
				return asInt - arg0.getInt(); // returns + if this int is higher, - otherwise (0 if equal)
			} catch(PropertyException e) {
				System.err.println("WARNING: not an int (comparison failed)");
			}
		case DOUBLE:
			try {
				return (int)Math.floor(asDouble - arg0.getDouble());
			} catch(PropertyException e) {
				System.err.println("WARNING: not a double (comparison failed)");
			}
		case DATE:
			try {
				return asDate.compareTo(arg0.getDate());
			} catch(PropertyException e) {
				System.err.println("WARNING: not a date (comparison failed)");
			}
		case BOOLEAN:
			try {
				return new Boolean(asBoolean).compareTo(arg0.getBoolean());
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
		else return asBoolean;
	}

	/**
	 * Gets the property value as a string
	 * @return the value as a string
	 * @throws PropertyException if this property isn't a string
	 */
	public String getString() throws PropertyException {
		if(type != PropertyType.STRING) throw new PropertyException();
		else return asString;
	}
	
	/**
	 * Gets the property value as an integer
	 * @return the value as an integer
	 * @throws PropertyException if this property isn't an integer
	 */
	public int getInt() throws PropertyException {
		if(type != PropertyType.INTEGER) throw new PropertyException();
		else return asInt;
	}
	
	/**
	 * Gets the property value as a double
	 * @return the value as a double
	 * @throws PropertyException if this property isn't a double
	 */
	public double getDouble() throws PropertyException {
		if(type != PropertyType.DOUBLE) throw new PropertyException();
		else return asDouble;
	}
	
	/**
	 * Gets the property as a date
	 * @return the value as a date
	 * @throws PropertyException if this property isn't a date
	 */
	public Date getDate() throws PropertyException {
		if(type != PropertyType.DATE) throw new PropertyException();
		else return asDate;
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
		switch(type) {
		case STRING:
			return asString;
		case INTEGER:
			return new Integer(asInt).toString();
		case DOUBLE:
			return new Double(asDouble).toString();
		case DATE:
			// allows us to keep a uniform date format across everywhere
			return CollectionFile.SDF.format(asDate);
		case BOOLEAN:
			if(asBoolean) return "yes";
			else return "no";
		default:
			return "corrupted";
		}
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
}
