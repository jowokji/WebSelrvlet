package com.example.demo.command;

public class Router {
    private String page = "index.jsp";
    private Type type = Type.FORWARD;

    enum Type {
        FORWARD,
        REDIRECT;
    }

    public Router(String page) {
        this.page = page;
    }

    public Router(String page, Type type) {
        this.page = page;
        this.type = type;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage() {
        return this.page;
    }

    public void setRedirect() {
        this.type = Type.REDIRECT;
    }
}
