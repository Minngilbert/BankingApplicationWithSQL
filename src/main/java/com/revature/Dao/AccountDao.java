package com.revature.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.beans.Account;
import com.revature.beans.User;

public interface AccountDao {
	public Account getAccountByAccountId(int accountId) throws SQLException;
	public ArrayList<Account> getAccountsByUserName(String userName) throws SQLException;
	public ArrayList<Account> getAllAccounts() throws SQLException;
	public boolean updateAccountbalance(int accountid, double balance) throws SQLException;
	public boolean createAccount(User u) throws SQLException;
	public boolean deleteAccount(int accountId) throws SQLException;
}
