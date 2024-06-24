package com.example.demo.dao;

import com.example.demo.exception.DaoException;

public interface UserDao {
    boolean authenticate(String login, String password) throws DaoException;
//    boolean register(String login, String password);

}
