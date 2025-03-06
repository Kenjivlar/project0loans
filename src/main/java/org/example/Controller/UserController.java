package org.example.Controller;

import io.javalin.http.Context;
import org.example.Model.User;
import org.example.Service.UserService;
import org.example.Util.ConnectionDB;

import java.sql.*;

import static org.example.Controller.AuthController.id_user;
import static org.example.Controller.AuthController.role;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
    this.userService = userService;
    }


    public void createUser(Context ctx){
        User request = ctx.bodyAsClass(User.class);

        if(request.getUsername() == null || request.getUserpass() == null || request.getUserrole() == null){
            ctx.status(400).json("{\"error\":\"Missing user name or password or user role\"}");
            return;
        }
        if (userExists(request.getUsername())) {
            ctx.status(409).json("{\"error\":\"Username already taken\"}");
            return;
        }
        boolean success = userService.createUser(request.getUsername(), request.getUserpass(), request.getUserrole());
        if(success){
            ctx.status(201).json("{\"message\":\"User registered successfully\"}");
        }else{
            ctx.status(409).json("{\"error\":\"User already exists\"}");
        }
    }

    private static boolean userExists(String username) {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            String sql = "SELECT 1 FROM users_loans WHERE username = ?";
            PreparedStatement preparedStatement = connectionDB.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();  // if there's a row, user exists
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true; // fail safe, assume it exists if error
        }
    }

    public void getUserById(Context ctx){
        if(role.equals("manager")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(userService.getUserById(id));
        } else if (ctx.pathParam("id").equals(String.valueOf(id_user))) {
            ctx.json(userService.getUserById(id_user));
        } else{
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }

    }


    public void updateUser(Context ctx){
        if(role.equals("manager")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            User request = ctx.bodyAsClass(User.class);

            User user = new User();

            user.setId(id);
            user.setUsername(request.username);
            user.setUserpass(request.userpass);
            user.setUserrole(request.userrole);

            userService.updateUser(id,user);

            ctx.status(201).json(user);

        } else if (ctx.pathParam("id").equals(String.valueOf(id_user))){
            int id = Integer.parseInt(ctx.pathParam("id"));
            User request = ctx.bodyAsClass(User.class);

            User user = new User();

            user.setId(id);
            user.setUsername(request.username);
            user.setUserpass(request.userpass);
            user.setUserrole(role);

            userService.updateUser(id,user);

            ctx.status(201).json(user);
        } else{
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }

    }
}
