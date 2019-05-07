package dbadapter;

import java.sql.Timestamp;
import java.util.ArrayList;

import datatypes.AddressData;
import datatypes.PersonData;

/**
 * Class representing an offer
 * 
 * @author swe.uni-due.de
 *
 */
public class Offer {

	private int id;
	private AddressData startPlace;
	private Timestamp startTime;
	private AddressData endPlace;
	private Timestamp endTime;
	private int numberOfSeats;
	private float luggageSize;
	private double price;
	private PersonData contactInfo;
	private ArrayList<Booking> bookings;

	public Offer(int id, AddressData startPlace, Timestamp startTime, AddressData endPlace, Timestamp endTime, int numberOfSeats, 
			float luggageSize, double price, PersonData contactInfo) {
		this.id = id;
		this.startPlace = startPlace;
		this.startTime = startTime;
		this.endPlace = endPlace;
		this.endTime = endTime;
		this.numberOfSeats = numberOfSeats;
		this.luggageSize = luggageSize;
		this.price = price;
		this.contactInfo = contactInfo;
		this.bookings = new ArrayList<Booking>();
	}

	public String toString() {
		return "Offer " + id + "\nstartPlace: " + startPlace.getStreet() + "," + startPlace.getTown() + "\nstartTime: " + startTime + 
				"\nendPlace: " + endPlace.getStreet() + "," + endPlace.getTown() + "\nendTime: " + endTime + "\nnumberOfSeats: " + numberOfSeats
				+ "\nluggageSize: " + luggageSize + "\nprice: " + price + "\ncontactInfo: " + contactInfo.getName(); 
	}

	public int getId() {
		return id;
	}
	
	public AddressData getStartPlace() {
		return startPlace;
	}

	public Timestamp getStartTime() {
		return startTime;
	}
	
	public AddressData getEndPlace() {
		return endPlace;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public float getLuggageSize() {
		return luggageSize;
	}

	public double getPrice() {
		return price;
	}
	
	public PersonData getContactInfo() {
		return contactInfo;
	}
	
	public ArrayList<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(ArrayList<Booking> bookings) {
		this.bookings = bookings;
	}
	/**
	 * Checking if this offer is available. All bookings for this offer are
	 * iteratively checked.
	 */
	public boolean available(float luggageSize1, int numberOfSeats1) {
		/*for (int i = 0; i < bookings.size(); i++) {
			if (bookings.get(i).overlap(arrivalTime, departureTime)) {
				return false;
			}
		}*/
		if(luggageSize1 <= this.luggageSize && numberOfSeats1 <= this.numberOfSeats) 
			return true;
		else
			return false;
		
		//return true;
	}
}
