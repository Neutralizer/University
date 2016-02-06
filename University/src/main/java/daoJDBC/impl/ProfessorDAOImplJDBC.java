package daoJDBC.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.api.ProfessorDAO;
import entityAPI.Lecture.Lecture;
import entityAPI.professor.Professor;
import entityImpl.lecture.LectureImpl;
import entityImpl.professor.ProfessorImpl;

public class ProfessorDAOImplJDBC implements ProfessorDAO {

	DAOManager mng = new DAOManager();

	public void persist(Professor professor) {
		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			PreparedStatement preparedStatement = mng.getConnection().prepareStatement(
					"INSERT INTO dummydb.professorjdbc " + "(name) VALUES (?)");
			preparedStatement.setString(1, professor.getName());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}

	}

	public void update(Professor professor) {
		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			PreparedStatement preparedStatement = mng.getConnection()
					.prepareStatement("UPDATE dummyDB.professorjdbc " + "SET name = ? "
							+ "WHERE jdbc_professor_id = ?");
			preparedStatement.setString(1, professor.getName());
			preparedStatement.setInt(2, professor.getId());
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}
	}

	public Professor findById(int id) {
		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			String selectSQL = "SELECT * FROM dummyDB.professorjdbc "
					+ "WHERE jdbc_professor_id = ?";
			PreparedStatement preparedStatement = mng.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				int professorId = Integer.parseInt(rs.getString("jdbc_professor_id"));
				String name = rs.getString("name");
				Professor prof = new ProfessorImpl(professorId, name);

				return prof;
			}

			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}
		return null;
	}

	public void kill(Professor professor) {
		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			PreparedStatement preparedStatement = mng.getConnection().prepareStatement(
					"DELETE FROM dummyDB.professorjdbc WHERE jdbc_professor_id = ?");
			preparedStatement.setInt(1, professor.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}

	}

}
