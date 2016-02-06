package entityAPI.student;

import java.util.Collection;

import entityAPI.Lecture.Lecture;
import entityImpl.lecture.StudentNotFoundException;
import entityImpl.student.LectureNotFoundException;

public interface StudentActionService {

	Student createStudent(String name);

	void updateStudent(Student student);

	Student findStudentById(int id);

	void killStudent(Student student) throws StudentNotFoundException;

}
