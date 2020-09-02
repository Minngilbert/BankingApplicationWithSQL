import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.revature.DaoImpl.AccountDaoImpl;
import com.revature.DaoImpl.UserDaoImpl;
import com.revature.Driver.DriverApp;
import com.revature.beans.User;

public class DriverTest {

	DriverApp dr = new DriverApp();
	UserDaoImpl udi = new UserDaoImpl();
	AccountDaoImpl adi = new AccountDaoImpl();

	
	User testUser = new User(1,"Test1","Test2","admin_90","password",2);
	@Test
	public void testCreateUser() {
		try {
			Assertions.assertEquals(true,udi.createUser(testUser));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUserAccountDelete() {
		try {
			Assertions.assertEquals(true,udi.deleteUser("admin_90"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateAccount() {
		try {
			Assertions.assertEquals(true,adi.createAccount(testUser));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
