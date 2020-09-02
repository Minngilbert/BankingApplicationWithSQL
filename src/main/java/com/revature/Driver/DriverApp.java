package com.revature.Driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.revature.DaoImpl.AccountDaoImpl;
import com.revature.DaoImpl.UserDaoImpl;
import com.revature.beans.Account;
import com.revature.beans.User;

public class DriverApp {
	
	private String bankname = "Pokeman Bank";
	private Scanner cons = new Scanner(System.in);

	private UserDaoImpl udi = new UserDaoImpl();
	private AccountDaoImpl adi = new AccountDaoImpl();
	
	private static User currUser = null;
	
	public static void main(String args[]) {
		DriverApp dr = new DriverApp();	
		try {
			dr.launchMenu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	//This method is run on start up
	public void launchMenu() throws SQLException {
		boolean valid = false;
		while(!valid) {
			System.out.println("Enter 1 to sign into existing account");
			System.out.println("Enter 2 to create a new account");
			System.out.println("Enter 3 to exit");
			
			int choice = Integer.parseInt(isvalidInteger(cons.nextLine()));
			switch(choice) {
			case 1:
				doUserLogin();
				valid = true;
				break;
			case 2:
				createUser();
				break;
			case 3:
				System.out.println("Thank you for choosing "+bankname);
				valid = true;
				break;
			default:
				System.out.println("Invalid input, try again");
			}
		}
	}
	


	public void doUserLogin() throws SQLException {
		boolean isvalid = false;
		while(!isvalid) {
			System.out.println("Please enter your username");
			String username = cons.nextLine();
			System.out.println("Please enter your password");
			String password = cons.nextLine();
			
			User u = udi.getUserByUserName(username);
			
			if(u != null && password.equals(u.getPassword())) {
				currUser = u;
				isvalid = true;
			}
			else {
				System.out.println("The username and password you have entered dont match existing accounts");
				System.out.println("Enter c to create new account");
				System.out.println("Enter q to exit ");
				System.out.println("Press any other key to continue");
				
				char choice = cons.nextLine().toLowerCase().charAt(0);
				
				switch(choice) {
				case 'c':
					createUser();
					break;
				case 'q':
					System.out.println("Thank you for choosing "+bankname);
					isvalid = true;
					break;
				default:
					System.out.println("Try again");
				}
				
			}
		}	
	}
	
	//Used in main menu to validate integer
	public String isvalidInteger(String str) {
		Pattern p = Pattern.compile("[0-9]+");
		if(p.matcher(str).matches()) return str;
		return "0";
	}
	
	/*
	 * Checks the string so that it has no spaces, numbers, or special characters 
	 * with a limit of 1 to 25 characters
	 */
	public boolean nameVal(String str) {
		Pattern p = Pattern.compile("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,25}$");
		if(p.matcher(str.toLowerCase()).matches()) return true;
		return false;
	}
	
	/*
	 * Validated username:
	 * valid characters are A-Z, a-z, 0-9, dashes, and underscores
	 * length 3 - 30 
	 */
	public boolean valUsername(String str) {
		Pattern p = Pattern.compile("[a-zA-Z0-9\\\\._\\\\-]{3,30}");
		if(p.matcher(str.toLowerCase()).matches()) return true;
		return false;
	}
	
	//Create a new user account
	public void createUser() throws SQLException {
		String[] credentials = new String[4];
		String input = "";
		boolean isValid = false;
		//first name 
		while(!isValid) {
			System.out.println("Please entert the first name");
			input = cons.nextLine();
			if(nameVal(input)) {
				credentials[0] = input;
				isValid = true;
			}else System.out.println("Invalid input, must not containt number or special characters");
		}
		//last name
		isValid = false;
		while(!isValid) {
			System.out.println("Please entert the last name");

			input = cons.nextLine();
			if(nameVal(input)) {
				credentials[1] = input;
				isValid = true;
			}else System.out.println("Invalid input, must not containt number or special characters");
		}
		//Username
		isValid = false;
		while(!isValid) {
			System.out.println("Please entert the user name");
			input = cons.nextLine();
			
			
			if(valUsername(input) && udi.getUserByUserName(input) == null) {
				credentials[2] = input;
				isValid = true;
			}else System.out.println("Invalid input, username is already taken"
					+ "\n valid characters are A-Z, a-z, 0-9, dashes, and underscores");
		}
		
		isValid = false;
		while(!isValid) {
			System.out.println("Please entert the password");
			input = cons.nextLine();
			if(input.length() < 30  && input.length() > 0) {
				credentials[3] = input;
				isValid = true;
			}else System.out.println("Invalid input, you know what you did");
		}
		
		String checkPass = "";
		while (!checkPass.equals(credentials[3])) {
			System.out.println("Please verify your password entered: ");
			checkPass = cons.nextLine();
		}
		//create account
		udi.createUser(new User(0,credentials[0],credentials[1],credentials[2],credentials[3],1));
	}
	
	//Shows normal user account menu
	public void userMenu() throws SQLException {
		ArrayList<Account> accounts = adi.getAccountsByUserName(currUser.getUserName());
		char choice ;
		boolean isValid = false;
		int accountId;
		while(isValid) {
			System.out.println("Enter V to view active bank accounts");
			System.out.println("Enter D to make a deposit");
			System.out.println("Enter W to withdraw funds");
			System.out.println("Enter C to create new account");
			System.out.println("Enter q to log out");
			
			if(accounts.size() > 1) {
				
				choice = cons.nextLine().toLowerCase().charAt(0);
				switch(choice) {
				case 'v':
					for(Account a: accounts) {
						System.out.println(a.toString());
					}
					break;
				case 'd':
					System.out.println("Please enter the account number you wish to depoist to");
					accountId = Integer.parseInt(isvalidInteger(cons.nextLine()));
					if(accountId == 0) {
						System.out.println("The account entered does not exist");
						break;
					}else doDeposit(accountId);
					break;
				case 'w':
					
					System.out.println("Please enter the account number you wish to withdraw from");
					accountId = Integer.parseInt(isvalidInteger(cons.nextLine()));
					
					if(accountId == 0) {
						System.out.println("The account entered does not exist");
						break;
					}
					for(Account a: accounts) {
						if(a.getAccountId() != accountId) {
							System.out.println("The account entered does not belong to you");
						}
					}	
					doWithdrawal(accountId);
					break;
				case 'c':
					createAccount(currUser.getUserName());
					accounts = adi.getAccountsByUserName(currUser.getUserName());
					break;
				case 'q':
					isValid = true;
					break;
				}
				
				
			}
			else {
				System.out.println("No bank accounts to act on, enter y to create new bank account ");
				choice = cons.nextLine().toLowerCase().charAt(0);
				
				switch(choice) {
				case 'y':
					createUser();
					break;
				default:
					System.out.println("No actions available, login out");
					isValid = true;
				}	
			}
		}
	}
	
	//creates a new bank account for the user and validates that the user has less than 3 accounts
	public void createAccount(String accountUserName) throws SQLException {
		ArrayList<Account> accounts = adi.getAccountsByUserName(accountUserName);
		if(accounts.size() < 3) {
			adi.createAccount(udi.getUserByUserName(accountUserName));
		}else System.out.println("Limit of 2 accounts");
	}
	
	public void doDeposit(int accountId) throws SQLException {
		Account a = adi.getAccountByAccountId(accountId);
		boolean isValid = false;	
		double amount;
		
		while(!isValid) {
			System.out.println("Please enter the amount you wish to deposit");
			amount = Double.parseDouble(cons.nextLine());
			if (amount < 0.01) {
				System.out.println("Cannot deposit less than one cent.");
			}else {
				isValid = adi.updateAccountbalance(accountId, amount+a.getBalance());
				a = adi.getAccountByAccountId(accountId);
				System.out.println(
						"Successfully deposited $" + amount + " to account.  New balance is $" + a.getBalance());
			}
			
		}
	}

	public void doWithdrawal(int accountId) throws SQLException {
		Account a = adi.getAccountByAccountId(accountId);
		boolean isValid = false;	
		double amount;
		
		while(!isValid) {
			System.out.println("Please enter the amount you wish to withdraw or q to exit");
			amount = Double.parseDouble(cons.nextLine());
			if (amount < 0.01) {
				System.out.println("Cannot withdraw less than one cent.");
			} else if (amount > a.getBalance()) {
				System.out.println("Cannot overdraw an account.");
			}else {
				isValid = adi.updateAccountbalance(accountId, amount - a.getBalance());
				a = adi.getAccountByAccountId(accountId);
				System.out.println(
						"Successfully withdrew $" + amount + " to account.  New balance is $" + a.getBalance());
			
			}
		}
	}
}

