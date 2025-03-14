package org.example.Controller;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.example.Model.Loan;
import org.example.Model.User;
import org.example.Service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    public LoanService loanService;

    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    public void createLoan(Context ctx){
        //Here I get the session
        HttpSession session = ctx.req().getSession();
        //Here I get the user session
        User user = (User) session.getAttribute("user");

        Loan loan = new Loan();


        if(user == null){
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
            //Here I just get the user role of the session
        }else if(user.getUserrole().equals("user")){
            Loan request = ctx.bodyAsClass(Loan.class);

            if(request.getTitle() == null){
                ctx.status(400).json("{\"error\":\"Missing the title\"}");
                return;
            }
//                          HERE I GET THE id of the user session user.getId()
            boolean success = loanService.createLoan(user.getId(), request.getTitle(), request.isCompleted());
            if(success){
                logger.info("User Id: {}", user.getId());
                logger.info("Loan Created: {}", request.getTitle());
                ctx.status(201).json("{\"message\":\"Loan registered successfully\"}");
            }else{
                ctx.status(409).json("{\"error\":\"Creation of the Loan Failed\"}");
            }
        } else {
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }

    }

    public void getAllLoans(Context ctx){
        HttpSession session = ctx.req().getSession();
        User user = (User) session.getAttribute("user");

        if(user == null){
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }else if(user.getUserrole().equals("manager")){
            logger.info("RoleM: {}", user.getUserrole());
            logger.info("LoansM: {}", loanService.getAllLoans());
            List<Loan> allLoans = loanService.getAllLoans();
            ctx.json(allLoans);
        }else if(user.getUserrole().equals("user")){
            logger.info("RoleU: {}", user.getUserrole());
            logger.info("LoansU: {}", loanService.getUserLoans(user.getId()));
            List<Loan> userLoans = loanService.getUserLoans(user.getId());
            ctx.json(userLoans);
        }else {
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }
    }

    public void getLoan(Context ctx){
        HttpSession session = ctx.req().getSession();
        User user = (User) session.getAttribute("user");

        if(user == null){
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }else if(user.getUserrole().equals("manager")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            logger.info("Loan Requested: {}", loanService.getLoanById(id));
            ctx.json(loanService.getLoanById(id));
        }else if(user.getUserrole().equals("user")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            logger.info("Loan Requested User: {}", loanService.getLoanById(id, user.getId()));
            ctx.json(loanService.getLoanById(id, user.getId()));
        }else{
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }

    }

//    public void getUserLoans(Context ctx){
//        int id_user = Integer.parseInt(ctx.pathParam("id_user"));
//        List<Loan> userLoans = loanService.getUserLoans(id_user);
//        ctx.json(userLoans);
//    }

    public void updateLoan(Context ctx){
        HttpSession session = ctx.req().getSession();
        User user = (User) session.getAttribute("user");

        if(user == null){
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }else if(user.getUserrole().equals("manager")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            Loan request = ctx.bodyAsClass(Loan.class);

            Loan loan = new Loan();

            loan.setId(id);
            loan.setTitle(request.title);
            loan.setCompleted(request.completed);

            loanService.updateLoan(id,loan);

            logger.info("Loan Updated By Manager: {}", loan);

            ctx.status(201).json(loan);
        }else if(user.getUserrole().equals("user")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            Loan request = ctx.bodyAsClass(Loan.class);

            Loan loan = new Loan();

            loan.setId(id);
            loan.setTitle(request.title);
            loan.setCompleted(request.completed);

            loanService.updateLoan(id,user.getId(), loan);

            logger.info("Loan Updated By User: {}", loan);

            ctx.status(201).json(loan);
        }else{
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }

    }

    public void updateStatusA(Context ctx){
        HttpSession session = ctx.req().getSession();
        User user = (User) session.getAttribute("user");

        if(user == null){
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }else if(user.getUserrole().equals("manager")){
            int id = Integer.parseInt(ctx.pathParam("id"));
            String status = ctx.pathParam("status");

            Loan loan = new Loan();

            loan.setId(id);
            loan.setStatus(status);

            loanService.updateStatusA(id,status);

            logger.info("Loan Updated Status: {}", loan);

            ctx.status(201).json(loan);
        }else{
            ctx.status(403).json("{\"error\":\"User not allow to do the action\"}");
        }
    }
}
