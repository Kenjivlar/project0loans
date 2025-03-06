package org.example.Service;

import org.example.DAO.LoanDAO;
import org.example.Model.Loan;
import org.example.Model.User;

import java.util.List;

public class LoanService {

    public LoanDAO loanDAO;

    public LoanService(LoanDAO loanDAO){
        this.loanDAO = loanDAO;
    }
    public boolean createLoan(int id_user, String title, boolean completed){
        Loan loan = new Loan();
        loan.setId_user(id_user);
        loan.setTitle(title);
        loan.setCompleted(completed);
        loanDAO.createLoan(loan);
        return true;
    }

    public List<Loan> getAllLoans(){
        return loanDAO.getAllLoans();
    }

    public List<Loan> getUserLoans(int id_user){
        return loanDAO.getLoansById(id_user);
    }

    public List<Loan> getLoanById(int id){
        return loanDAO.getLoanById(id);
    }

    public List<Loan> getLoanById(int id, int id_user){
        return loanDAO.getLoanById(id, id_user);
    }

    public void updateLoan(int id, Loan loan){
        loanDAO.updateLoan(id, loan);
    }

    public void updateLoan(int id, int id_user, Loan loan){
        loanDAO.updateLoan(id, id_user, loan);
    }

    public void updateStatusA(int id, String status){
        loanDAO.updateStatusA(id, status);
    }
}
