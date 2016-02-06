package dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.api.LectureDAO;
import entityAPI.Lecture.Lecture;
import entityAPI.professor.Professor;
import entityAPI.student.Student;
import entityImpl.lecture.LectureImpl;
import entityImpl.professor.ProfessorImpl;

public class LectureDAOImpl implements LectureDAO {

	public void persist(Lecture lecture) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		
		try {
			session.save(lecture);
			session.getTransaction().commit();
		} catch (Throwable err) {
			System.err
					.println("Could not perform operation " + err);
			session.getTransaction().rollback();
			HibernateSessionManager.shutdown();
		}

	}
	
	public void update(Lecture lecture) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		try {
			session.merge(lecture);
			session.getTransaction().commit();
		} catch (Throwable err) {
			System.err
					.println("Could not perform operation " + err);
			session.getTransaction().rollback();
			HibernateSessionManager.shutdown();
		}
	}

	public Lecture findById(int id) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		Lecture lec = null;

		lec = session.get(LectureImpl.class, id);

		return lec;
	}
	
	public Collection<Student> getStudents(Lecture lecture) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		List<Student> stud = null;
		Lecture lec = session.get(LectureImpl.class, lecture.getId());
		stud = (List<Student>) lec.getAttendingStudents();
		return stud;
	}


	public void removeLecture(Lecture lecture) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		
		session.refresh(lecture);
		
		
		try {
			session.delete(lecture);
			session.getTransaction().commit();
		} catch (Throwable err) {
			System.err
					.println("Could not perform operation " + err);
			session.getTransaction().rollback();
			HibernateSessionManager.shutdown();
		}
	}



	

}
