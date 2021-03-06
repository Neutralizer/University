package daoJDBC.impl;

import java.sql.*;

public class GetFromJDBC {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/university";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "Cecoceco1";

	public static void main(String[] args) {
		
		Connection conn = null;
		   Statement stmt = null;
		   
		   try{
			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			      System.out.println("Connecting to database...");
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);

			      //STEP 4: Execute a query
			      System.out.println("Creating statement...");
			      stmt = conn.createStatement();
			      
			      stmt.execute("use dummyDB");
			      
			      
			      String sql;
			      sql = "SELECT jdbc_student_id, name FROM `dummydb`.`studentjdbc`";
			      ResultSet rs = stmt.executeQuery(sql);
			      //STEP 5: Extract data from result set
			      while(rs.next()){
			         //Retrieve by column name
			         int id  = rs.getInt("jdbc_student_id");
			         String name = rs.getString("name");

			         //Display values
			         System.out.print("ID: " + id);
			         System.out.println(", Name: " + name);
			      }
			    //STEP 6: Clean-up environment
			      rs.close();
			      stmt.close();
			      conn.close();
			   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }finally{
			     
				   try{
				         if(stmt!=null)
				            stmt.close();
				      }catch(SQLException se2){
				    	  se2.printStackTrace();
				      }
				      try{
				         if(conn!=null)
				            conn.close();
				      }catch(SQLException se){
				         se.printStackTrace();
				      }
				   }
				  

	}

}
