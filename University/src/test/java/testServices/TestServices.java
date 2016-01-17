package testServices;

import static org.junit.Assert.*;

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

		Lecture lec1 = lecService.createLecture("Phys1");

		Lecture lec2 = lecService.createLecture("Phys2");

		Professor professor1 = profService.createProfessor("Duduk", lec1);

		Professor professor2 = profService.createProfessor("Shebek", lec2);

		Student stud1 = studService.createStudent("Habib");

		Student stud2 = studService.createStudent("Fu Xi");

		assertEquals("Phys1", lecService.findLectureById(1).getName());
		assertEquals("Phys2", lecService.findLectureById(2).getName());

		assertEquals("Duduk", profService.findProfessorById(1).getName());
		assertEquals("Shebek", profService.findProfessorById(2).getName());

		assertEquals("Habib", studService.findStudentById(1).getName());
		assertEquals("Fu Xi", studService.findStudentById(2).getName());

		lecService.setLectureProfessor(lecService.findLectureById(2),
				profService.findProfessorById(2));

		assertEquals("Shebek",
				lecService.findLectureById(2).getLeadingProfessor().getName());

		lecService.removeLectureProfessor(lecService.findLectureById(2));

		assertNull(lecService.findLectureById(2).getLeadingProfessor());

		// cascade for stud-lecture is set
		studService.assignLecture(studService.findStudentById(1),
				lecService.findLectureById(1));
		studService.assignLecture(studService.findStudentById(2),
				lecService.findLectureById(1));

		assertTrue(lecService.isStudentAttending(lecService.findLectureById(1),
				studService.findStudentById(2)));

		assertTrue(studService.isLectureAttended(studService.findStudentById(1),
				lecService.findLectureById(1)));

		// how many students are attending the lecture
		assertEquals(2, lecService.findLectureById(1).getAttendingStudents().size());
		
		try {
			studService.unassignLecture(studService.findStudentById(2), lecService.findLectureById(1));
			assertFalse(lecService.isStudentAttending(lecService.findLectureById(1), studService.findStudentById(2)));
			assertFalse(studService.isLectureAttended(studService.findStudentById(2), lecService.findLectureById(1)));
		} catch (LectureNotFoundException e) {
			assertFalse(true);
		}
		
		//clear lecture from 
		try {
			studService.unassignLecture(studService.findStudentById(1), lecService.findLectureById(1));
			assertFalse(lecService.isStudentAttending(lecService.findLectureById(1), studService.findStudentById(2)));
			assertFalse(studService.isLectureAttended(studService.findStudentById(2), lecService.findLectureById(1)));
		} catch (LectureNotFoundException e) {
			assertFalse(true);
		}
		
		Professor prof1 = profService.unassignLecture(profService.findProfessorById(1));
		
		//unassign stud
		//unassign lec
		
		profService.killProfessor(profService.findProfessorById(1));
		
		assertNull(profService.findProfessorById(1));
		
		// remove all professors and students, associated with the lecture
		
		lecService.removeLecture(lecService.findLectureById(1));
		
		assertNull(lecService.findLectureById(1));
		
		
		studService.killStudent(studService.findStudentById(1));

		assertNull(studService.findStudentById(1));
		
		
		

	}

}
