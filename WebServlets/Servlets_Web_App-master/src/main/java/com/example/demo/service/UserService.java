package com.example.demo.service;

import com.example.demo.exception.ServiceException;

public interface UserService {
    boolean authenticate(String login, String password) throws ServiceException;
    boolean register(String login, String password) throws ServiceException;
}
