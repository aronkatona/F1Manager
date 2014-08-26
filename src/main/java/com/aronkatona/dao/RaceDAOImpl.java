package com.aronkatona.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.aronkatona.model.Race;

@Repository
public class RaceDAOImpl implements RaceDAO {
	

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addRace(Race r) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(r);
	}

	@Override
	public void updateRace(Race r) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(r);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Race> listRaces() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Race> racesList = session.createQuery("from Race").list();
		return racesList;
	}
	

	@Override
	public Race getRaceById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Race r = (Race) session.get(Race.class, new Integer(id));
		return r;
	}

	@Override
	public void removeRace(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Race r = (Race) session.get(Race.class, new Integer(id));
		if(r != null){
			session.delete(r);
		}
	}

} 