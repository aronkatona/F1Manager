package com.aronkatona.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.aronkatona.model.Driver;
import com.aronkatona.model.Team;

@Repository
public class TeamDAOImpl implements TeamDAO {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addTeam(Team t) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(t);
	}

	@Override
	public void updateTeam(Team t) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Team> listTeams() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Team> teamsList = session.createQuery("from Team").list();
		return teamsList;
	}

	@Override
	public Team getTeamById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Team t = (Team) session.get(Team.class, new Integer(id));
		return t;
	}

	@Override
	public void removeTeam(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Team t = (Team) session.get(Team.class, new Integer(id));
		if(t != null){
			session.delete(t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Driver> listTeamsDriversById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Driver where team_id = :id ");
		query.setParameter("id", id);
		return query.list();
	}

} 