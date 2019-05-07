package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.CSSApplication;
import datatypes.AddressData;
import datatypes.PersonData;
import dbadapter.Offer;

/**
 * Class responsible for the GUI of the guest
 * 
 * @author swe.uni-due.de
 *
 */
public class PassengerGUI extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * doGet is responsible for search form and booking form
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// Set navtype
		request.setAttribute("navtype", "Passenger");

		// Catch error if there is no page contained in the request
		String action = (request.getParameter("action") == null) ? "" : request.getParameter("action");

		// Case: Request booking form
		if (action.equals("selectOffer")) {
			// Set request attributes
			request.setAttribute("pagetitle", "Book Offer");
			request.setAttribute("oid", request.getParameter("oid"));

			// Dispatch request to template engine
			try {
				request.getRequestDispatcher("/templates/showBookOfferForm.ftl").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Otherwise show search form
		} else {

			// Set request attributes
			request.setAttribute("pagetitle", "Search Offers");

			// Dispatch request to template engine
			try {
				request.getRequestDispatcher("/templates/defaultWebpageP.ftl").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * doPost manages handling of submitted forms.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// Set attribute for navigation type.
		request.setAttribute("navtype", "Passenger");

		// Generate and show results of a search
		if (request.getParameter("action").equals("browseAvailableOffer")) {
			request.setAttribute("pagetitle", "Search results");
			List<Offer> availableOffers = null;

			// Call application to request the results
			try {
				availableOffers = CSSApplication.getInstance().getOffer(
						new AddressData(request.getParameter("street1"), request.getParameter("town1")), request.getParameter("startTime"),
						new AddressData(request.getParameter("street2"), request.getParameter("town2")), request.getParameter("endTime"),
						request.getParameter("numberOfSeats"), request.getParameter("luggageSize"));

				// Dispatch results to template engine
				if(availableOffers != null) {
					request.setAttribute("availableOffers", availableOffers);
					request.getRequestDispatcher("/templates/offersRepresentation.ftl").forward(request, response);
				}
				else {
					request.setAttribute("errormessage", "No offers with the criteria are found");
					request.getRequestDispatcher("/templates/error.ftl").forward(request, response);
				}
			} catch (Exception e1) {
				try {
					request.setAttribute("errormessage", "Database error: please contact the administator");
					request.getRequestDispatcher("/templates/error.ftl").forward(request, response);
				} catch (Exception e) {
					request.setAttribute("errormessage", "System error: please contact the administrator");
					e.printStackTrace();
				}
				e1.printStackTrace();
			}
			// Insert booking into database
		} else if (request.getParameter("action").equals("bookOffer")) {
			// Decide whether booking was successful or not
			if (CSSApplication.getInstance().bookOffer(request.getParameter("oid"),
					new PersonData(request.getParameter("name"), request.getParameter("email")),
					request.getParameter("numberOfSeats"),
					request.getParameter("luggageSize")) != null) {

				// Set request attributes
				request.setAttribute("pagetitle", "Booking Successful");
				request.setAttribute("message",
						"Booking successfully created. You will receive a confirmation mail shortly");

				// Dispatch to template engine
				try {
					request.getRequestDispatcher("/templates/okRepresentation.ftl").forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}

				// Catch booking error and print an error on the gui
			} else {
				request.setAttribute("pagetitle", "Booking failed");
				request.setAttribute("message",
						"Booking failed. The selected offer is no longer available for your selected parameters.");

				try {
					request.getRequestDispatcher("/templates/failInfoRepresentation.ftl").forward(request,
							response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}

			}
			// If there is no page request, call doGet to show standard gui for
			// guest
		} else
			doGet(request, response);
	}
}