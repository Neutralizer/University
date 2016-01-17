package dao.impl;

import org.hibernate.Session;

import dao.api.ProfessorDAO;
import entityAPI.professor.Professor;
import entityAPI.professor.ProfessorActionService;
import entityImpl.professor.ProfessorActionServiceImpl;
import entityImpl.professor.ProfessorImpl;

public class ProfessorDAOImpl implements ProfessorDAO {

	public void persist(Professor professor) {
		
		
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
//		session.refresh(professor);
		try {
			session.save(professor);
			session.getTransaction().commit();
		} catch (Throwable err) {
			System.err
					.println("Could not perform operation " + err);
			session.getTransaction().rollback();
			HibernateSessionManager.shutdown();
		}

	}
	
	public void update(Professor professor) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		try {
			session.merge(professor);
			session.getTransaction().commit();
		} catch (Throwable err) {
			System.err
					.println("Could not perform operation " + err);
			session.getTransaction().rollback();
			HibernateSessionManager.shutdown();
		}
	}

	public Professor findById(int id) {

		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		Professor prof = null;

		prof = session.get(ProfessorImpl.class, id);

		return prof;
	}

	public void kill(Professor professor) {
		Session session = HibernateSessionManager.openCurrentSessionwithTransaction();
		session.refresh(professor);
//		Professor prof1 = profService.unassignLecture(prof);
		try {
			session.delete(professor);
			session.getTransaction().commit();
		} catch (Throwable err) {
			System.err
					.println("Could not perform operation " + err);
			session.getTransaction().rollback();
			HibernateSessionManager.shutdown();
		}
	}

}
