package jm.task.core.jdbc.dao;

import jakarta.persistence.criteria.CriteriaQuery;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.sessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        String sql = "CREATE TABLE user" +
                "   (id        bigint auto_increment," +
                "    user_name character(45) null," +
                "    last_name character(45) null," +
                "    age       tinyint       null," +
                "  PRIMARY KEY ( id ))";
        session.beginTransaction();
        try {
            session.createNativeQuery(sql, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        String sql = "DROP TABLE `user`";
        session.beginTransaction();
        try {
            session.createNativeQuery(sql, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        User user = new User(name, lastName, age);
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            System.out.println("Пользователь с именем - " + name + " сохранён!");
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();

        User user = new User();
        user.setId(id);

        try {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            Query<User> query = session.createQuery(criteriaQuery);
            users = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        List<User> users = getAllUsers();
        for (User user : users) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        }
    }
}
