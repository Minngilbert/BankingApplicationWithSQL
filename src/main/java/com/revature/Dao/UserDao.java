package com.revature.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.User;



public interface UserDao {
	
	public User getUserByUserName(String userName) throws SQLException;
	public boolean createUser(User user) throws SQLException;
	public boolean deleteUser(String userName) throws SQLException;
	public  ArrayList<User> getAllUsers() throws SQLException;
}
