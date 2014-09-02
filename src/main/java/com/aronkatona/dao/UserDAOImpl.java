package com.aronkatona.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.aronkatona.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addUser(User u) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(u);
	}

	@Override
	public void updateUser(User u) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(u);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> usersList = session.createQuery("from User").list();
		return usersList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUsersByTeamName(String teamName){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User where team_id = (select id from Team where name = :teamName)");
		query.setParameter("teamName", teamName);
		return query.list();		
	}

	@Override
	public User getUserById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		User u = (User) session.get(User.class, new Integer(id));
		return u;
	}

	@Override
	public void removeUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		User u = (User) session.get(User.class, new Integer(id));
		if(u != null){
			session.delete(u);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByName(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User where name = :userName)");
		query.setParameter("userName", userName);
		List<User> userList = query.list();
		return userList.get(0);
	}

} 