package entityAPI.student;

import java.util.Collection;

import entityAPI.Lecture.Lecture;
import entityImpl.lecture.StudentNotFoundException;
import entityImpl.student.LectureNotFoundException;

public interface StudentActionService {

	Student createStudent(String name);

	void updateStudent(Student student);

	Student findStudentById(int id);

	Collection<Lecture> findAllStudentLectures(Student student);

	Student assignLecture(Student student, Lecture lecture);

	Student unassignLecture(Student student, Lecture lecture)
			throws LectureNotFoundException;

	boolean isLectureAttended(Student student, Lecture lecture);

	void killStudent(Student student) throws StudentNotFoundException;

}
