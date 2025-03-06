package org.example.Controller;

//import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Model.Loan;
//import org.example.Model.User;
import org.example.Model.User;
import org.example.Service.LoanService;

import java.util.List;
import java.util.Objects;

import static org.example.Controller.AuthController.id_user;
import static org.example.Controller.AuthController.role;



public class LoanController {

    int id_log = id_user;

    public LoanService loanService;

    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    public void createLoan(Context ctx){
        if(role.equals("user")){
            Loan request = ctx.bodyAsClass(Loan.class);

            if(request.getTitle() == null){
                ctx.status(400).json("{\"error\":\"Missing the title\"}");
                return;
            }

            boolean success = loanService.createLoan(id_log, request.getTitle(), request.isCompleted());
            if(success){
                ctx.status(201).json("{\"message\":\"Loan registered successfully\"}");
            }else{
                ctx.status(409).json("{\"error\":\"Creation of the Loan Failed\"}");
            }
        } else {
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }

    }

    public void getAllLoans(Context ctx){
        if(role.equals("manager")){
            List<Loan> allLoans = loanService.getAllLoans();
            ctx.json(allLoans);
        }else if(role.equals("user")){
            List<Loan> userLoans = loanService.getUserLoans(id_user);
            ctx.json(userLoans);
        } else {
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }
    }

    public void getLoan(Context ctx){
        if(role.equals("manager")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(loanService.getLoanById(id));
        }else{
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }

    }

    public void getUserLoans(Context ctx){
        int id_user = Integer.parseInt(ctx.pathParam("id_user"));
        List<Loan> userLoans = loanService.getUserLoans(id_user);
        ctx.json(userLoans);
    }

    public void updateLoan(Context ctx){
        if(role.equals("manager")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            Loan request = ctx.bodyAsClass(Loan.class);

            Loan loan = new Loan();

            loan.setId(id);
            loan.setTitle(request.title);
            loan.setCompleted(request.completed);

            loanService.updateLoan(id,loan);

            ctx.status(201).json(loan);
        }else{
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }

    }

    public void updateStatusA(Context ctx){
        if(role.equals("manager")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            String status = ctx.pathParam("status");

            Loan loan = new Loan();

            loan.setId(id);
            loan.setStatus(status);

            loanService.updateStatusA(id,status);

            ctx.status(201).json(loan);
        }else{
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }
    }
}
