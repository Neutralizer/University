package daoJDBC.impl;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import dao.api.LectureDAO;
import entityAPI.Lecture.Lecture;
import entityAPI.professor.Professor;
import entityAPI.student.Student;
import entityImpl.lecture.LectureImpl;
import entityImpl.professor.ProfessorImpl;
import entityImpl.student.StudentImpl;

public class LectureDAOImplJDBC implements LectureDAO {

	DAOManager mng = new DAOManager();

	public void persist(Lecture lecture) {

		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			PreparedStatement preparedStatement = mng.getConnection().prepareStatement(
					"INSERT INTO dummyDB.lecturejdbc (name) " + "VALUES (?)");
			preparedStatement.setString(1, lecture.getName());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}

	}

	public void update(Lecture lecture) {

		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			String selectSQL = "SELECT jdbc_student_id FROM `dummydb`.`student_lecture_jdbc`"
					+ " WHERE jdbc_lecture_id = ?";
			PreparedStatement preparedStatement = mng.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, lecture.getId());
			ResultSet rs = preparedStatement.executeQuery();

			ArrayList<Integer> listDBNumbers = new ArrayList<Integer>();
			while (rs.next()) {
				listDBNumbers.add(rs.getInt("jdbc_student_id"));
			}

			ArrayList<Integer> listLocalNumbers = new ArrayList<Integer>();

			Iterator<Student> iter = lecture.getAttendingStudents().iterator();
			while (iter.hasNext()) {
				listLocalNumbers.add(iter.next().getId());
			}

			if (!listDBNumbers.equals(listLocalNumbers)) {
				int counter = 0;
				int number = 0;

				if (listDBNumbers.size() > listLocalNumbers.size()) {

					// removing a student

					while (counter < listLocalNumbers.size()) {
						if (!listLocalNumbers.contains(listDBNumbers.get(counter))) {
							number = listDBNumbers.get(counter);
						}

						counter++;
					}
					if (number == 0) {
						number = listDBNumbers.get(listDBNumbers.size() - 1);
					}
					System.out.println(number + " number");

					mng.open();
					Statement stat = mng.getStatement();
					stat.execute("use dummyDB");
					PreparedStatement prepStatement = mng.getConnection()
							.prepareStatement("DELETE FROM dummydb.student_lecture_jdbc "
									+ "WHERE jdbc_student_id = ? AND "
									+ "jdbc_lecture_id = ?");
					prepStatement.setInt(1, number);
					prepStatement.setInt(2, lecture.getId());
					prepStatement.executeUpdate();
					prepStatement.close();

				} else {
					// adding a student

					while (counter < listDBNumbers.size()) {
						if (!listDBNumbers.contains(listLocalNumbers.get(counter))) {
							number = listLocalNumbers.get(counter);
						}

						counter++;
					}
					if (number == 0) {
						number = listLocalNumbers.get(listLocalNumbers.size() - 1);
					}
					System.out.println(number + " number");

					mng.open();
					Statement stat = mng.getStatement();
					stat.execute("use dummyDB");
					PreparedStatement prepStatement = mng.getConnection()
							.prepareStatement("INSERT INTO dummydb.student_lecture_jdbc "
									+ "VALUES (?, ?)");
					prepStatement.setInt(1, number);
					prepStatement.setInt(2, lecture.getId());
					prepStatement.executeUpdate();
					prepStatement.close();
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}

		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			PreparedStatement preparedStatement = mng.getConnection()
					.prepareStatement("UPDATE dummyDB.lecturejdbc "
							+ "SET name = ?, leading_professor_id = ? "
							+ "WHERE jdbc_lecture_id = ?");
			preparedStatement.setString(1, lecture.getName());
			if(lecture.getLeadingProfessor() == null){
				preparedStatement.setNull(2, java.sql.Types.INTEGER);
			} else {
				preparedStatement.setInt(2, lecture.getLeadingProfessor().getId());
			}
			preparedStatement.setInt(3, lecture.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			// TODO check if leading prof is null error
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}
	}

	public Lecture findById(int id) {

		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			String selectSQL = "SELECT * FROM dummyDB.lecturejdbc WHERE jdbc_lecture_id = ?";
			PreparedStatement preparedStatement = mng.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				ProfessorDAOImplJDBC profDAO = new ProfessorDAOImplJDBC();
				LectureDAOImplJDBC lecDAO = new LectureDAOImplJDBC();

				int lectureId = Integer.parseInt(rs.getString("jdbc_lecture_id"));
				String name = rs.getString("name");
				int professorNumber;
				if (rs.getString("leading_professor_id") == null) {
					Lecture dummyLec = new LectureImpl(lectureId, name);
					Collection<Student> attendingStudents = lecDAO.getStudents(dummyLec);
					Lecture lecture = new LectureImpl(lectureId, name,
							attendingStudents);
					return lecture;

				} else {
					professorNumber = Integer
							.parseInt(rs.getString("leading_professor_id"));
					Professor prof = profDAO.findById(professorNumber);
					Lecture dummyLec = new LectureImpl(lectureId, name, prof);
					Collection<Student> attendingStudents = lecDAO.getStudents(dummyLec);
					Lecture lecture = new LectureImpl(lectureId, name, prof,
							attendingStudents);

					return lecture;
				}

				// requires only lecture id set

			}

			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}
		return null;
	}

	public Collection<Student> getStudents(Lecture lecture) {
		// select all from students, where

		StudentDAOImplJDBC studentDAOJDBS = new StudentDAOImplJDBC();

		ArrayList<Integer> listNumbers = new ArrayList<Integer>();
		Collection<Student> students = new ArrayList<Student>();

		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			String selectSQL = "SELECT jdbc_student_id FROM `dummydb`.`student_lecture_jdbc`"
					+ " WHERE jdbc_lecture_id = ?";
			// select all from students, where
			PreparedStatement preparedStatement = mng.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, lecture.getId());
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				listNumbers.add(rs.getInt("jdbc_student_id"));
			}

			preparedStatement.close();
			// ArrayList
			// Collection<Student> students = new ArrayList<Student>();

			// iterator
			// Iterator<Integer> iter = listNumbers.iterator();
			// while (iter.hasNext()) {
			// students.add(studentDAOJDBS.findById(iter.next()));
			// }
			//
			// return students;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}
		
		for(int i= 0; i < listNumbers.size(); i++){
		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			String selectSQL = "SELECT * FROM `dummydb`.`studentjdbc`"
					+ " WHERE jdbc_student_id IN (?)";
			// select all from students, where
			PreparedStatement preparedStatement = mng.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, listNumbers.get(i));
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Student stud = new StudentImpl(rs.getInt("jdbc_student_id"),
						rs.getString("name"));
				students.add(stud);
			}
			preparedStatement.close();
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}
		}

		return students;

		// try {
		// mng.open();
		// Statement statement = mng.getStatement();
		// statement.execute("use dummyDB");
		// String selectSQL = "SELECT jdbc_student_id FROM
		// `dummydb`.`student_lecture_jdbc`"
		// + " WHERE jdbc_lecture_id = ?";
		// //select all from students, where
		// PreparedStatement preparedStatement = mng.getConnection()
		// .prepareStatement(selectSQL);
		// preparedStatement.setInt(1, lecture.getId());
		// ResultSet rs = preparedStatement.executeQuery();
		//
		//
		//
		// while (rs.next()) {
		// listNumbers.add(rs.getInt("jdbc_student_id"));
		// }
		//
		// preparedStatement.close();
		// // ArrayList
		// Collection<Student> students = new ArrayList<Student>();
		//
		// // iterator
		// Iterator<Integer> iter = listNumbers.iterator();
		// while (iter.hasNext()) {
		// students.add(studentDAOJDBS.findById(iter.next()));
		// }
		//
		// return students;
		//
		// } catch (SQLException e) {
		// e.printStackTrace();
		// } finally {
		// mng.close();
		// }
		// return null;

	}

	public void removeLecture(Lecture lecture) {
		try {
			mng.open();
			Statement statement = mng.getStatement();
			statement.execute("use dummyDB");
			PreparedStatement preparedStatement = mng.getConnection().prepareStatement(
					"DELETE FROM dummyDB.lecturejdbc WHERE jdbc_lecture_id = ?");
			preparedStatement.setInt(1, lecture.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mng.close();
		}

	}

}
