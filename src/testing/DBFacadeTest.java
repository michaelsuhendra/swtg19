package testing;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import datatypes.AddressData;
import datatypes.PersonData;
import dbadapter.Booking;
import dbadapter.Configuration;
import dbadapter.DBFacade;
import dbadapter.Offer;

/**
 * Testing our DBFacade.
 * 
 * @author swe.uni-due.de
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DBFacade.class)
public class DBFacadeTest {

	private Connection stubCon;
			//search
			private String sqlSelect = "SELECT * FROM Offer WHERE startPlace = ? AND startTime <= ? AND endPlace = ? AND endTime <= ? AND numberOfSeats >= ? AND luggageSize >= ?";
			//createOffer
			private String sqlInsert = "INSERT INTO offer (startPlace, startTime, endPlace, endTime, numberOfSeats, luggageSize, price, contactInfo) VALUES (?,?,?,?,?,?,?,?)";
			//book
			private String sqlSelectO = "SELECT * FROM offer WHERE id=?";
			private String sqlInsertBooking = "INSERT INTO Booking (creationDate, oid, passengerData, price) VALUES (?,?,?,?)";
			private String sqlSelectB = "SELECT * FROM Booking WHERE bid=?";
			private String sqlUpdate = "update offer set numberOfSeats = numberOfSeats - ?, luggageSize = luggageSize - ? where id = ?";
			//removeOffer
			private String sqlDeleteO = "DELETE FROM Offer WHERE numberOfSeats = 0 AND startTime < CURRENT_TIMESTAMP";
	
	private PreparedStatement ps;
	private PreparedStatement psSelectB;
	private ResultSet rs;
	private ResultSet brs;
	//private ResultSet of;
	
	private PreparedStatement psInsert;
	private PreparedStatement psDelete;
	private PreparedStatement psInsertBooking;
	private PreparedStatement psSelectO;

	/**
	 * Preparing classes with static methods
	 */
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DriverManager.class);

		// Declare necessary SQL queries
		//search
		sqlSelect = "SELECT * FROM Offer WHERE startPlace = ? AND startTime <= ? AND endPlace = ? AND endTime <= ? AND numberOfSeats >= ? AND luggageSize >= ?";
		//createOffer
		sqlInsert = "INSERT INTO offer (startPlace, startTime, endPlace, endTime, numberOfSeats, luggageSize, price, contactInfo) VALUES (?,?,?,?,?,?,?,?)";
		//book
		sqlSelectO = "SELECT * FROM offer WHERE id=?";
		sqlInsertBooking = "INSERT INTO Booking (creationDate, oid, passengerData, price) VALUES (?,?,?,?)";
		sqlSelectB = "SELECT * FROM Booking WHERE bid=?";
		sqlUpdate = "update offer set numberOfSeats = numberOfSeats - ?, luggageSize = luggageSize - ? where id = ?";
		//removeOffer
		sqlDeleteO = "DELETE FROM Offer WHERE numberOfSeats = 0 AND startTime < CURRENT_TIMESTAMP";

	
		// Mock return values
		ps = mock(PreparedStatement.class);
		psSelectB = mock(PreparedStatement.class);
		rs = mock(ResultSet.class);
		brs = mock(ResultSet.class);
		
		//of = mock(ResultSet.class);
		
		psInsert = mock(PreparedStatement.class);
		psDelete = mock(PreparedStatement.class);
		psInsertBooking = mock(PreparedStatement.class);
		psSelectO = mock(PreparedStatement.class);

		try {
			// Setting up return values for connection and statements
			stubCon = mock(Connection.class);
			PowerMockito.when(DriverManager.getConnection(
					"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
							+ Configuration.getPort() + "/" + Configuration.getDatabase(),
					Configuration.getUser(), Configuration.getPassword())).thenReturn(stubCon);

			when(stubCon.prepareStatement(sqlSelect)).thenReturn(ps);
			when(stubCon.prepareStatement(sqlSelectB)).thenReturn(psSelectB);
			when(ps.executeQuery()).thenReturn(rs);
			when(psSelectB.executeQuery()).thenReturn(brs);
			
			when(stubCon.prepareStatement(sqlInsert)).thenReturn(psInsert);
			when(psInsert.executeUpdate()).thenReturn(1);
			
			when(stubCon.prepareStatement(sqlDeleteO)).thenReturn(psDelete);
			when(psDelete.executeUpdate()).thenReturn(1);
			
			when(stubCon.prepareStatement(sqlSelectO)).thenReturn(psSelectO);
			when(psSelectO.executeQuery()).thenReturn(rs);
			
			when(stubCon.prepareStatement(sqlInsertBooking)).thenReturn(psInsertBooking);
			when(psInsertBooking.executeUpdate()).thenReturn(1);

			// Setting up return values for methods
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getInt(9)).thenReturn(3);
			when(rs.getString(1)).thenReturn("Road ABC, ABC");
			when(rs.getTimestamp(2)).thenReturn(Timestamp.valueOf("2019-11-04 00:00:00"));
			when(rs.getString(3)).thenReturn("Road XYZ, XYZ");
			when(rs.getTimestamp(4)).thenReturn(Timestamp.valueOf("2019-11-04 02:00:00"));
			when(rs.getInt(5)).thenReturn(7);
			when(rs.getFloat(6)).thenReturn((float) 40.5);
			when(rs.getDouble(7)).thenReturn(117.39);
			when(rs.getString(8)).thenReturn("Charlie, charlie@gmail.com");


			// Setting up return values for corresponding booking
			when(brs.next()).thenReturn(true).thenReturn(false);
			when(brs.getInt(5)).thenReturn(42042);
			when(brs.getTimestamp(1)).thenReturn(Timestamp.valueOf("2019-01-03 20:00:00"));
			when(brs.getInt(2)).thenReturn(3);
			when(brs.getString(3)).thenReturn("Bob, bob@wazzup.com");
			when(brs.getDouble(4)).thenReturn(117.39);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Testing getOffer with non-empty results.
	 */
	@Test
	public void testGetOffer() {

		// Select a time where no booking exists
		AddressData startPlace = new AddressData("Road ABC", "ABC");
		Timestamp startTime = Timestamp.valueOf("2019-11-04 00:00:00");
		AddressData endPlace = new AddressData("Road XYZ", "XYZ");
		Timestamp endTime = Timestamp.valueOf("2019-11-04 02:00:00");
		
		ArrayList<Offer> offer = DBFacade.getInstance().getOffer(startPlace, startTime, endPlace, endTime, 1, 1);

		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlSelect);
			verify(ps, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values
		assertTrue(offer.size() == 1);
		assertTrue(offer.get(0).getId() == 3);
		assertTrue(offer.get(0).getPrice() == 117.39);
		
		// ...
		
		System.out.println("Test for getOffer passed");

	}

	/**
	 * Testing getOffer with empty result.
	 */
	@Test
	public void testGetOfferEmpty() {

		// Select an offer that doesn't exist
			AddressData startPlace = new AddressData("Road XYZ", "XYZ");
			Timestamp startTime = Timestamp.valueOf("2018-12-04 00:00:00");
			AddressData endPlace = new AddressData("Road XYZ", "XYZ");
			Timestamp endTime = Timestamp.valueOf("2018-12-04 11:30:00");
			
			ArrayList<Offer> offer = DBFacade.getInstance().getOffer(startPlace, startTime, endPlace, endTime, 1, 99999);

		// Verify how often a method has been called
		try {
			verify(stubCon, times(1)).prepareStatement(sqlSelect);
			verify(ps, times(1)).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Verify return values
		assertTrue(offer.size() == 0);
		
		System.out.println("Test for getOfferEmpty passed");

	}
	
	/**
	 * Testing createOffer
	 */
	@Test
	public void testCreateOffer() {
		
		Timestamp startTime = Timestamp.valueOf("2019-01-19 00:00:00");
		Timestamp endTime = Timestamp.valueOf("2019-01-19 01:00:00");
		AddressData startPlace = new AddressData("Road ABC", "ABC");
		AddressData endPlace = new AddressData("Road XYZ", "XYZ");
		PersonData contactInfo = new PersonData("Charlie", "charlie@gmail.com");
		
		DBFacade.getInstance().createOffer(startPlace, startTime, endPlace, endTime, 1, 1, 1, contactInfo);
		
		try {
			verify(stubCon, times(1)).prepareStatement(sqlInsert);
			verify(psInsert, times(1)).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//verify return values
		
		System.out.println("Test for createOffer passed");
	}
	
	
	/**
	 * Testing removeOffer
	 */
	@Test
	public void testRemoveOffer() {
		
		DBFacade.getInstance().setAvailableOffer();
		
		try {
			verify(stubCon, times(1)).prepareStatement(sqlDeleteO);
			verify(psDelete, times(1)).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Test for removeOffer passed");
		
	}
	
	/**
	 * Testing bookingOffer
	 */
	@Test
	public void testBookingOffer() {
		
		PersonData passengerData = new PersonData("Delta", "delta@yahoo.com");
		Booking bo = DBFacade.getInstance().bookingOffer(3, passengerData, 1, 1);
		bo.setId(42042);
		
		
		try {
			verify(stubCon, times(1)).prepareStatement(sqlSelectO);
			verify(stubCon, times(1)).prepareStatement(sqlSelectB);
			verify(stubCon, times(1)).prepareStatement(sqlInsertBooking, PreparedStatement.RETURN_GENERATED_KEYS);
			verify(psSelectO, times(1)).executeQuery();
			verify(psSelectB, times(1)).executeQuery();
			//verify(psInsertBooking, times(1)).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertTrue(bo != null);
		assertTrue(bo.getId() == 42042);
		
		//System.out.println("Test for bookingOffer passed");
	}
	
	
}
