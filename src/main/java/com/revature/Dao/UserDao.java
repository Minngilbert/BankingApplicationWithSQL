package com.revature.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.User;



public interface UserDao {
	
	public User getUserByUserName(String userName) throws SQLException;
	public boolean createUser(User user) throws SQLException;
	public boolean deleteUser(String userName) throws SQLException;
	public boolean deleteAllUsers() throws SQLException;
	public  ArrayList<User> getAllUsers() throws SQLException;
	
	public boolean updateUserfirstname(User u, String change) throws SQLException;
	public boolean updateUserlastname(User u, String change) throws SQLException;
	public boolean updateUserusername(User u, String change) throws SQLException;
	public boolean updateUserpsword(User u, String change) throws SQLException;
}
