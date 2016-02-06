package daoJDBC.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import dao.api.StudentDAO;
import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;
import entityImpl.student.StudentImpl;

public class StudentDAOImplJDBC implements StudentDAO {

	DAOManager mng = new DAOManager();

	public void persist(Student student) {

		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			PreparedStatement preparedStatement = mng.getConnection().prepareStatement(
					"INSERT INTO dummyDB.studentjdbc (name) VALUES (?)");
			preparedStatement.setString(1, student.getName());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}

	}

	public void update(Student student) {
		
		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			PreparedStatement preparedStatement = mng.getConnection()
					.prepareStatement("UPDATE dummyDB.studentjdbc " + "SET name = ?"
							+ "WHERE jdbc_student_id = ?");
			preparedStatement.setString(1, student.getName());
			preparedStatement.setInt(2, student.getId());

			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}

	}

	public Student findById(int id) {

		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			String selectSQL = "SELECT * FROM dummyDB.studentjdbc WHERE jdbc_student_id = ?";
			PreparedStatement preparedStatement = mng.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				int studentId = Integer.parseInt(rs.getString("jdbc_student_id"));
				String name = rs.getString("name");


				Student student = new StudentImpl(studentId, name);
				return student;
			}

			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}
		return null;

	}


	public void kill(Student student) {
		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			PreparedStatement preparedStatement = mng.getConnection().prepareStatement(
					"DELETE FROM dummyDB.studentjdbc WHERE jdbc_student_id = ?");
			preparedStatement.setInt(1, student.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}

	}

}
