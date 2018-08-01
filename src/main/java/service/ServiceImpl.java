package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;

import java.util.List;

public class ServiceImpl implements Service {

    private UserDao userDao = new UserDaoImpl();

    public User saveUser(User user) {
        return userDao.saveUser(user);
    }

    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public int deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }

    @Override
    public void dropTable(String name) {
        userDao.dropTableIfExist(name);
    }

    @Override
    public void createTables() {
        userDao.createTables();
    }
}

