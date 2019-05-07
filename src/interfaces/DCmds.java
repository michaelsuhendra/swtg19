package interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;

import datatypes.AddressData;
import datatypes.PersonData;
import dbadapter.Booking;
import dbadapter.Offer;

/**
 * Interface that provides all method to interact with a guest.
 * 
 * @author swe.uni-due.de
 *
 */
public interface DCmds {

	/*public ArrayList<Offer> getOffer(AddressData startPlace, String startTime, AddressData endPlace, String endTime, String numberOfSeats, String luggageSize);*/

	public void createOffer(AddressData startPlace, String startTime, AddressData endPlace, String endTime, String numberOfSeats, 
			String luggageSize, String price, PersonData contactInfo );

	

}
