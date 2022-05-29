package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Statement statement;

    public UserDaoJDBCImpl() {
        try {
            Util util = new Util();
            Connection connection = util.createConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("�� ������� ���������� � DB");
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE users" +
                "   (id       int auto_increment," +
                "    userName character(45) null," +
                "    lastName character(45) null," +
                "    age      int           null," +
                "  PRIMARY KEY ( id ))";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("�� ������� ������� �������");
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE users";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("�� ������� ������� �������");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(`userName`, `lastName`, `age`) VALUES('" + name + "',  '" + lastName + "', " + age + ");";
        try {
            statement.execute(sql);
            System.out.println("������������ � ������ - " + name + " �������� � ���� ������");
        } catch (SQLException e) {
            System.err.println("�� ������� ��������� ������������");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM `users` WHERE id = " + id;
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("�� ������� ������� ������������ �� �������");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";
        try {
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                User user = new User();

                user.setId(rs.getLong("id"));
                user.setName(rs.getString("userName"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));

                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("�� ������� ������� ������ � �������������");
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("�� ������� �������� �������");
        }
    }
}
