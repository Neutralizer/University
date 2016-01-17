package entityImpl.student;

import java.util.ArrayList;
import java.util.Collection;

import dao.api.StudentDAO;
import dao.impl.StudentDAOImpl;
import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;
import entityAPI.student.StudentActionService;
import entityImpl.lecture.StudentNotFoundException;

public class StudentActionServiceImpl implements StudentActionService {

	StudentDAO dao = new StudentDAOImpl();

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

	public Collection<Lecture> findAllStudentLectures(Student student) {
		return dao.getLectures(student);
	}

	public Student assignLecture(Student student, Lecture lecture) {
		// created new student to keep it immutable
		Student stud = new StudentImpl(student.getId(), student.getName(),
				student.getAttendedLectures());
		stud.getAttendedLectures().add(lecture);
		dao.update(stud);
		return stud;
	}

	public Student unassignLecture(Student student, Lecture lecture)
			throws LectureNotFoundException {
		StudentActionService studService = new StudentActionServiceImpl();
		if (studService.isLectureAttended(student, lecture)) {

			Collection<Lecture> list = student.getAttendedLectures();
			ArrayList arr = new ArrayList();
			arr.addAll(list);
			arr.remove(lecture.getId() - 1);
			// arr.remove(0);
			Student stud = new StudentImpl(student.getId(), student.getName(), arr);
			dao.update(stud);
			return stud;

			// student.getAttendedLectures().(lecture.getId());
			// dao.update(student);
			// return student;
		} else {
			throw new LectureNotFoundException();
		}

	}

	public boolean isLectureAttended(Student student, Lecture lecture) {
		// return student.getAttendedLectures().contains(lecture);

		for (Lecture l : student.getAttendedLectures()) {
			if (l.getId() == lecture.getId()) {
				return true;
			}
		}
		return false;
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
