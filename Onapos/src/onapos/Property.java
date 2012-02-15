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
	
	public Property(String value) {
		type = PropertyType.STRING;
		asString = value;
	}
	
	public Property(int value) {
		type = PropertyType.INTEGER;
		asInt = value;
	}
	
	public Property(double value) {
		type = PropertyType.DOUBLE;
		asDouble = value;
	}
	
	public Property(Date value) {
		type = PropertyType.DATE;
		asDate = value;
	}
	
	public Property(boolean value) {
		type = PropertyType.BOOLEAN;
		asBoolean = value;
	}
	
	// TODO: is there a less abusive way of doing this?
	public Property() throws Exception {
		throw new Exception("ERROR: invoked a naked property");
	}
	
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
	
	public boolean getBoolean() throws PropertyException {
		if(type != PropertyType.BOOLEAN) throw new PropertyException();
		else return asBoolean;
	}

	public String getString() throws PropertyException {
		if(type != PropertyType.STRING) throw new PropertyException();
		else return asString;
	}
	
	public int getInt() throws PropertyException {
		if(type != PropertyType.INTEGER) throw new PropertyException();
		else return asInt;
	}
	
	public double getDouble() throws PropertyException {
		if(type != PropertyType.DOUBLE) throw new PropertyException();
		else return asDouble;
	}
	
	public Date getDate() throws PropertyException {
		if(type != PropertyType.DATE) throw new PropertyException();
		else return asDate;
	}
	
	public PropertyType getType() {
		return type;
	}
	
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
			return asDate.toString();
		case BOOLEAN:
			if(asBoolean) return "yes";
			else return "no";
		default:
			return "corrupted";
		}
	}
	
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
