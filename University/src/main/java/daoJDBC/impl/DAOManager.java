package daoJDBC.impl;

import java.sql.*;

public class DAOManager {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/university";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "Cecoceco1";

	private static Connection conn = null;
	private static Statement stmt = null;

	public void open() {

		// STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
		// STEP 3: Open a connection
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		// STEP 4: Execute a query
		System.out.println("Creating statement...");
		stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Statement getStatement(){
		return stmt;
	}
	
	public Connection getConnection(){
		return conn;
	}
	
	public void close() {

				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
				
				System.out.println("Successfully closed");
		 
	}

}
