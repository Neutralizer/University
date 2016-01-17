package testStudent;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;
import entityAPI.student.StudentActionService;
import entityImpl.lecture.LectureImpl;
import entityImpl.student.LectureNotFoundException;
import entityImpl.student.StudentActionServiceImpl;
import entityImpl.student.StudentImpl;

public class TestStudent {

	@Test
	public void testCreateStudent() {

		StudentActionService studService = getStudentActionServiceImpl();

		Student stud = studService.createStudent("Mochacho");

		assertEquals("Mochacho", stud.getName());

	}

	@Test
	public void testStudentLectures() throws LectureNotFoundException {

		StudentActionService studService = getStudentActionServiceImpl();

		Student stud = new StudentImpl("Mochacho");

		Lecture lec = new LectureImpl("dummyLec");

		Student stud1 = studService.assignLecture(stud, lec);

		assertEquals(lec, stud.getAttendedLectures().iterator().next());

		assertNotEquals(stud, stud1);
		
		boolean flag = studService.isLectureAttended(stud1, lec);
		
		assertTrue(flag);

		Student stud2 = studService.unassignLecture(stud, lec);

		assertEquals(0, stud2.getAttendedLectures().size());

	}

	@Test(expected = LectureNotFoundException.class)
	public void testExceptionIsThrown() throws LectureNotFoundException {
		
		StudentActionService studService = getStudentActionServiceImpl();
		
		Student stud = new StudentImpl("Mochacho");

		Lecture lec = new LectureImpl("dummyLec");
		
		Student stud1 = studService.unassignLecture(stud, lec);
	}

	private StudentActionService getStudentActionServiceImpl() {
		return new StudentActionServiceImpl();
	}

}
