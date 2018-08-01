package dao;

import model.User;

import java.util.List;

public interface UserDao {

    User saveUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();

    int deleteUser(Integer id);

    void dropTableIfExist(String name);

    void createTables();

}
