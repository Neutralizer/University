package entityAPI.professor;

import entityAPI.Lecture.Lecture;
import entityImpl.professor.ProfessorNotFoundException;

public interface ProfessorActionService {

	Professor createProfessor(String name, Lecture lecture);

	void updateProfessor(Professor professor);
	
	Professor findProfessorById(int id);

	Professor unassignLecture(Professor professor);

	void killProfessor(Professor professor) throws ProfessorNotFoundException;

}
