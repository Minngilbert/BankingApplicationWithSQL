package com.revature.DaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.Dao.AccountDao;
import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.util.ConnFactory;

public class AccountDaoImpl implements AccountDao {
	public static ConnFactory cf = ConnFactory.getInstance();
	
	public ArrayList<Account> getAccountsByUserName(String userName) throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		Connection conn = cf.getConnection();
		String sql = "select * from \"useraccount\" where \"accountuserid\"= (select \"userid\" from \"bankuser\" where \"username\"= ?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userName);
		ResultSet rs = ps.executeQuery();
		
		Account ac = null;
		while(rs.next()) {
			ac = new Account(rs.getInt("accountid"),rs.getDouble("balance"));
			accounts.add(ac);
		}
		return accounts;
	}
	
	public ArrayList<Account> getAllAccounts() throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		Connection conn = cf.getConnection();
		String sql = "select * from \"useraccount\"";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		Account ac = null;
		while(rs.next()) {
			ac = new Account(rs.getInt("accountid"),rs.getDouble("balance"));
			accounts.add(ac);
		}
		return accounts;
	}
	
	public Account getAccountByAccountId(int accountId) throws SQLException {
		Connection conn = cf.getConnection();
		String sql = "select * from \"useraccount\"  where \"accountid\"= ?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, accountId);
		ResultSet rs = ps.executeQuery();
		
		Account ac = null;
		if(rs.next()) {
			ac = new Account(rs.getInt("accountid"),rs.getDouble("balance"));
		}
		return ac;
	}	

	public boolean createAccount(User u) throws SQLException {
		Connection conn = cf.getConnection();
		String sql = "insert into \"useraccount\" values(?,?,?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, 0);
		ps.setInt(2, u.getUserId());
		ps.setInt(3, 0);
		
		if(ps.executeUpdate() != 0) {
			return true;
		}else
			return false;
	}

	public boolean deleteAccount(int accountId) throws SQLException {
		Connection conn = cf.getConnection();
		String sql = "delete from \"useraccount\" where \"accountid\"= ?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, accountId);
		
		if(ps.executeUpdate() != 0) {
			return true;
		}
		else 
			return false;
	}

	public boolean updateAccountbalance(int accountId, double balance) throws SQLException {
		Connection conn = cf.getConnection();
		String sql = "update \"useraccount\" set \"balance\"=?  where \"accountid\"= ?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, balance);
		ps.setInt(2, accountId);
		
		if(ps.executeUpdate() != 0) {
			return true;
		}
		else 
			return false;
	}
}
