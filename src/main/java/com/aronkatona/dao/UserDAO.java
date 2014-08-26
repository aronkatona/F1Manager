package com.aronkatona.dao;

import java.util.List;

import com.aronkatona.model.User;

public interface UserDAO {

	public void addUser(User u);
	public void updateUser(User u);
	public List<User> listUsers();
	public User getUserById(int id);
	public void removeUser(int id);
	public List<User> listUsersByTeamName(String teamName);
} 