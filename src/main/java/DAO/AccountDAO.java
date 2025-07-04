package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {

    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        Account result = null;

        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            int affected = ps.executeUpdate();
            if (affected != 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    result = new Account(id, account.getUsername(), account.getPassword());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Account getAccountById(int accountId) {
    Connection connection = ConnectionUtil.getConnection();
    Account account = null;

    try {
        String sql = "SELECT * FROM Account WHERE account_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, accountId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            account = new Account(
                rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return account;
}

    public Account getAccountByUsernameAndPassword(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();
        Account account = null;

        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }
}
