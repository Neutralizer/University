package testProfessor;

import org.junit.Test;

import static org.junit.Assert.*;

import entityAPI.Lecture.Lecture;
import entityAPI.professor.Professor;
import entityAPI.professor.ProfessorActionService;
import entityImpl.lecture.LectureImpl;
import entityImpl.professor.ProfessorActionServiceImpl;

public class TestProfessor {

	@Test
	public void testProfessor() {
		Lecture lec = new LectureImpl("Phys");
		
		ProfessorActionService profService = getProfessorActionService();
		
		Professor prof = profService.createProfessor("Albert San", lec);
		
		assertEquals("Albert San", prof.getName());
		
		assertEquals(lec, prof.getLecture());
		

	}

	private ProfessorActionService getProfessorActionService() {
		return new ProfessorActionServiceImpl();
	}

}
