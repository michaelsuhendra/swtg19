package dbadapter;

import java.sql.Timestamp;

import datatypes.PersonData;

//Class representing a booking

public class Booking {

	int bid;
	Timestamp creationDate;
	int oid;
	PersonData passengerData;
	double price;
	
	public Booking(int bid, Timestamp creationDate, int oid, PersonData passengerData, double price) {
		super();
		this.bid = bid;
		this.creationDate = creationDate;
		this.oid = oid;
		this.passengerData = passengerData;
		this.price = price;
	}

	public int getId() {
		return bid;
	}

	public void setId(int bid) {
		this.bid = bid;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	
	public int getOfferId() {
		return oid;
	}
	
	public void setOfferId(int oid) {
		this.oid = oid;
	}
	
	public PersonData getPassengerData() {
		return passengerData;
	}

	public void setPassengerData(PersonData passengerData) {
		this.passengerData = passengerData;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
