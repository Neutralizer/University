package dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import dao.api.StudentDAO;
import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;
import entityImpl.student.StudentImpl;

public class StudentDAOImpl implements StudentDAO {

	public void persist(Student student) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();

		try {
			session.save(student);
			session.getTransaction().commit();
		} catch (Throwable err) {
			System.err.println("Could not perform operation " + err);
			session.getTransaction().rollback();
			HibernateSessionManager.shutdown();
		}
	}

	public void update(Student student) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		try {
			session.merge(student);
			session.getTransaction().commit();
		} catch (Throwable err) {
			System.err.println("Could not perform operation " + err);
			session.getTransaction().rollback();
			HibernateSessionManager.shutdown();
		}
	}

	public Student findById(int id) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		Student stud = null;

		stud = session.get(StudentImpl.class, id);

		return stud;
	}

	public void kill(Student student) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		session.refresh(student);
		try {
			session.delete(student);
			session.getTransaction().commit();
		} catch (Throwable err) {
			System.err.println("Could not perform operation " + err);
			session.getTransaction().rollback();
			HibernateSessionManager.shutdown();
		}
	}

}
