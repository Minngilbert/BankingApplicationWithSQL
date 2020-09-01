package com.revature.Driver;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.revature.DaoImpl.UserDaoImpl;
import com.revature.beans.User;

public class DriverApp {
	
	private String bankname = "Pokeman Bank";
	private Scanner cons = new Scanner(System.in);
	private UserDaoImpl udi = new UserDaoImpl();

	private User currUser = null;
	
	public static void main(String args[]) {
		DriverApp dr = new DriverApp();
		dr.showMainMenu();
	}
	
	public String isvalidInteger(String str) {
		Pattern p = Pattern.compile("[0-9]+");
		if(p.matcher(str).matches()) return str;
		return "0";
	}
	
	
	public void showMainMenu() {
		boolean valid = false;
		while(!valid) {
			System.out.println("Enter 1 to sign into existing account");
			System.out.println("Enter 2 to create a new account");
			System.out.println("Enter 3 to exit");
			
			int choice = Integer.parseInt(isvalidInteger(cons.nextLine()));
			switch(choice) {
			case 1:
				//login
				valid = true;
				break;
			case 2:
				//create account
				break;
			case 3:
				System.out.println("Thank you for choosing "+bankname);
				valid = true;
				break;
			default:
				System.out.println("Invalid, try again");
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
			if(u != null && u.getPassword() == password) {
				currUser = u;
			}
			else {
				System.out.println("The username and password you have entered dont match existing accounts");
				System.out.println("Enter c to create new account");
				System.out.println("Enter q to exit ");
				System.out.println("Press any other key to continue");
				
				char choice = cons.nextLine().toLowerCase().charAt(0);
				
				switch(choice) {
				case 'c':
					//call create account
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
}

