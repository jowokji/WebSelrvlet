package com.example.demo.service.impl;

import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.dao.impl.UserDaoImpl;
import com.example.demo.service.UserService;

public class UserServiceImpl implements UserService {
    private static final UserService instance = new UserServiceImpl();

    private UserServiceImpl(){}

    public static UserService getInstance() {
        return instance;
    }




    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        //validate login pass + md5
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try {
            match = userDao.authenticate(login, password);
        } catch (DaoException e) {
            throw new ServiceException("ServiceException authenticate error",e);
        }
        return match;
    }

    @Override
    public boolean register(String login, String password) throws ServiceException {
        //validate login pass + md5
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try {
            match = userDao.authenticate(login, password);
        } catch (DaoException e) {
            throw new ServiceException("ServiceException register error ",e);
        }
        return match;
    }
}
