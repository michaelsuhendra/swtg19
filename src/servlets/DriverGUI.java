package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.CSSApplication;
import datatypes.AddressData;
import datatypes.PersonData;

/**
 * Contains GUI for staffmember
 * 
 * @author swe.uni-due.de
 *
 */
public class DriverGUI extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * doGet contains the insertOffer form
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// set pagetitle und navtype
		request.setAttribute("navtype", "Driver");
		request.setAttribute("pagetitle", "Create Offer");

		// Dispatch request to template engine
		try {
			request.getRequestDispatcher("/templates/defaultWebpageD.ftl").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Contains handling of insertOffer call
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("navtype", "Driver");

		// Check whether the call is insertOffer or not
		if (request.getParameter("action").equals("insertOffer")) {

			// Append parameter of request
			String street1 = (String) request.getParameter("street1");
			String town1 = (String) request.getParameter("town1");
			String startTime = (String) request.getParameter("startTime");
			String street2 = (String) request.getParameter("street2");
			String town2 = (String) request.getParameter("town2");
			String endTime = (String) request.getParameter("endTime");
			String numberOfSeats = (String) request.getParameter("numberOfSeats");
			String trunkSize = (String) request.getParameter("trunkSize");
			String price = (String) request.getParameter("price");
			String name = (String) request.getParameter("name");
			String email = (String) request.getParameter("email");


			// Call application to insert offer
			new CSSApplication().createOffer(new AddressData(street1, town1), startTime, new AddressData(street2, town2),
					endTime, numberOfSeats, trunkSize, price, new PersonData(name, email));

			// Dispatch message to template engine
			try {
				request.setAttribute("pagetitle", "Create Offer");
				request.setAttribute("message", "A new offer is successfuly stored in the database.");
				request.getRequestDispatcher("/templates/showCreateOfferOk.ftl").forward(request, response);

			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			// Call doGet if request is not equal to insertOffer
		} else
			doGet(request, response);

	}
}