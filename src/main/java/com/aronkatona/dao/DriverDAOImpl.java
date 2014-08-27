package com.aronkatona.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.aronkatona.model.Driver;
import com.aronkatona.model.Team;

@Repository
public class DriverDAOImpl implements DriverDAO {
	

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addDriver(Driver d) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(d);
	}

	@Override
	public void updateDriver(Driver d) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(d);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Driver> listDrivers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Driver> driversList = session.createQuery("from Driver").list();
		return driversList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Driver> listDriversByTeamName(String teamName){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Driver where team_id = (select id from Team where name = :teamName)");
		query.setParameter("teamName", teamName);
		return query.list();		
	}

	@Override
	public Driver getDriverById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Driver d = (Driver) session.get(Driver.class, new Integer(id));

		return d;
	}

	@Override
	public void removeDriver(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Driver d = (Driver) session.get(Driver.class, new Integer(id));
		if(d != null){
			session.delete(d);
		}
	}
	
} 