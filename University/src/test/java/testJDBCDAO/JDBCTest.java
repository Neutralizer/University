package testJDBCDAO;

import static org.junit.Assert.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import daoJDBC.impl.DAOManager;
import daoJDBC.impl.StudentDAOImplJDBC;
import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;
import entityImpl.student.StudentImpl;

public class JDBCTest {

	DAOManager mng = new DAOManager();

	@Test
	public void test() {
		mng.open();
		Statement statement = mng.getStatement();
		try {
			statement.execute("use dummyDB");

			// studentDAO class
			String sql;
			sql = "INSERT INTO studentjdbc (name) " + " VALUES ('Babanka') ;";
			// TODO

			statement.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			// Probably excess catch
			e.printStackTrace();
		} finally {
			mng.close();
		}
	}

	// works
	@Test
	public void testStudentJDBCInsert() {

		StudentDAOImplJDBC dao = new StudentDAOImplJDBC();

		Student student = new StudentImpl("Aatrox");

		dao.persist(student);

	}

	// works &
	// add lectures
	@Test
	public void testStudentJDBCfindById() {

		StudentDAOImplJDBC dao = new StudentDAOImplJDBC();

		Student student = dao.findById(1);

		assertEquals("Hello", student.getName());

	}

	// works
	@Test
	public void testStudentJDBCDelete() {
		StudentDAOImplJDBC dao = new StudentDAOImplJDBC();

		Student student = dao.findById(5);

		dao.kill(student);

	}

	// works
	@Test
	public void testStudentJDBCgetLectures() {

		Collection<Lecture> lectures = new ArrayList<Lecture>();

		Iterator<Lecture> iter = lectures.iterator();

		// Lecture lec = iter.next();

		// assertEquals("math", lec.getName());

		Lecture[] dummy = new Lecture[5];

		int counter = 0;

		while (iter.hasNext()) {
			dummy[counter] = iter.next();
			counter++;
		}
		assertEquals("math", dummy[0].getName());
		assertEquals("phys", dummy[1].getName());
		assertEquals("bio", dummy[2].getName());

	}

}
