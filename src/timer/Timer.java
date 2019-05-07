package timer;

import application.CSSApplication;

/**
 * Timer class to call the method checkPayment in the application. Main method
 * can be executed in a scheduled way.
 * 
 * @author swe.uni-due.de
 *
 */
public class Timer {

	public static void main(String[] args) {
		CSSApplication vrApp = new CSSApplication();
		vrApp.checkOffer();
		System.out
				.println("All offers in the past are successfully deleted");
	}
}/*

package timer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import datatypes.AddressData;
import datatypes.PersonData;
import dbadapter.Booking;
import dbadapter.Configuration;
import dbadapter.Offer;

public class testingSQL {
public static void main() {
	ArrayList<Offer> result = new ArrayList<Offer>();

	String sqlSelect = "SELECT * FROM Offer WHERE startPlace = ? AND startTime <= ? AND endPlace = ? AND endTime <= ? AND numberOfSeats >= ? AND luggageSize >= ?";

	// Query all offers that fits to the given criteria.
	try (Connection connection = DriverManager.getConnection(
			"jdbc:" + Configuration.getType() + "://"
					+ Configuration.getServer() + ":"
					+ Configuration.getPort() + "/"
					+ Configuration.getDatabase(), Configuration.getUser(),
			Configuration.getPassword())) {

		try (PreparedStatement ps = connection.prepareStatement(sqlSelect);
				PreparedStatement psSelectB = connection
						.prepareStatement(sqlSelect)) {
			ps.setString(1, "");
			ps.setTimestamp(2, startTime);
			ps.setString(3, endPlace.getStreet() + ", "+ endPlace.getTown());
			ps.setTimestamp(4, endTime);
			ps.setInt(5, numberOfSeats);
			ps.setFloat(6, luggageSize);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Offer temp = new Offer(rs.getInt(1),
								 new AddressData(rs.getString(2), rs.getString(3)),
								 rs.getTimestamp(4),
								 new AddressData(rs.getString(5), rs.getString(6)),
								 rs.getTimestamp(7), rs.getInt(8), rs.getFloat(9),
							     rs.getDouble(10),
							     new PersonData(rs.getString(11), rs.getString(12)));
					psSelectB.setInt(1, temp.getId());

					// Query all bookings for the offer to check if its
					// available.
					try (ResultSet brs = psSelectB.executeQuery()) {
						ArrayList<Booking> bookings = new ArrayList<Booking>();
						while (brs.next()) {
							bookings.add(new Booking(brs.getInt(1), brs
									.getTimestamp(2), brs.getTimestamp(3),
									brs.getTimestamp(4), brs.getBoolean(5),
									new PersonData(brs.getString(6), brs
											.getString(7)), brs
											.getDouble(8), brs.getInt(9)));
						}
						temp.setBookings(bookings);
					}
					if (temp.available(startTime, endTime))
						result.add(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

	System.out.println(result);
	}

}
*/