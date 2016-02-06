package entityImpl.professor;

import dao.api.ProfessorDAO;
import dao.impl.ProfessorDAOImpl;
import daoJDBC.impl.ProfessorDAOImplJDBC;
import entityAPI.Lecture.Lecture;
import entityAPI.professor.Professor;
import entityAPI.professor.ProfessorActionService;

public class ProfessorActionServiceImpl implements ProfessorActionService {

	ProfessorDAOImplJDBC dao = new ProfessorDAOImplJDBC();
//	ProfessorDAOImpl dao = new ProfessorDAOImpl();
	
	public Professor createProfessor(String name) {
		Professor prof = new ProfessorImpl(name);
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

	

}
