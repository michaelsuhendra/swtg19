package interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;

import datatypes.AddressData;
import datatypes.PersonData;
import dbadapter.Booking;
import dbadapter.Offer;

/**
 * Interface that provides all methods for the interaction with the staffmember.
 * 
 * @author swe.uni-due.de
 *
 */
public interface PCmds {

	public ArrayList<Offer> getOffer(AddressData startPlace, String startTime, AddressData endPlace, String endTime, String numberOfSeats, String luggageSize);

	public Booking bookOffer(String oid, PersonData passengerData, String numberOfSeats, String luggageSize);
}
