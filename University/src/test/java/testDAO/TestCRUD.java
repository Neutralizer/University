package testDAO;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import dao.api.LectureDAO;
import dao.api.ProfessorDAO;
import dao.api.StudentDAO;
import dao.impl.LectureDAOImpl;
import dao.impl.ProfessorDAOImpl;
import dao.impl.StudentDAOImpl;
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

public class TestCRUD {
	//no longer needs DAO access
	
	ProfessorDAO profDAO = new ProfessorDAOImpl();

	LectureDAO lecDAO = new LectureDAOImpl();

	StudentDAO studDAO = new StudentDAOImpl();

	ProfessorActionService profService = new ProfessorActionServiceImpl();

	LectureActionService lecService = new LectureActionServiceImpl();

	StudentActionService studService = new StudentActionServiceImpl();


	@Test
	public void testDAO(){

		Lecture lec1 = lecService.createLecture("Phys1");

		Lecture lec2 = new LectureImpl("Phys2");

		Professor professor1 = profService.createProfessor("Sharp");

		Professor professor2 = profService.createProfessor("Keen");

		Student stud1 = studService.createStudent("Habib");

		Student stud2 = studService.createStudent("Fu Xi");

		lecDAO.persist(lec1);

		lecDAO.persist(lec2);

		profDAO.persist(professor1);

		profDAO.persist(professor2);

		studDAO.persist(stud1);

		studDAO.persist(stud2);
		
		
		
		assertEquals("Phys1", lecDAO.findById(1).getName());
		assertEquals("Phys2", lecDAO.findById(2).getName());
		
		assertEquals("Sharp", profDAO.findById(1).getName());
		assertEquals("Keen", profDAO.findById(2).getName());
		
		assertEquals("Habib", studDAO.findById(1).getName());
		assertEquals("Fu Xi", studDAO.findById(2).getName());
		
		
		lecService.setLectureProfessor(lecDAO.findById(2), profDAO.findById(2));
		
		assertEquals("Keen", lecDAO.findById(2).getLeadingProfessor().getName());
		
		lecService.removeLectureProfessor(lecDAO.findById(2));
		
		assertNull(lecDAO.findById(2).getLeadingProfessor());
		
		// cascade for stud-lecture is set
		assertTrue(lecService.isStudentAttending(lecDAO.findById(1), studDAO.findById(2)));
		
		
		//how many students are attending the lecture
		assertEquals(2, lecDAO.findById(1).getAttendingStudents().size());
		
		// unassign lecture 
		// can not due to child cascade restrictions
//		try {
//			lecService.unassignStudent(lecDAO.findById(1), studDAO.findById(1));
//			assertFalse(lecService.isStudentAttending(lecDAO.findById(1), studDAO.findById(1)));
//			assertFalse(studService.isLectureAttended(studDAO.findById(1), lecDAO.findById(1)));
//
//		} catch (StudentNotFoundException e) {
//			assertFalse(true);
//		}
		
		
		
		//unassign stud
		//unassign lec
//
		profDAO.kill(profDAO.findById(1));
//		
		assertNull(profDAO.findById(1));
		
		// remove all professors and students, associated with the lecture
		
		lecDAO.removeLecture(lecDAO.findById(1));
		
		assertNull(lecDAO.findById(1));
		
		
		studDAO.kill(studDAO.findById(1));
//		
		assertNull(studDAO.findById(1));
		
		
		//student.getLectures
		

	}
}
