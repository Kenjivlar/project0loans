package org.example.DAO;

import org.example.Model.Loan;
import org.example.Model.User;
import org.example.Util.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {



    public LoanDAO(ConnectionDB connection) {
    }

    public void createLoan(Loan loan){
        ConnectionDB connectionDB = new ConnectionDB();
        try{
            String sql = "INSERT INTO loan (id_user, title, completed) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connectionDB.connection.prepareStatement(sql);

            preparedStatement.setInt(1, loan.getId_user());
            preparedStatement.setString(2, loan.getTitle());
            preparedStatement.setBoolean(3, loan.isCompleted());

            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public List<Loan> getAllLoans(){
        ConnectionDB connectionDB = new ConnectionDB();
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT id, id_user, title, completed, status FROM loan;";

        try{

            PreparedStatement preparedStatement = connectionDB.connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Loan loan = new Loan(
                        rs.getInt("id"),
                        rs.getInt("id_user"),
                        rs.getString("title"),
                        rs.getBoolean("completed"),
                        rs.getString("status"));
                loans.add(loan);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return loans;
    }

    public List<Loan> getLoanById(int id){
        ConnectionDB connectionDB = new ConnectionDB();
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT id, id_user, title, completed, status FROM loan WHERE id = ?;";

        try{

            PreparedStatement preparedStatement = connectionDB.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Loan loan = new Loan(
                        rs.getInt("id"),
                        rs.getInt("id_user"),
                        rs.getString("title"),
                        rs.getBoolean("completed"),
                        rs.getString("status"));
                loans.add(loan);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return loans;
    }


    public List<Loan> getLoanById(int id, int id_user){
        ConnectionDB connectionDB = new ConnectionDB();
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT id, id_user, title, completed, status FROM loan WHERE id = ? AND id_user = ?;";

        try{

            PreparedStatement preparedStatement = connectionDB.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id_user);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Loan loan = new Loan(
                        rs.getInt("id"),
                        rs.getInt("id_user"),
                        rs.getString("title"),
                        rs.getBoolean("completed"),
                        rs.getString("status"));
                loans.add(loan);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return loans;
    }






    public List<Loan> getLoansById(int id_user){
        ConnectionDB connectionDB = new ConnectionDB();
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT id, id_user, title, completed, status FROM loan WHERE id_user = ?;";

        try{

            PreparedStatement preparedStatement = connectionDB.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_user);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Loan loan = new Loan(
                        rs.getInt("id"),
                        rs.getInt("id_user"),
                        rs.getString("title"),
                        rs.getBoolean("completed"),
                        rs.getString("status"));
                loans.add(loan);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return loans;
    }

    public void updateLoan(int id, Loan loan){
        ConnectionDB connectionFl = new ConnectionDB();
        try{
            String sql ="UPDATE loan SET title = ?, completed = ? WHERE id = ?";

            PreparedStatement preparedStatement = connectionFl.connection.prepareStatement(sql);

            preparedStatement.setString(1, loan.title);
            preparedStatement.setBoolean(2, loan.completed);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public void updateLoan(int id,int id_user, Loan loan){
        ConnectionDB connectionFl = new ConnectionDB();
        try{
            String sql ="UPDATE loan SET title = ?, completed = ? WHERE id = ? AND id_user = ?";

            PreparedStatement preparedStatement = connectionFl.connection.prepareStatement(sql);

            preparedStatement.setString(1, loan.title);
            preparedStatement.setBoolean(2, loan.completed);
            preparedStatement.setInt(3, id);
            preparedStatement.setInt(4, id_user);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateStatusA(int id, String status){
        ConnectionDB connectionFl = new ConnectionDB();
        try{
            String sql ="UPDATE loan SET status = ? WHERE id = ?";

            PreparedStatement preparedStatement = connectionFl.connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


}
