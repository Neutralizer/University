package dao.api;

import java.util.Collection;

import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;

public interface LectureDAO {

	void persist(Lecture lecture);
	
	void update(Lecture lecture);

	Lecture findById(int id);
	
	Collection<Student> getStudents(Lecture lecture);
	
	void removeLecture(Lecture lecture);

}
