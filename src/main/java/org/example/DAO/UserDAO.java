package org.example.DAO;

import org.example.Model.User;
import org.example.Util.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public UserDAO(ConnectionDB connection) {
    }

    public void registerUser(User user){
        ConnectionDB connectionDB = new ConnectionDB();
        try{
        String sql = "INSERT INTO users_loans (username, userpass, userrole) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connectionDB.connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getUserpass());
            preparedStatement.setString(3, user.getUserrole());

            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public List<User> getUserById(int id){
        ConnectionDB connectionDB = new ConnectionDB();
        List<User> users = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT id, username, userpass, userrole FROM users_loans WHERE id = ?";

            PreparedStatement preparedStatement = connectionDB.connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("userpass"),
                        rs.getString("userrole")
                );
                users.add(user);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void updateUser(int id,User user){
        ConnectionDB connectionFl = new ConnectionDB();
        try{
            String sql ="UPDATE users_loans SET username = ?, userpass = ? WHERE id = ?";

            PreparedStatement preparedStatement = connectionFl.connection.prepareStatement(sql);

            preparedStatement.setString(1, user.username);
            preparedStatement.setString(2, user.userpass);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
