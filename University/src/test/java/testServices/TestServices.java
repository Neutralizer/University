package testServices;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

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
import entityImpl.professor.ProfessorNotFoundException;
import entityImpl.student.LectureNotFoundException;
import entityImpl.student.StudentActionServiceImpl;

public class TestServices {

	ProfessorActionService profService = new ProfessorActionServiceImpl();

	LectureActionService lecService = new LectureActionServiceImpl();

	StudentActionService studService = new StudentActionServiceImpl();

	@Test
	public void testStudentService() throws StudentNotFoundException, LectureNotFoundException, ProfessorNotFoundException {

		lecService.createLecture("Phys1");

		lecService.createLecture("Phys2");

		profService.createProfessor("Sharp");

		profService.createProfessor("Keen");

		studService.createStudent("Habib");

		studService.createStudent("Fu Xi");

		assertEquals("Phys1", lecService.findLectureById(1).getName());
		assertEquals("Phys2", lecService.findLectureById(2).getName());

		assertEquals("Sharp", profService.findProfessorById(1).getName());
		assertEquals("Keen", profService.findProfessorById(2).getName());

		assertEquals("Habib", studService.findStudentById(1).getName());
		assertEquals("Fu Xi", studService.findStudentById(2).getName());
		
		lecService.setLectureProfessor(lecService.findLectureById(2),
				profService.findProfessorById(2));

		assertEquals("Keen",
				lecService.findLectureById(2).getLeadingProfessor().getName());
		
		lecService.removeLectureProfessor(lecService.findLectureById(2));
		
		assertNull(lecService.findLectureById(2).getLeadingProfessor());
		
		
		
		
		
		
		
		

		// cascade for stud-lecture is set
		// //student 1 - lecture 1
		// //student 2 - lecture 1
		lecService.assignStudent(lecService.findLectureById(1),
				studService.findStudentById(1));
		
		lecService.assignStudent(lecService.findLectureById(1),
				studService.findStudentById(2));
		
		
		
		// // 2 asserts
		assertTrue(lecService.isStudentAttending(lecService.findLectureById(1),
				studService.findStudentById(1)));

		assertTrue(lecService.isStudentAttending(lecService.findLectureById(1),
				studService.findStudentById(2)));

		// how many students are attending the lecture 1
		assertEquals(2, lecService.findLectureById(1).getAttendingStudents().size());
		
		// how many students are attending the lecture from action service
		
		assertEquals(lecService.findLectureById(1).getAttendingStudents().size(), 
				lecService.findAllLecturesStudents(lecService.findLectureById(1)).size());
		
		lecService.setLectureProfessor(lecService.findLectureById(2),
				profService.findProfessorById(2));
		
		
		// // remove lecture from professor
		//unassign stud
		//unassign lec
		
		profService.killProfessor(profService.findProfessorById(1));
		
		assertNull(profService.findProfessorById(1));
		
		// remove all professors and students, associated with the lecture
		
		// if there is a lecture reference in the intermediary table 
		// the lecture will not be removed
		lecService.unassignStudent(lecService.findLectureById(1), 
				studService.findStudentById(1));
		
		lecService.unassignStudent(lecService.findLectureById(1), 
				studService.findStudentById(2));
		
		lecService.removeLecture(lecService.findLectureById(1));
		
		assertNull(lecService.findLectureById(1));
		
		
		studService.killStudent(studService.findStudentById(1));

		assertNull(studService.findStudentById(1));
		
		
		

	}

}
