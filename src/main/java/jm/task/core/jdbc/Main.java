package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
//
//        userService.createUsersTable();
//
//        userService.saveUser("Andrey", "Vasilev", (byte) 25);
//        userService.saveUser("Maksim", "Andreev", (byte) 31);
//        userService.saveUser("Vadim", "Ivanov", (byte) 21);
//        userService.saveUser("Anton", "Trofimov", (byte) 25);
//
//        System.out.println();
//
//        List<User> users = userService.getAllUsers();
//        for (User user : users) {
//            System.out.println(user);
//        }

        userService.cleanUsersTable();

//        userService.dropUsersTable();
    }

}
