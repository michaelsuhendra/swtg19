package interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;

import datatypes.AddressData;
import datatypes.PersonData;
import dbadapter.Booking;
import dbadapter.Offer;

/**
 * Interface for DBFacade to provide all necessary database function.
 * 
 * @author swe.uni-due.de
 *
 */
public interface IOffer {

	public ArrayList<Offer> getOffer(AddressData startPlace, Timestamp startTime, AddressData endPlace, Timestamp endTime, 
			int numberOfSeats, float luggageSize);

	/*public Booking bookingHolidayOffer(Timestamp arrivalTime,
			Timestamp departureTime, int hid, PersonData guestData, int persons);*/

	public void setAvailableOffer();

}
