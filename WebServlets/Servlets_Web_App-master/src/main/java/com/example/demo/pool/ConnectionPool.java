package com.example.demo.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {

    private static final String URL = "jdbc:postgresql://localhost:5432/JavaWebServlet";
    private static final String USER = "postgres";
    private static final String PASSWORD_DB = "123";
    private static final int EIGHT =  8;

    private static ConnectionPool instance;
    // private static final Object lock = new Object(); // Объект блокировки
    private BlockingQueue<Connection> free = new LinkedBlockingQueue<>(EIGHT);
    private BlockingQueue<Connection> used = new LinkedBlockingQueue<>(EIGHT);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }

    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) { // todo
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }


    private ConnectionPool() {
        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD_DB);

        for (int i = 0; i < EIGHT; i++) {
            try {
                Connection connection = DriverManager.getConnection(URL, properties);
                free.add(connection);
            } catch (SQLException e) {
                throw new RuntimeException("Error getting connection: " + e.getMessage(), e);
            }
        }
    }


    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = free.take();
            used.put(connection);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (connection != null && used.remove(connection)) {
                free.put(connection);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Error  releaseConnection: " + e.getMessage(), e);
        }
    }

    public void destroyPool() {
        for (int i = 0; i < EIGHT; i++) {
            try {
                free.take().close();
            } catch (SQLException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void deregisterDriver(){
        try {
            DriverManager.deregisterDriver(DriverManager.getDriver(URL));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // todo
}
