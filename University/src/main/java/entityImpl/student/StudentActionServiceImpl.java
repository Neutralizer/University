package entityImpl.student;

import java.util.ArrayList;
import java.util.Collection;

import dao.api.StudentDAO;
import dao.impl.StudentDAOImpl;
import daoJDBC.impl.StudentDAOImplJDBC;
import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;
import entityAPI.student.StudentActionService;
import entityImpl.lecture.StudentNotFoundException;

public class StudentActionServiceImpl implements StudentActionService {

	StudentDAOImplJDBC dao = new StudentDAOImplJDBC();
//	StudentDAOImpl dao = new StudentDAOImpl();
	
	public Student createStudent(String name) {
		Student stud = new StudentImpl(name);
		dao.persist(stud);
		return stud;
	}

	public void updateStudent(Student student) {
		dao.update(student);
	}

	public Student findStudentById(int id) {
		return dao.findById(id);
	}


	public void killStudent(Student student) throws StudentNotFoundException {
		Student stud = null;
		stud = dao.findById(student.getId());
		if (stud.equals(null)) {
			throw new StudentNotFoundException();
		} else {
			dao.kill(stud);
		}

	}

}
