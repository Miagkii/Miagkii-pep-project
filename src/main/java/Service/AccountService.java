package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }
    
    public Account registration(Account account) {
        if(account.username == null || account.username.trim().isEmpty()) {
            System.out.println("Registration failed: Username cannot be blank");
            return null;
        }
        if (account.password == null || account.password.length() < 4) {
            System.out.println("Registration failed: Password must be at least 4 characters long");
            return null;
        }
        try {
            if(accountDAO.getByUsername(account.username) != null){
                System.out.println("Registration failed: Username already exists.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Registration failed: Database error occurred.");
            return null;
        }
        try {
            Account registredAccount = accountDAO.insert(account);
            return registredAccount;
        } catch (Exception e) {
            System.out.println("Registration failed: Could not register user due to a database error.");
        }
        return null;
    }

    public Account login(Account account) {
        try {
            Account loggedAccount = accountDAO.login(account.getUsername(), account.getPassword());
            return loggedAccount;
        } catch (Exception e) {
            System.out.println("Login failed: Exception occurred while validating login.");
        }
        return null;
    }
    
    public Account getById(int id) {
        try {
            Account account = accountDAO.getById(id);
            return account;
        } catch (Exception e) {
            System.out.println("Account retrieval failed");
        }
        return null;
    }

    public boolean accountIsPresent(int id){
        if(getById(id) == null) {
            return false;
        } else return true;
    }
    

}
