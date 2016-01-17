package dao.api;

import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;

public interface LectureDAO {

	void persist(Lecture lecture);
	
	void update(Lecture lecture);

	Lecture findById(int id);
	
	void removeLecture(Lecture lecture);

}
