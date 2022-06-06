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

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        String sql = """
             CREATE TABLE user ( 
                id        bigint auto_increment,
                user_name character(45) null,
                last_name character(45) null,
                age       tinyint       null,
                    PRIMARY KEY ( id )
              );""";

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(sql, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE `user`";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(sql, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            System.out.println("Пользователь с именем - " + name + " сохранён!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
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
        List<User> users = getAllUsers();
        try (Session session = sessionFactory.openSession()) {
            for (User user : users) {
                session.beginTransaction();
                session.remove(user);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
