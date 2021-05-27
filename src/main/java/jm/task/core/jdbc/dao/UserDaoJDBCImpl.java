package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String CREATE_TABLE = "CREATE TABLE `user` (\n" +
                "  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NOT NULL,\n" +
                "  `age` TINYINT(2) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(CREATE_TABLE)){
            preparedStatement.execute();
        } catch (SQLException throwables) {
            System.out.println("Такая таблица уже существует");
        }
    }

    public void dropUsersTable() {
        final String DELETE_TABLES = "DROP TABLES user;";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(DELETE_TABLES)) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            System.out.println("Такой таблицы не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String INSERT = "INSERT INTO user (name, lastname, age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(INSERT)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Что-то пошло не так");
        }
    }

    public void removeUserById(long id) {
        final String REMOVE_USER = "DELETE FROM user WHERE id = ?";
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(REMOVE_USER)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Пользователя не существует");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new <User>LinkedList();
        final String ALL_USERS = "SELECT * FROM user";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(new User(resultSet.getString(2),resultSet.getString(3),resultSet.getByte(4)));
            }
        } catch (SQLException throwables) {
            System.out.println("Что-то пошло не так");
        }
        return users;
    }

    public void cleanUsersTable() {
        final String CLEAN_TABLE = "DELETE FROM user;";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(CLEAN_TABLE)) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            System.out.println("Что-то пошло не так");
        }
    }
}
