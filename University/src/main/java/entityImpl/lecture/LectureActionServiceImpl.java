package entityImpl.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import dao.api.LectureDAO;
import dao.impl.HibernateSessionManager;
import dao.impl.LectureDAOImpl;
import entityAPI.Lecture.Lecture;
import entityAPI.Lecture.LectureActionService;
import entityAPI.professor.Professor;
import entityAPI.student.Student;
import entityImpl.student.LectureNotFoundException;

public class LectureActionServiceImpl implements LectureActionService {

	LectureDAO dao = new LectureDAOImpl();
	
	public Lecture createLecture(String name) {
		
		Lecture lec = new LectureImpl(name);
		dao.persist(lec);
		return lec;
	}

	public void updateLecture(Lecture lecture) {
		dao.update(lecture);
	}

	public Lecture findLectureById(int id) {
		return dao.findById(id);
	}

	public Lecture assignStudent(Lecture lecture, Student student) {
		Lecture lec = new LectureImpl(lecture.getId(), lecture.getName(),
				lecture.getLeadingProfessor(), lecture.getAttendingStudents());
		lec.getAttendingStudents().add(student);

		dao.update(lec);
		return lec;
	}

	public Lecture unassignStudent(Lecture lecture, Student student)
			throws StudentNotFoundException {
		LectureActionService lecService = new LectureActionServiceImpl();

		if (lecService.isStudentAttending(lecture, student)) {
			Collection<Student> list = lecture.getAttendingStudents();
			ArrayList arr = new ArrayList();
			arr.addAll(list);
			arr.remove(student.getId() - 1);
			Lecture lec = new LectureImpl(lecture.getId(), lecture.getName(),
					lecture.getLeadingProfessor(), arr);
			// lec.getAttendingStudents().remove(student);
			dao.update(lec);
			return lec;
		} else {
			throw new StudentNotFoundException();
		}

	}

	public boolean isStudentAttending(Lecture lecture, Student student) {

		for (Student s : lecture.getAttendingStudents()) {
			if (s.getId() == student.getId()) {
				return true;
			}
		}
		return false;

	}

	public void removeLecture(Lecture lecture) throws LectureNotFoundException {
		dao.removeLecture(lecture);
	}

	public Lecture setLectureProfessor(Lecture lecture, Professor professor) {
		Lecture lec = new LectureImpl(lecture.getId(), lecture.getName(), professor);
		dao.update(lec);
		return lec;

	}

	public Lecture removeLectureProfessor(Lecture lecture) {
		Lecture lec = new LectureImpl(lecture.getId(), lecture.getName(), null);
		dao.update(lec);
		return lec;

	}

}
