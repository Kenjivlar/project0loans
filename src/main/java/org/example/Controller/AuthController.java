package org.example.Controller;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.example.Model.User;
import org.example.Util.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthController {

    public static String role;
    public static int id_user;
    public static void login(Context ctx){
        User requestUser = ctx.bodyAsClass(User.class);
        if (requestUser.getUsername() == null || requestUser.getUserpass() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }

        // Check credentials. dbUser makes it clear we got this data from the db after verifying with the requestUser.
        User dbUser = getUserDB(requestUser.getUsername());
        if (dbUser == null) {
            ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
            return;
        }

        // Compare password (plain text for demo)
        if (!dbUser.getUserpass().equals(requestUser.getUserpass())) {
            ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
            return;
        }

        // If valid, start a session
        HttpSession session = ctx.req().getSession(true);
        session.setAttribute("user", dbUser);
//        session.setAttribute("username", dbUser.getUsername());
        session.setAttribute("id user", dbUser.getId());
        session.setAttribute("role", dbUser.getUserrole());
        role = String.valueOf(session.getAttribute("role"));
        id_user = (int) session.getAttribute("id user");

        ctx.status(200).json("{\"message\":\"Login successful\"}");
    }

    public static void logout(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        role = "";
        ctx.status(200).json("{\"message\":\"Logged out\"}");
    }


    private static User getUserDB(String username) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            String sql = "SELECT * FROM users_loans WHERE username = ?";
            PreparedStatement preparedStatement = connectionDB.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setUserpass(rs.getString("userpass"));
                user.setUserrole(rs.getString("userrole"));
                return user;
            }
            return null;  // if there's a row, user exists
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null; // fail safe, assume it exists if error
        }
    }

    public static void checkLogin(Context ctx) {

        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            ctx.status(200).json("{\"message\":\"You are logged in\"}");
            //ctx.json(session.getAttribute("username"));
            //ctx.json(session.getAttribute("userrole"));

        } else {
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }
}
