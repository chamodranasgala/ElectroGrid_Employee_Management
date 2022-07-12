package com;

import java.sql.*;

public class Employee {

	// Database Connection
	private Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/emp", "root", "root");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	// Read
	public String readEmployees() {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";

			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Employee Name</th> <th>Email</th> <th>Phone Number</th>"
					+ "<th>Gender</th> <th>Update</th><th>Remove</th></tr>";

			String query = "select * from employees";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String eid = Integer.toString(rs.getInt("eid"));
				String ename = rs.getString("ename");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				String gender = rs.getString("gender");
				
				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + eid + "'>" + ename + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + gender + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-itemid='" + eid + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + eid + "'></td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the employees.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// Insert
	public String insertEmployee(String ename, String email, String phone, String gender) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into employees (`eid`,`ename`,`email`,`phone`,`gender`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, ename);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, phone);
			preparedStmt.setString(5, gender);

			// execute the statement
			preparedStmt.execute();

			con.close();

			String newEmployees = readEmployees();

			output = "{\"status\":\"success\", \"data\": \"" + newEmployees + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the employee.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	
	
	// Update
	public String updateEmployee(String ID, String ename, String email, String phone, String gender) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE employees SET ename=?, email=?, phone=?, gender=? WHERE eid=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, ename);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, phone);
			preparedStmt.setString(4, gender);
			preparedStmt.setInt(5, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();

			con.close();

			String newEmployees = readEmployees();

			output = "{\"status\":\"success\", \"data\": \"" + newEmployees + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the employee.\"}";
			
			System.err.println(e.getMessage());
		}

		return output;
	}

	
	
	// Delete
	public String deleteEmployee(String eid) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from employees where eid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(eid));

			// execute the statement
			preparedStmt.execute();

			con.close();

			String newEmployees = readEmployees();

			output = "{\"status\":\"success\", \"data\": \"" + newEmployees + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the employee.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}
}