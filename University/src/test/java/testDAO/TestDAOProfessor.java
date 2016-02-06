package testDAO;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.Test;

import dao.api.LectureDAO;
import dao.api.ProfessorDAO;
import dao.impl.HibernateSessionManager;
import dao.impl.LectureDAOImpl;
import dao.impl.ProfessorDAOImpl;
import entityAPI.Lecture.Lecture;
import entityAPI.professor.Professor;
import entityAPI.professor.ProfessorActionService;
import entityImpl.lecture.LectureImpl;
import entityImpl.professor.ProfessorActionServiceImpl;

public class TestDAOProfessor {

	@Test
	public void testDAOProfessor() {
		
		ProfessorDAO dao = new ProfessorDAOImpl();

		LectureDAO lecDAO = new LectureDAOImpl();

		ProfessorActionService profService = new ProfessorActionServiceImpl();

		Lecture lec = new LectureImpl("Phys");

		Professor professor = profService.createProfessor("Sharp");

		Lecture lec1 = new LectureImpl("Phys1");

		Professor professor1 = profService.createProfessor("Keen");

		lecDAO.persist(lec);

		dao.persist(professor);
		//
		lecDAO.persist(lec1);

		dao.persist(professor1);

	}
	// Could not determine type for: java.util.Collection,
	// at table: lecture, for columns:
	// [org.hibernate.mapping.Column(attendingStudents)]
}
