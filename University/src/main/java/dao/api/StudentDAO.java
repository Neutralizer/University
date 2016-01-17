package dao.api;

import java.util.Collection;

import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;

public interface StudentDAO {
	
	void persist(Student student);
	
	void update(Student student);
	
	Student findById(int id);
	
	Collection<Lecture> getLectures(Student student);
	
	void kill(Student student);
}
