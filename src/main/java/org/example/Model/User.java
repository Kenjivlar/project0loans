package org.example.Model;


import at.favre.lib.crypto.bcrypt.BCrypt;

public class User {

public int id;
public String username;
public String userpass;
public String userrole;


//    BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);
//
//    Hash hash = Password.hash("Hello there")
//            .addPepper("shared-secret")
//            .with(bcrypt);
//
//    public Hash getHash() {
//        return hash;
//    }
//
//    public void setHash(Hash hash) {
//        this.hash = hash;
//    }

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


//    public void setHash(String userpass){
//        String bcryptHashString = BCrypt.withDefaults().hashToString(12, userpass.toCharArray());
//    }


    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

//    public void setUserpass(Hash hash) {
//        this.userpass = String.valueOf(hash);
//    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", userRole='" + userrole + '\'' +
                '}';
    }
}
