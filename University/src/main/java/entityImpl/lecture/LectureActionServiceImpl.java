package entityImpl.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import dao.api.LectureDAO;
import dao.impl.HibernateSessionManager;
import dao.impl.LectureDAOImpl;
import daoJDBC.impl.LectureDAOImplJDBC;
import entityAPI.Lecture.Lecture;
import entityAPI.Lecture.LectureActionService;
import entityAPI.professor.Professor;
import entityAPI.student.Student;
import entityImpl.student.LectureNotFoundException;

public class LectureActionServiceImpl implements LectureActionService {

	LectureDAOImplJDBC dao = new LectureDAOImplJDBC();
//	LectureDAOImpl dao = new LectureDAOImpl();
	
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
			
			
			// get the id of the student to be removed and remove the object with
			// the same id from the collection
			
			Iterator<Student> iter = list.iterator();
//			int counter = 0;
			while(iter.hasNext()){
				if(iter.next().getId() == student.getId()){
					iter.remove();
//					list.remove(iter.next());
					break;
				}
				
//				counter++;
				//remove here when found
			}
			
			//
//			list.remove();
			Lecture lec = new LectureImpl(lecture.getId(), lecture.getName(),
					lecture.getLeadingProfessor(), list);
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
	
	public Collection<Student> findAllLecturesStudents(Lecture lecture) {
		return dao.getStudents(lecture);
	}

	public Lecture setLectureProfessor(Lecture lecture, Professor professor) {
		Lecture lec = new LectureImpl(lecture.getId(), lecture.getName(), 
				professor, lecture.getAttendingStudents());
		dao.update(lec);
		return lec;

	}

	public Lecture removeLectureProfessor(Lecture lecture) {
		Lecture lec = new LectureImpl(lecture.getId(), lecture.getName(),
				null, lecture.getAttendingStudents());
		dao.update(lec);
		return lec;

	}

}
