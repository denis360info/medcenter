/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.impl;

import command.ICommand;
import constants.PageName;
import controller.OfficeJpaController;
import controller.UserJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Office;
import models.User;

  
/**
 *
 * @author Ника
 */
public class Registration implements ICommand {
    @Resource
    private PageName pageName = PageName.REGISTRATION;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
         
         System.out.println("Registration !!!!!!!!!!!!!!!!!");
            System.out.println(request.getParameter("login_in"));
        if(request.getParameter("login_in") !=null && request.getParameter("login_in").length() != 0){
        String login =  request.getParameter("login_in");
        String password = request.getParameter("password_in");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
             try {
                 EntityManagerFactory emf = Persistence.createEntityManagerFactory("MedCenterMarchenkoPU");
                User user=new User(9,login,password,"Пользователь",email,surname,  name, patronymic, 1,null,null,null,null);
        UserJpaController user_controller=new UserJpaController(emf);
        user_controller.create(user);
        } catch (Exception ex) {
                 Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
             }
        
        }
            request.getSession().setAttribute("role",0);
        //    Client client = serviceFactory.getClientService().signIn(login,password);
//            if(client!=null){
//                HttpSession session = request.getSession();
//                session.setAttribute("user",);
//            }


        
        return pageName.getPath();
        
    }
    
}
