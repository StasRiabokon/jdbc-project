package controller;

import model.Address;
import model.User;
import service.Service;
import service.ServiceImpl;

import java.util.Date;


public class UserController {

    private static Service service;

    public static void main(String[] args) {

        service = new ServiceImpl();

        service.dropTable("Address");
        service.dropTable("Users");
        service.createTables();

        User user1 = new User(1, "Stas", "Ryabokon", new Date(),
                new Address(1, "Kyiv", "Nezhinska", 28, 1));
        User user2 = new User(2, "Adolf", "Hitler", new Date(),
                new Address(2, "Berlin", "Yugent", 15, 2));
        User updatedUser = new User(2, "Vladimir", "Putin", new Date(),
                new Address(2, "Moscow", "Uliza", 666, 2));

        System.out.println("******************************* Empty list *******************************");
        service.getAllUsers().stream().forEach(System.out::println);
        service.saveUser(user1);
        service.saveUser(user2);
        System.out.println("******************************* Added users *******************************");
        service.getAllUsers().stream().forEach(System.out::println);
        service.updateUser(updatedUser);
        System.out.println("******************************* Updated user *******************************");
        service.getAllUsers().stream().forEach(System.out::println);
        service.deleteUser(2);
        System.out.println("******************************* Deleted user *******************************");
        service.getAllUsers().stream().forEach(System.out::println);
    }
}
