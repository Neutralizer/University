package testJDBCDAO;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import dao.api.LectureDAO;
import dao.api.ProfessorDAO;
import dao.api.StudentDAO;
import dao.impl.LectureDAOImpl;
import dao.impl.ProfessorDAOImpl;
import dao.impl.StudentDAOImpl;
import daoJDBC.impl.LectureDAOImplJDBC;
import daoJDBC.impl.ProfessorDAOImplJDBC;
import daoJDBC.impl.StudentDAOImplJDBC;
import entityAPI.Lecture.Lecture;
import entityAPI.Lecture.LectureActionService;
import entityAPI.professor.Professor;
import entityAPI.professor.ProfessorActionService;
import entityAPI.student.Student;
import entityAPI.student.StudentActionService;
import entityImpl.lecture.LectureActionServiceImpl;
import entityImpl.lecture.LectureImpl;
import entityImpl.lecture.StudentNotFoundException;
import entityImpl.professor.ProfessorActionServiceImpl;
import entityImpl.student.LectureNotFoundException;
import entityImpl.student.StudentActionServiceImpl;

public class JDBCSeparateTest {
	//no longer needs access from DAO

	ProfessorDAOImplJDBC profDAO = new ProfessorDAOImplJDBC();

	LectureDAOImplJDBC lecDAO = new LectureDAOImplJDBC();

	StudentDAOImplJDBC studDAO = new StudentDAOImplJDBC();

	ProfessorActionService profService = new ProfessorActionServiceImpl();

	LectureActionService lecService = new LectureActionServiceImpl();

	StudentActionService studService = new StudentActionServiceImpl();

	// works x
	@Test
	public void testDAO() {
		// does not save the id
		lecService.createLecture("Phys");

		lecService.createLecture("Phys1");

		profService.createProfessor("Sharp");	

		profService.createProfessor("Keen");

		studService.createStudent("Habib");

		studService.createStudent("Fu Xi");

		

	}

	// works x
	@Test
	public void testAddProf() {

//		Lecture lec1 = lecDAO.findById(1);
		// test if lecture is not existent - ok
		Lecture lec1 = lecService.findLectureById(2);

		@SuppressWarnings("unused")
		Professor professor1 = profService.createProfessor("Novost");

//		profDAO.persist(professor1);

	}

	// works x
	@Test
	public void testAddStud() {
		Student stud = studService.createStudent("Habib1");

		Student stud1 = studService.createStudent("Fu Xi2");

		Student stud2 = studService.createStudent("Habib3");

		Student stud3 = studService.createStudent("Fu Xi4");

		Student stud4 = studService.createStudent("Habib5");

		Student stud5 = studService.createStudent("Fu Xi6");

		Student stud6 = studService.createStudent("Habib7");

		Student stud7 = studService.createStudent("Fu Xi8");

		Student stud8 = studService.createStudent("Habib9");

		Student stud9 = studService.createStudent("Fu Xi10");


	}

	// works x
	@Test
	public void testManyToManyStudToLectureMultiple() {

		Lecture lec = lecDAO.findById(2);

		Student stud4 = studDAO.findById(4);

//		Student stud5 = studDAO.findById(5);
//
//		Student stud6 = studDAO.findById(6);
//
//		Student stud7 = studDAO.findById(7);

//		studService.assignLecture(stud5, lec);
//		studService.assignLecture(stud6, lec);
//		studService.assignLecture(stud7, lec);

	}

	// works x
	@Test
	public void testAssignProfToLecture() {

		Lecture lec1 = lecDAO.findById(1);

		Professor prof = profDAO.findById(1);

		Lecture lecture = lecService.setLectureProfessor(lec1, prof);
		
		// check if null

	}

	// works x
	@Test
	public void testUnassignProfFromLecture() {
		Lecture lec1 = lecDAO.findById(1);

		Lecture lecture = lecService.removeLectureProfessor(lec1);

	}

	// works if i > 0 and if the students have been set before
	// use studService.assignLecture x
	@Test
	public void testManyToManyAreThereStudentsAttendingTheLecture() {

		Student stud = studDAO.findById(3);

		// Student stud1 = studDAO.findById(2);

		Lecture lec = lecDAO.findById(3);

		int i = lec.getAttendingStudents().size();

		System.out.println(i);

		// Lecture lecture = lecService.assignStudent(lec, stud);

		// lecService.assignStudent(lec, stud1);

		// Illegal attempt to associate a collection with two open sessions.
		// Collection : [entityImpl.lecture.LectureImpl.attendingStudents#1]

	}

	// works ,but reverse does not x
	@Test
	public void testManyToManyStudToLecture() {

		Lecture lec = lecDAO.findById(2);

		Student stud = studDAO.findById(1);


		// studDAO.update(student);

	}

	//works x
	@Test
	public void testManyToManyDraw() {

		Lecture lec = lecDAO.findById(1);

		Collection col = lec.getAttendingStudents();

		int i = col.size();

		System.out.println(i);
	}

	//works x
	@Test
	public void testLectureAttendance() {

		Student stud1 = studDAO.findById(1);

		Lecture lec1 = lecDAO.findById(1);

		System.out.println(lec1.getAttendingStudents().size());
		
		boolean flag1 = lecService.isStudentAttending(lec1, stud1);
		


		System.out.println("Flag1 " + flag1);
		

	}
	
	// works with and without refresh
	@Test
	public void testKillProf() {

		Professor professor = profDAO.findById(1);

		// remove lec from prof
		//no longer needed due to cascade probably
//		Professor prof = profService.unassignLecture(professor);

		// deleting detached entity

		profDAO.kill(professor);

	}

	// works with refresh only
	@Test
	public void testKillLecture() {

		// remove all professors, associated with the lecture

		Lecture lec1 = lecDAO.findById(1);

		lecDAO.removeLecture(lec1);

	}

	// works with refresh only
	@Test
	public void testKillStudent() {
		// because of cascade and @ManyToMany must refresh before deleting

		Student stud = studDAO.findById(2);

		studDAO.kill(stud);

	}
	
	// can not due to child cascade restrictions

}
