package entityImpl.professor;

import dao.api.ProfessorDAO;
import dao.impl.ProfessorDAOImpl;
import entityAPI.Lecture.Lecture;
import entityAPI.professor.Professor;
import entityAPI.professor.ProfessorActionService;

public class ProfessorActionServiceImpl implements ProfessorActionService {

	ProfessorDAO dao = new ProfessorDAOImpl();

	public Professor createProfessor(String name, Lecture lecture) {
		Professor prof = new ProfessorImpl(name, lecture);
		dao.persist(prof);
		return prof;
	}

	public void updateProfessor(Professor professor) {
		dao.update(professor);

	}
	
	public Professor findProfessorById(int id) {
		return dao.findById(id);
	}

	public void killProfessor(Professor professor) throws ProfessorNotFoundException {
		dao.kill(professor);

	}

	public Professor unassignLecture(Professor professor) {
		Professor prof = new ProfessorImpl(professor.getId(), professor.getName());
		dao.update(prof);
		return prof;
	}

	

}
