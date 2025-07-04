package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    private final AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    //register new accounts if valid
    public Account register(Account account) {
        if (account == null) return null;

        String username = account.getUsername();
        String password = account.getPassword();

        if (username == null || username.isBlank()) return null;
        if (password == null || password.length() < 4) return null;

        //insert a new account
        return accountDAO.insertAccount(account);
    }

    //login validation
    public Account login(String username, String password) {
        if (username == null || password == null) return null;

        return accountDAO.getAccountByUsernameAndPassword(username, password);
    }
}
