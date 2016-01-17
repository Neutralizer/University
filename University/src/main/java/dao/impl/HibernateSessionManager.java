package dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateSessionManager {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static Session currentSession;
	private static Transaction currentTransaction;

	public static Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	public static Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}

	public static void closeCurrentSession() {
		currentSession.close();
	}

	public static void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}

	public static Session getCurrentSession() {
		return currentSession;
	}

	public static Transaction getCurrentTransaction() {
		return currentTransaction;
	}
	

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

}