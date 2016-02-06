package entityAPI.Lecture;

import java.util.Collection;

import entityAPI.professor.Professor;
import entityAPI.student.Student;
import entityImpl.lecture.StudentNotFoundException;
import entityImpl.student.LectureNotFoundException;

public interface LectureActionService {

	Lecture createLecture(String name);

	void updateLecture(Lecture lecture);

	Lecture findLectureById(int id);

	Lecture assignStudent(Lecture lecture, Student student);

	Lecture unassignStudent(Lecture lecture, Student student)
			throws StudentNotFoundException;

	boolean isStudentAttending(Lecture lecture, Student student);

	void removeLecture(Lecture lecture) throws LectureNotFoundException;
	
	Collection<Student> findAllLecturesStudents(Lecture lecture);

	Lecture setLectureProfessor(Lecture lecture, Professor professor);

	Lecture removeLectureProfessor(Lecture lecture);

}
