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
    public List<Loan> getAllLoans(){
        return loanDAO.getAllLoans();
    }
    public List<Loan> getUserLoans(int id_user){
        return loanDAO.getLoansById(id_user);
    }

    public List<Loan> getLoanById(int id){
        return loanDAO.getLoanById(id);
    }

    public void updateLoan(int id, Loan loan){
        loanDAO.updateLoan(id, loan);
    }

    public void updateStatusA(int id, String status){
        loanDAO.updateStatusA(id, status);
    }
}
