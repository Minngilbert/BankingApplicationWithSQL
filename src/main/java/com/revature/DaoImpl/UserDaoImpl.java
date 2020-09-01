package com.revature.DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.Dao.UserDao;
import com.revature.beans.User;
import com.revature.util.ConnFactory;

public class UserDaoImpl implements UserDao {
	public static ConnFactory cf = ConnFactory.getInstance();
	
	public User getUserByUserName(String userName) throws SQLException {
		Connection conn = cf.getConnection();
		String sql = "select * from \"bankuser\" where \"username\"=?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		
		User u = null;
		if(rs.next()) {
			u = new User();
			u.setUserId(rs.getInt("userid"));
			u.setFirstName(rs.getString("firstname"));
			u.setLastName(rs.getString("lastname"));
			u.setUserName(userName);
			u.setPassword(rs.getString("Psword"));
			u.setUserType(rs.getInt("usertypeid"));	
		}
		return u;
	}

	public boolean createUser(User user) throws SQLException {
		Connection conn = cf.getConnection();
		String sql = "insert into \"bankuser\" values(?,?,?,?,?,?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, 0);
		ps.setString(2, user.getFirstName());
		ps.setString(3, user.getLastName());
		ps.setString(4, user.getUserName());
		ps.setString(5, user.getPassword());
		ps.setInt(6, user.getUserType());
		
		if(ps.executeUpdate() != 0) {
			return true;
		}else
			return false;
	}
	
	public boolean deleteUser(String userName) throws SQLException {
		Connection conn = cf.getConnection();
		String sql = "delete from \"bankuser\" where \"username\"=?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userName);
		
		if(ps.executeUpdate() != 0) {
			return true;
		}
		else 
			return false;	
	}

	public ArrayList<User> getAllUsers() throws SQLException {
		ArrayList<User> userList = new ArrayList<User>();
		Connection conn = cf.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from \"bankuser\"");
		User u = null;
		
		while(rs.next()) {
			u = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6));
			userList.add(u);
		}
		return userList;
	}
}
