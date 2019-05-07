package testing;

import org.junit.Before;
import org.junit.Test;

import net.sourceforge.jwebunit.junit.WebTester;

/**
 * This class performs a system test on the GuestGUI using JWebUnit.
 * 
 * @author swe.uni-due.de
 *
 */
public class DriverGUIWebTestCase {

	private WebTester tester;

	/**
	 * Create a new WebTester object that performs the test.
	 */
	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost:8080/CSS/");
	}

	/**
	 * Test for the Driver GUI in displaying the create offer form.
	 */
	@Test
	public void testCreateOfferPage() {
		// Start testing for guestgui
		tester.beginAt("drivergui");

		// Check all components of the search form
		tester.assertTitleEquals("Smart Mobility - Create Offer");
		tester.assertFormPresent();
		tester.assertTextPresent("Required Information");
		//tester.assertTextPresent("Departure Address");
		tester.assertFormElementPresent("street1");
		tester.assertFormElementPresent("town1");
		//tester.assertTextPresent("Departure Time");
		tester.assertFormElementPresent("startTime");
		//tester.assertTextPresent("Arrival Address");
		tester.assertFormElementPresent("street2");
		tester.assertFormElementPresent("town2");
		//tester.assertTextPresent("Arrival Time");
		tester.assertFormElementPresent("endTime");
		//tester.assertTextPresent("Number of Seats");
		tester.assertFormElementPresent("numberOfSeats");
		//tester.assertTextPresent("Luggage Size");
		tester.assertFormElementPresent("trunkSize");
		//tester.assertTextPresent("Price");
		tester.assertFormElementPresent("price");
		//tester.assertTextPresent("Name");
		tester.assertFormElementPresent("name");
		//tester.assertTextPresent("Email");
		tester.assertFormElementPresent("email");
		tester.assertButtonPresent("submit");

		// Submit the form with given parameters
		tester.setTextField("street1", "Road ABC");
		tester.setTextField("town1", "ABC");
		tester.setTextField("startTime", "2019-07-11T11:07");
		tester.setTextField("street2", "Road XYZ");
		tester.setTextField("town2", "XYZ");
		tester.setTextField("endTime", "2019-07-11T15:09");
		tester.setTextField("numberOfSeats", "3");
		tester.setTextField("trunkSize", "117,39");
		tester.setTextField("price", "200");
		tester.setTextField("name", "Max Mustermann");
		tester.setTextField("email", "max@xyz.com");

		tester.clickButton("submit");

		// Check for a success representation
		tester.assertTextPresent("A new offer is successfuly stored in the database.");
	}

}
