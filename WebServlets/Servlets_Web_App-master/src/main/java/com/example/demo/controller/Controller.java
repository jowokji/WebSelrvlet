package com.example.demo.controller;

import com.example.demo.command.Command;
import com.example.demo.command.CommandType;
import com.example.demo.exception.CommandException;
import com.example.demo.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = {"/controller"})

public class Controller extends HttpServlet {

    static final Logger logger = LogManager.getLogger();
    public void init() {
        ConnectionPool.getInstance();
        logger.log(Level.INFO, "----------> Servlet Init :" + this.getServletInfo());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/htm");

        String commandStr = request.getParameter("command");
        Command command = CommandType.defineCommand(commandStr);
        String page; //choose what to use (request)
        try {
            page = command.execute(request);
            request.getRequestDispatcher(page).forward(request, response);
//            response.sendRedirect(page);

        } catch (CommandException e) {
            throw new ServletException(e); //2
        }
        //implement sessions
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {


    }

    public void destroy() {
//        ConnectionPool.getInstance().destroyPool();
        logger.log(Level.INFO, "----------> Servlet Destroyed :" + this.getServletName());

    }
}