package test.onapos;

import static org.junit.Assert.fail;

import java.util.Date;

import onapos.Property;
import onapos.PropertyException;

import org.junit.Test;

public class PropertyTest {

	private Property property;
	
	@Test
	public void testPropertyString() {
		property = new Property("Test");
	}

	@Test
	public void testPropertyInt() {
		for(int i = Integer.MIN_VALUE;i<=Integer.MAX_VALUE;i++) {
			property = new Property(i);
			try {
				if (property.getInt() != i)
				fail("did not set integer correctly");
			} catch (PropertyException e) {
				fail("getInt() threw exception");
			}
		}
		
	}

	@Test
	public void testPropertyDouble() {
		for(double i = Double.MIN_VALUE;i<=Double.MAX_VALUE;i++) {
			property = new Property(i);
			try {
				if (property.getDouble() != i)
				fail("did not set double correctly");
			} catch (PropertyException e) {
				fail("getDouble() threw exception");
			}
		}
		
	}

	@Test
	public void testPropertyDate() {
		Date testDate = new Date();
		property = new Property(testDate);
		try {
			if (property.getDate() != testDate)
			fail("did not set date correctly");
		} catch (PropertyException e) {
			fail("getDate() threw exception");
		}
	}

	@Test(expected=Exception.class)
	public void testProperty() {
		
	}

	@Test
	public void testCompareTo() {
		fail();
	}

	@Test
	public void testGetString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDate() {
		fail("Not yet implemented");
	}

}
