package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory sessionFactory = Util.getConnectionHibernate();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS user " +
                    "(id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT(2) NOT NULL)";

            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();

        } catch(Exception e) {
            System.out.println("Неудалось создать таблицу");
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "DROP TABLE user";

            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();
        } catch(Exception e) {
            System.out.println("Неудалось удалить таблицу");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {

             transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();

        } catch(Exception e) {
            System.out.println("Неудалось сохранить пользователя");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            transaction.commit();

        } catch(Exception e) {
            System.out.println("Неудалось удалить пользователя");
        }
    }

    @Override
    public List<User> getAllUsers() {

        List <User> users = null;
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(User.class);
            Root <User> root = cq.from(User.class);
            cq.select(root);
            Query query = session.createQuery(cq);
            users = query.getResultList();

        } catch(Exception e) {
            System.out.println("Неудалось получить список пользователей");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE from user ").executeUpdate();
            transaction.commit();
        } catch(Exception e) {
            System.out.println("Неудалось очистить таблицу пользователей");
        }
    }
}
