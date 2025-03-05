package org.example.Controller;

//import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Model.Loan;
//import org.example.Model.User;
import org.example.Service.LoanService;

import java.util.List;
import java.util.Objects;

import static org.example.Controller.AuthController.role;

public class LoanController {
    public LoanService loanService;

    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    public void getAllLoans(Context ctx){
        if(Objects.equals(role, "manager")){
            List<Loan> allLoans = loanService.getAllLoans();
            ctx.json(allLoans);
        }else{
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }
    }

    public void getLoan(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(loanService.getLoanById(id));
    }

    public void getUserLoans(Context ctx){
        int id_user = Integer.parseInt(ctx.pathParam("id_user"));
        List<Loan> userLoans = loanService.getUserLoans(id_user);
        ctx.json(userLoans);
    }

    public void updateLoan(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        Loan request = ctx.bodyAsClass(Loan.class);

        Loan loan = new Loan();

        loan.setId(id);
        loan.setTitle(request.title);
        loan.setCompleted(request.completed);

        loanService.updateLoan(id,loan);

        ctx.status(201).json(loan);
    }

    public void updateStatusA(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        String status = ctx.pathParam("status");

        Loan loan = new Loan();

        loan.setId(id);
        loan.setStatus(status);

        loanService.updateStatusA(id,status);

        ctx.status(201).json(loan);
    }
}
