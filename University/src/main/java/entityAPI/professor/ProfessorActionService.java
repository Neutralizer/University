package entityAPI.professor;

import entityAPI.Lecture.Lecture;
import entityImpl.professor.ProfessorNotFoundException;

public interface ProfessorActionService {

	Professor createProfessor(String name);

	void updateProfessor(Professor professor);

	Professor findProfessorById(int id);

	void killProfessor(Professor professor) throws ProfessorNotFoundException;

}
