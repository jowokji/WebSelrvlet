package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        HttpSession session = request.getSession();

        UserService userService = UserServiceImpl.getInstance();
        String page;
        try {
            if( userService.authenticate(login, password)){
                request.setAttribute("user", login);
                session.setAttribute("user_name", login);


                page = "pages/main.jsp";
            } else {
                request.setAttribute("login_msg", "Wrong login or password");
                page = "index.jsp";
            }
            session.setAttribute("current_page", page);
        } catch (ServiceException e) {
           throw new CommandException("LoginCommand error", e);
        }
        return page;
    }
}
