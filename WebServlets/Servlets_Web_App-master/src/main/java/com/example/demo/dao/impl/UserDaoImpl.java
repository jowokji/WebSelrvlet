package com.example.demo.dao.impl;

import com.example.demo.exception.DaoException;
import com.example.demo.dao.BaseDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final String SELECT_PASSWORD_FROM_USER = "SELECT password FROM users WHERE login = ?";

    // singleton
    private UserDaoImpl() {
    }

    private static final UserDaoImpl instance = new UserDaoImpl();


    public static UserDaoImpl getInstance() {
        return instance;
    }


    @Override
    public boolean insert(User user)  {
        return false;

    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        boolean match = false;
        try {

            ConnectionPool pool = ConnectionPool.getInstance();

            try (Connection connection = pool.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD_FROM_USER)){
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String passFromDb = resultSet.getString("password");
                        match = password.equals(passFromDb);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }
}



