package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/test";

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            Properties prop= new Properties();

            prop.put(Environment.URL, URL);
            prop.put(Environment.USER, USER);
            prop.put(Environment.PASS, PASSWORD);
            prop.put(Environment.SHOW_SQL, true);
            prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

            configuration.addProperties(prop);
            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Connection JDBCConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

}