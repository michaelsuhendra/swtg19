package application;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import datatypes.AddressData;
import datatypes.PersonData;
import dbadapter.Booking;
import dbadapter.DBFacade;
import dbadapter.Offer;
import interfaces.DCmds;
import interfaces.PCmds;

/**
 * This class contains the VRApplication which acts as the interface between all
 * components.
 * 
 * @author swe.uni-due.de
 *
 */
public class CSSApplication implements DCmds, PCmds {

	private static CSSApplication instance;

	/**
	 * Implementation of the Singleton pattern.
	 * 
	 * @return
	 */
	public static CSSApplication getInstance() {
		if (instance == null) {
			instance = new CSSApplication();
		}

		return instance;
	}

	/**
	 * Calls DBFacade method to retrieve all offers fitting to the given
	 * parameters.
	 * @param startPlace
	 * @param startTime
	 * @param endPlace
	 * @param endTime
	 * @param numberOfSeats
	 * @param luggageSize
	 * @return availableOffers
	 */
	public ArrayList<Offer> getOffer(AddressData startPlace, String startTime, AddressData endPlace, String endTime, String numberOfSeats, String luggageSize) {
		ArrayList<Offer> result = null;

		// Parse string attributes to correct datatype
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date date = dateFormat.parse(startTime);
			long time = date.getTime();
			Timestamp startTimeSQL = new Timestamp(time);
			Date date2 = dateFormat.parse(endTime);
			time = date2.getTime();
			Timestamp endTimeSQL = new Timestamp(time);
			int personsSQL = Integer.parseInt(numberOfSeats);
			float trunkSizeSQL = Float.parseFloat(luggageSize);
			result = DBFacade.getInstance().getOffer(startPlace, startTimeSQL, endPlace, endTimeSQL, personsSQL, trunkSizeSQL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Forwards a new offer to the database.
	 * @param startPlace
	 * @param startTime
	 * @param endPlace
	 * @param endTime
	 * @param numberOfSeats
	 * @param luggageSize
	 * @param price
	 * @param contactInfo
	 * 
	 */
	public void createOffer(AddressData startPlace, String startTime, AddressData endPlace, String endTime, String numberOfSeats, 
			String luggageSize, String price, PersonData contactInfo ) {
		

		// Parse inputs to correct datatypes
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date date = dateFormat.parse(startTime);
			long time = date.getTime();
			Timestamp startTimeSQL = new Timestamp(time);
			date = dateFormat.parse(endTime);
			time = date.getTime();
			Timestamp endTimeSQL = new Timestamp(time);
			int personsSQL = Integer.parseInt(numberOfSeats);
			float trunkSizeSQL = Float.parseFloat(luggageSize);
			double priceSQL = Double.parseDouble(price);
			DBFacade.getInstance().createOffer(startPlace, startTimeSQL, endPlace, endTimeSQL, personsSQL, trunkSizeSQL, priceSQL, contactInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Forwards a booking request to the database and waits for the new booking
	 * offer. This will be returned to the GUI after.
	 * 
	 * @param oid
	 * @param passengerData
	 * @param numberOfSeats
	 * @param luggageSize
	 * @return
	 */
	public Booking bookOffer(String oid, PersonData passengerData, String numberOfSeats, String luggageSize) {

		// pre: ho−>one(h:HolidayOffer|h.id = hid)
		assert preOfferAvailable(Integer.parseInt(oid)) : "Precondition not satisfied";

		// Create result object
		Booking okfail = null;

		// Parse inputs to correct datatypes
		try {
			int oidSQL = Integer.parseInt(oid);
			int numberOfSeatsSQL = Integer.parseInt(numberOfSeats);
			float luggageSizeSQL = Float.parseFloat(luggageSize);
					
			okfail = DBFacade.getInstance().bookingOffer(oidSQL, passengerData, numberOfSeatsSQL, luggageSizeSQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return okfail;
	}

	/**
	 * Initiates deleting of all bookings older than the present time.
	 */
	public void checkOffer() {
		DBFacade.getInstance().setAvailableOffer();
	}

	/**
	 * Checks precondition offer exists (before a booking on that offer is done)
	 * 
	 * @param id
	 * @return
	 */
	private boolean preOfferAvailable(int id) {
		return DBFacade.getInstance().checkOfferById(id);
	}
}
