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
		
		Professor prof = profService.createProfessor("Albert San");
		
		assertEquals("Albert San", prof.getName());
		
		

	}

	private ProfessorActionService getProfessorActionService() {
		return new ProfessorActionServiceImpl();
	}

}
