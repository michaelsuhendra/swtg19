package dbadapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import datatypes.AddressData;
import datatypes.PersonData;
import interfaces.IOffer;

/**
 * Class which acts as the connector between application and database. Creates
 * Java objects from SQL returns. Exceptions thrown in this class will be
 * catched with a 500 error page.
 * 
 * @author swe.uni-due.de
 *
 */
public class DBFacade implements IOffer {
	private static DBFacade instance;

	/**
	 * Constructor which loads the corresponding driver for the chosen database
	 * type
	 */
	private DBFacade() {
		try {
			Class.forName("com." + Configuration.getType() + ".jdbc.Driver")
					.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Implementation of the Singleton pattern.
	 * 
	 * @return
	 */
	public static DBFacade getInstance() {
		if (instance == null) {
			instance = new DBFacade();
		}

		return instance;
	}

	/**
	 * Function that returns all appropriate offers from the database.
	 * @param startPlace
	 * @param startTime
	 * @param endPlace
	 * @param endTime
	 * @param numberOfSeats
	 * @param luggageSize
	 * @return result
	 */
	public ArrayList<Offer> getOffer(AddressData startPlace, Timestamp startTime, AddressData endPlace, Timestamp endTime, int numberOfSeats, float luggageSize) {
		ArrayList<Offer> result = new ArrayList<Offer>();

		// Declare the necessary SQL queries.
		String sqlSelect = "SELECT * FROM Offer WHERE startPlace = ? AND startTime <= ? AND endPlace = ? AND endTime <= ? AND numberOfSeats >= ? AND luggageSize >= ?";
		//String sqlSelectB = "SELECT * FROM Booking WHERE hid = ?";

		// Query all offers that fits to the given criteria.
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {

			try (PreparedStatement ps = connection.prepareStatement(sqlSelect);
					/*PreparedStatement psSelectB = connection
							.prepareStatement(sqlSelectB)*/) {
				ps.setString(1, startPlace.getStreet() + ", " + startPlace.getTown());
				ps.setTimestamp(2, startTime);
				ps.setString(3, endPlace.getStreet() + ", " + endPlace.getTown());
				ps.setTimestamp(4, endTime);
				ps.setInt(5, numberOfSeats);
				ps.setFloat(6, luggageSize);
				
				/*System.out.println(startPlace.getStreet() + ", " + startPlace.getTown());
				System.out.println(startTime);
				System.out.println(endPlace.getStreet() + ", " + endPlace.getTown());
				System.out.println(endTime);
				System.out.println(numberOfSeats);
				System.out.println(luggageSize);*/
				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						String street1 = rs.getString(1).substring(0, rs.getString(1).indexOf(","));
						String town1 = rs.getString(1).substring(rs.getString(1).indexOf(",") + 1, rs.getString(1).length());
						String street2 = rs.getString(3).substring(0, rs.getString(3).indexOf(","));
						String town2 = rs.getString(3).substring(rs.getString(3).indexOf(",") + 1, rs.getString(3).length());
						String name = rs.getString(8).substring(0, rs.getString(8).indexOf(","));
						String email = rs.getString(8).substring(rs.getString(8).indexOf(",") + 1, rs.getString(8).length());

						Offer temp = new Offer(rs.getInt(9),
									 new AddressData(street1, town1),
									 rs.getTimestamp(2),
									 new AddressData(street2, town2),
									 rs.getTimestamp(4), rs.getInt(5), rs.getFloat(6),
								     rs.getDouble(7),
								     new PersonData(name, email));
						
						if(temp.available(luggageSize, numberOfSeats))
								result.add(temp);
					
				/*try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Offer temp = new Offer(rs.getInt(1),
									 new AddressData(rs.getString(2), rs.getString(3)),
									 rs.getTimestamp(4),
									 new AddressData(rs.getString(5), rs.getString(6)),
									 rs.getTimestamp(7), rs.getInt(8), rs.getFloat(9),
								     rs.getDouble(10),
								     new PersonData(rs.getString(11), rs.getString(12)));*/
						/*ps.SelectB.setInt(1, temp.getId());*/

						// Query all bookings for the offer to check if its
						// available.
						/*try (ResultSet brs = psSelectB.executeQuery()) {
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
						}*/
						
						/*if (temp.available(startTime, endTime))
							result.add(temp);*/
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Inserts a new offer in the database.
	 * @param startPlace
	 * @param startTime
	 * @param endPlace
	 * @param endTime
	 * @param numberOfSeats
	 * @param luggageSize
	 * @param price
	 * @param contactInfo
	 */
	public void createOffer(AddressData startPlace, Timestamp startTime, AddressData endPlace, Timestamp endTime,
			int numberOfSeats, float luggageSize, double price, PersonData contactInfo) {

		// Declare SQL query to insert offer.
		String sqlInsert = "INSERT INTO offer (startPlace, startTime, endPlace, endTime, numberOfSeats, luggageSize, price, contactInfo) VALUES (?,?,?,?,?,?,?,?)";

		// Insert offer into database.
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {

			try (PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
				ps.setString(1, startPlace.getStreet() + ", " + startPlace.getTown());
				ps.setTimestamp(2, startTime);
				ps.setString(3, endPlace.getStreet() + ", "+ endPlace.getTown());
				ps.setTimestamp(4, endTime);
				ps.setInt(5, numberOfSeats);
				ps.setFloat(6, luggageSize);
				ps.setDouble(7,  price);
				ps.setString(8,  contactInfo.getName() + ", " + contactInfo.getEmail());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Inserts a booking into the database if there are enough capacities
	 * 
	 * @param oid
	 * @param passengerData
	 * @param numberOfSeats
	 * @param luggageSize
	 * @return new booking object if available or null if not available
	 */
	public Booking bookingOffer(int oid, PersonData passengerData, int numberOfSeats, float luggageSize) {
		Offer offer = null;
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		Booking booking = null;

		// Declare necessary SQL queries.
		String sqlSelectO = "SELECT * FROM offer WHERE id=?";
		String sqlInsertBooking = "INSERT INTO Booking (creationDate, oid, passengerData, price) VALUES (?,?,?,?)";
		String sqlSelectB = "SELECT * FROM Booking WHERE bid=?";

		// Get selected offer
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {
			try (PreparedStatement psSelect = connection
					.prepareStatement(sqlSelectO);
					PreparedStatement psSelectB = connection
							.prepareStatement(sqlSelectB);
					PreparedStatement psInsert = connection.prepareStatement(
							sqlInsertBooking,
							PreparedStatement.RETURN_GENERATED_KEYS)) {
				
				psSelect.setInt(1, oid);
				
				try (ResultSet rs = psSelect.executeQuery()) {
					if (rs.next()) {
						String street1 = rs.getString(1).substring(0, rs.getString(1).indexOf(","));
						String town1 = rs.getString(1).substring(rs.getString(1).indexOf(",") + 1, rs.getString(1).length());
						String street2 = rs.getString(3).substring(0, rs.getString(3).indexOf(","));
						String town2 = rs.getString(3).substring(rs.getString(3).indexOf(",") + 1, rs.getString(3).length());
						String name = rs.getString(8).substring(0, rs.getString(8).indexOf(","));
						String email = rs.getString(8).substring(rs.getString(8).indexOf(",") + 1, rs.getString(8).length());

						offer = new Offer(rs.getInt(9),
									 new AddressData(street1, town1),
									 rs.getTimestamp(2),
									 new AddressData(street2, town2),
									 rs.getTimestamp(4), rs.getInt(5), rs.getFloat(6),
								     rs.getDouble(7),
								     new PersonData(name, email));
					}
				}
				
				// Check if offer is still available
				if (offer != null) {
					psSelectB.setInt(1, oid);
					try (ResultSet brs = psSelectB.executeQuery()) {
						while (brs.next()) {
							String name = brs.getString(3).substring(0, brs.getString(3).indexOf(","));
							String email = brs.getString(3).substring(brs.getString(3).indexOf(",") + 1, brs.getString(3).length());
							bookings.add(new Booking(brs.getInt(5), brs.getTimestamp(1),
									brs.getInt(2), new PersonData(name, email), brs.getFloat(4)));
						}
						offer.setBookings(bookings);
					}

					// Insert new booking
					if (offer.available(luggageSize, numberOfSeats)) {
						Timestamp creationDate = new Timestamp(new Date().getTime());
						System.out.println(creationDate);
						
						booking = new Booking(0, creationDate, offer.getId(), passengerData, offer.getPrice());
						//booking = new Booking(0, cd2, offer.getId(), passengerData, offer.getPrice());
						//error on testing line 277
						psInsert.setTimestamp(1, booking.getCreationDate());
						psInsert.setInt(2, booking.getOfferId());
						psInsert.setString(3, passengerData.getName() + ", " + passengerData.getEmail());
						psInsert.setDouble(4, booking.getPrice());
						psInsert.executeUpdate();
						try (ResultSet generatedKeys = psInsert
								.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								booking.setId(generatedKeys.getInt(1));
							}
						}
						
						//updateOffer
						try(PreparedStatement psUpdate = connection.
								prepareStatement("update offer set numberOfSeats = numberOfSeats - ?, "
										+ "luggageSize = luggageSize - ? where id = ?")){
							psUpdate.setInt(1, numberOfSeats);
							psUpdate.setFloat(2, luggageSize);
							psUpdate.setInt(3, oid);
							psUpdate.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						 
					} else
						offer = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return booking;
	}

	/**
	 * Delete all bookings in the past.
	 */
	public void setAvailableOffer() {

		// Declare necessary SQL statement.
		String deleteO = "DELETE FROM Offer WHERE numberOfSeats = 0 AND startTime < CURRENT_TIMESTAMP";

		// Update Database.
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {
			try (PreparedStatement psDelete = connection
					.prepareStatement(deleteO)) {
				psDelete.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if offer with given id exists.
	 * 
	 * @param oid
	 * @return
	 */
	public boolean checkOfferById(int oid) {

		// Declare necessary SQL query.
		String queryO = "SELECT FROM offer WHERE id=?";

		// query data.
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://"
						+ Configuration.getServer() + ":"
						+ Configuration.getPort() + "/"
						+ Configuration.getDatabase(), Configuration.getUser(),
				Configuration.getPassword())) {
			try (PreparedStatement psSelect = connection
					.prepareStatement(queryO)) {
				psSelect.setInt(1, oid);
				try (ResultSet rs = psSelect.executeQuery()) {
					return rs.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
}
