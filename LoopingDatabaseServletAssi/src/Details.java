import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Servlet implementation class Customers
 */
@WebServlet("/Details")
public class Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String messageDetail;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String custId = request.getParameter("CustomerID");
			messageDetail = "";
			//URL of Oracle database server
			String url = "jdbc:oracle:thin:testuser/password@localhost";

			//properties for creating connection to Oracle database
			Properties props = new Properties();
			props.setProperty("user", "testdb");
			props.setProperty("password", "password");

			Class.forName("oracle.jdbc.driver.OracleDriver");
			//creating connection to Oracle database using JDBC
			Connection conn = DriverManager.getConnection(url, props);
			
			String sql = "";
			if (custId == null) {
				sql = "SELECT * FROM demo_customers";
			} else {
				sql = "SELECT * FROM demo_customers WHERE customer_id = " + custId;
			}
				//creating PreparedStatement object to execute query
				PreparedStatement preStatement = conn.prepareStatement(sql);
				ResultSet result = preStatement.executeQuery();
				messageDetail += "<table class=\"table table-striped\"><thead><tr><th>Customer ID</th><th>Name</th><th>Street Address 1</th><th>Street Address 2</th><th>City</th><th>State</th><th>Zipcode</th><th>Phone #</th><th>Credit Limit</th></tr></thead>";
				while (result.next()) {
					String name = result.getString("cust_first_name") + " " + result.getString("cust_last_name");
					String streetAdd2 = result.getString("cust_street_address2");
					if (streetAdd2 == null) {
						streetAdd2 = "";
					}
					messageDetail += "<tbody><tr><td>" + result.getString("customer_id") + "</td><td>" + name + "</td><td>" + result.getString("cust_street_address1") + "</td><td>" + streetAdd2 + "</td><td>" + result.getString("cust_city") + "</td><td>" + result.getString("cust_state") + "</td><td>" + result.getString("cust_postal_code") + "</td><td>" + result.getString("phone_number1") + "</td><td>" + result.getString("credit_limit") + "</td></tr></tbody>";
				}
				messageDetail += "</table>";
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("messageDetail", messageDetail);
		getServletContext().getRequestDispatcher("/DetailsOutput.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
