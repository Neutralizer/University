package testLecture;

import static org.junit.Assert.*;

import org.junit.Test;

import entityAPI.Lecture.Lecture;
import entityAPI.Lecture.LectureActionService;
import entityAPI.professor.Professor;
import entityImpl.lecture.LectureActionServiceImpl;
import entityImpl.lecture.LectureImpl;
import entityImpl.professor.ProfessorImpl;

public class TestLecture {

	@Test
	public void testLecture() {

	LectureActionService lecService = getLectureActionService();
	
	Lecture lec = lecService.createLecture("Math");
	
	assertEquals("Math",lec.getName());
	
	Professor prof = new ProfessorImpl("dummyProf", lec);
	
	Lecture newLec = lecService.setLectureProfessor(lec, prof);
	
	//must not be eq due to immutability
	assertNotEquals(newLec, prof.getLecture());
	
	//name of lecture must be the same
	assertEquals(newLec.getName(), prof.getLecture().getName());
	
	//new lecture has assigned professor
	assertEquals(prof, newLec.getLeadingProfessor());
	
	
	}

	private LectureActionService getLectureActionService() {
		return new LectureActionServiceImpl();
	}

}
