package dao.api;

import entityAPI.professor.Professor;

public interface ProfessorDAO {

	void persist(Professor professor);

	void update(Professor professor);

	Professor findById(int id);

	void kill(Professor professor);

}
