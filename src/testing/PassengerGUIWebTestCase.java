package testing;

import org.junit.Before;
import org.junit.Test;

import net.sourceforge.jwebunit.junit.WebTester;

/**
 * This class performs a system test on the PassengerGUI using JWebUnit.
 * 
 * @author swe.uni-due.de
 *
 */
public class PassengerGUIWebTestCase {

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
	 * Test for the Passenger GUI in displaying the search offer form.
	 */
	@Test
	public void testSearchOfferPage() {
		// Start testing for guestgui
		tester.beginAt("passengergui");

		// Check all components of the search form
		tester.assertTitleEquals("Smart Mobility - Search Offers");
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
		tester.assertFormElementPresent("luggageSize");
		tester.assertButtonPresent("SelectOfferWebpage");

		// Submit the form with given parameters
		tester.setTextField("street1", "Road ABC");
		tester.setTextField("town1", "ABC");
		tester.setTextField("startTime", "2019-07-11T11:07");
		tester.setTextField("street2", "Road XYZ");
		tester.setTextField("town2", "XYZ");
		tester.setTextField("endTime", "2019-07-11T15:09");
		tester.setTextField("numberOfSeats", "3");
		tester.setTextField("luggageSize", "117,39");

		tester.clickButton("SelectOfferWebpage");

		// Check the representation of the table for an empty result
		tester.assertTablePresent("availableOffer");
		String[][] tableHeadings = { { "#", "Start Place", "Start Time", "End Place", "End Time", "Number of seats", "Luggage Size" } };
		tester.assertTableEquals("availableOffer", tableHeadings);
	
	}

}
