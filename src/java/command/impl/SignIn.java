package command.impl;

import command.ICommand;
import constants.PageName;
import controller.OfficeJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
//import epam.chernova.finalproject.entity.ext.Client;
//import epam.chernova.finalproject.factory.ServiceFactory;
//import epam.chernova.finalproject.webenum.PageName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Office;

public class SignIn implements ICommand {
   // private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private PageName pageName = PageName.INDEX;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login =  request.getParameter("login_in");
        String password = request.getParameter("password_in");
        boolean role = Boolean.parseBoolean(request.getParameter("check"));

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111");
     
            request.getSession().setAttribute("role",0);
        //    Client client = serviceFactory.getClientService().signIn(login,password);
//            if(client!=null){
//                HttpSession session = request.getSession();
//                session.setAttribute("user",);
//            }
            Office office = new Office(123, 123);
         //   OfficeJpaController of = new  OfficeJpaController();
      
          //  of.create(office);
     

        
        return pageName.getPath();
        
    }
}
