package com.wstore.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = buildSessionFactory();
	private static StandardServiceRegistry serviceRegistry;

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static SessionFactory buildSessionFactory() {
		try {
			if (sessionFactory == null) {
				Configuration configuration = new Configuration().configure();
				serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
						.build();
				sessionFactory = configuration.configure().buildSessionFactory(serviceRegistry);
			}
			return sessionFactory;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
