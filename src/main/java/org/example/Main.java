package org.example;

import io.javalin.Javalin;
import org.example.Controller.UserController;
import org.example.Controller.LoanController;
import org.example.Controller.AuthController;
import org.example.DAO.LoanDAO;
import org.example.DAO.UserDAO;
import org.example.Model.User;
import org.example.Service.LoanService;
import org.example.Service.UserService;
import org.example.Util.ConnectionDB;

import java.sql.*;

import static org.example.Controller.AuthController.role;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

private static final String DROP_TABLES_SQL = """
        DROP TABLE IF EXISTS users_loans CASCADE;
        DROP TABLE IF EXISTS loan CASCADE;
        """;

    private static final String CREATE_TABLES_SQL = """
        CREATE TABLE IF NOT EXISTS users_loans (
            id serial not null,
                userName varchar(255),
                userPass varchar(255),
                userRole varchar(255)
        );

        CREATE TABLE IF NOT EXISTS loan (
            id serial not null,
                id_user int,
                title varchar(255),
                completed boolean,
                status varchar(255)
        );
        """;

    private static final String INSERT_DATA_SQL = """
        

            INSERT INTO loan (id_user, title, completed, status)
            VALUES (1,'bike',true,'approve'),
            (1,'house',false,'reject'),
            (3,'car',true,'approve'),
            (4,'computer',false,'approve'),
            (5,'gameconsole',true,'reject'),
            (1,'car',true,'reject');
            
            INSERT INTO users_loans (username, userpass, userrole)
                values ('charlie','1234','user'),
                ('charles','4321','manager'),
                ('ralph','2345','user'),
                ('lupin','5432','manager'),
                ('alonso','3456','user'),
                ('lewis','6543','user');
        """;
    public static void main(String[] args) {

        ConnectionDB connection = new ConnectionDB();

        resetDatabase();

        UserDAO userDAO = new UserDAO(connection);
        LoanDAO loanDAO = new LoanDAO(connection);

        UserService userService = new UserService(userDAO);
        LoanService loanService = new LoanService(loanDAO);

        UserController userController = new UserController(userService);
        LoanController loanController = new LoanController(loanService);


        Javalin app = Javalin.create(config -> {
        }).start(7001);

        app.get("/", ctx -> ctx.result(role));
        app.post("/register", userController::createUser);
        app.post("/login", AuthController::login);
        app.post("logout", AuthController::logout);
        app.get("/checklogin", AuthController::checkLogin);
        app.get("/user/{id}", userController::getUserById);
        app.put("/user/{id}", userController::updateUser);
        app.get("/loans", loanController::getAllLoans);
        //app.get("/loans/userid:/{id_user}", loanController::getUserLoans);
        app.get("/loans/{id}", loanController::getLoan);
        app.put("/loans/{id}", loanController::updateLoan);
        app.put("/loans/{id}/{status}", loanController::updateStatusA);
        app.post("/loans", loanController::createLoan);

        role = "";
        //User user = new User();

//        String greet = user.getUserpass();
//        app.get("/", ctx -> ctx.result(greet));

//        User user = new User();
//
//        System.out.println(user.getHash());


    }

    private static void resetDatabase(){
            runSql(DROP_TABLES_SQL);
            runSql(CREATE_TABLES_SQL);
            runSql(INSERT_DATA_SQL);
    }
    private static void runSql(String sql) {
        ConnectionDB connectionDB = new ConnectionDB();
        try{
            Statement stmt = connectionDB.connection.createStatement();
            stmt.execute(sql);
            System.out.println("Executed SQL:\n" + sql);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}