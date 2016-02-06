package daoJDBC.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class FillDBBatch {
	
	// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost/university";

		// Database credentials
		static final String USER = "root";
		static final String PASS = "Cecoceco1";

		public static void main(String[] args) {

			Connection conn = null;
			Statement stmt = null;

			try {
				// STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");

				// STEP 3: Open a connection
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				
				// STEP 4: Execute a query
				System.out.println("Creating statement...");
				stmt = conn.createStatement();
				
				conn.setAutoCommit(false);

				stmt.execute("use dummyDB");

				String sql;
				sql = "CREATE TABLE `studentjdbc` "
						+ "( `jdbc_student_id` INT NOT NULL AUTO_INCREMENT , "
						+ " `name` VARCHAR(45)  ," 
						+ "PRIMARY KEY (`jdbc_student_id`) );";
				stmt.addBatch(sql);
				
				sql = "CREATE TABLE `lecturejdbc` "
						+ "( `jdbc_lecture_id` INT NOT NULL AUTO_INCREMENT , "
						+ " `name` VARCHAR(45)  ," 
						+ " `leading_professor_id` INT  ,"
						+ "PRIMARY KEY (`jdbc_lecture_id`) );";
				stmt.addBatch(sql);
				
				sql = "CREATE TABLE `professorjdbc` "
						+ "( `jdbc_professor_id` INT NOT NULL AUTO_INCREMENT , "
						+ " `name` VARCHAR(45)  ," 
						+ "PRIMARY KEY (`jdbc_professor_id`) );";
				stmt.addBatch(sql);
				
				sql = "CREATE TABLE `student_lecture_jdbc` "
						+ "( `jdbc_student_id` INT NOT NULL , "
						+ " `jdbc_lecture_id` INT NOT NULL );";
				stmt.addBatch(sql);
				
				sql = "ALTER TABLE student_lecture_jdbc "
						+ "ADD FOREIGN KEY (jdbc_student_id) "
						+ "REFERENCES studentjdbc (jdbc_student_id);";
				stmt.addBatch(sql);
				
				sql = "ALTER TABLE student_lecture_jdbc "
						+ "ADD FOREIGN KEY (jdbc_lecture_id) "
						+ "REFERENCES lecturejdbc (jdbc_lecture_id);";
				stmt.addBatch(sql);
				
				sql = "ALTER TABLE lecturejdbc " 
						+ "ADD FOREIGN KEY (leading_professor_id) "
						+ "REFERENCES professorjdbc (jdbc_professor_id);";
				stmt.addBatch(sql);
				
				stmt.executeBatch();

				conn.commit();
				stmt.close();
				conn.close();
			} catch (SQLException se) {
				// Handle errors for JDBC
				se.printStackTrace();
			} catch (Exception e) {
				// Handle errors for Class.forName
				e.printStackTrace();
			} finally {

				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			
			System.out.println("Complete");
		}

}
