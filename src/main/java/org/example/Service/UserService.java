package org.example.Service;

import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

import org.example.DAO.UserDAO;
import org.example.Model.User;

public class UserService {
    public UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public boolean createUser(String username, String userpass, String userrole){

        String hashedPassword = BCrypt.hashpw(userpass, BCrypt.gensalt(12));
        User user = new User();
        user.setUsername(username);
        user.setUserpass(hashedPassword);
        user.setUserrole(userrole);
        userDAO.registerUser(user);
        return true;
    }

    public List<User> getUserById(int id){
        return userDAO.getUserById(id);
    }

    public void updateUser(int id, User user){
        userDAO.updateUser(id, user);
    }

}
