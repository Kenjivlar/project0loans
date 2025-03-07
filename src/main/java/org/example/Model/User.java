package org.example.Model;


import at.favre.lib.crypto.bcrypt.BCrypt;

public class User {

public int id;
public String username;
public String userpass;
public String userrole;


    public User(){

}
    public User( String userName, String userPass, String userRole){
        this.username = userName;
        this.userpass = userPass;
        this.userrole = userRole;
    }
public User(int id, String userName, String userPass, String userRole){
    this.id = id;
    this.username = userName;
    this.userpass = userPass;
    this.userrole = userRole;
}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", userRole='" + userrole + '\'' +
                '}';
    }
}
