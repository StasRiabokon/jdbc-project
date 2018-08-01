package service;

import model.User;

import java.util.List;

public interface Service {

    User saveUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();

    int deleteUser(Integer id);

    void dropTable(String name);

    void createTables();
}
