package dao;

import model.Address;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static Connection connection;

    @Override
    public void dropTableIfExist(String name) {
        connection = getConnection();
        String sql = "DROP TABLE IF EXISTS " + name;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void createTables() {
        connection = getConnection();
        String users = "CREATE TABLE userDB.Users(\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  firstName VARCHAR(30) NOT NULL,\n" +
                "  lastName varchar(30) NOT NULL,\n" +
                "  birth DATE NOT NULL,\n" +
                "  primary key (id)\n" +
                ");";

        String address = "CREATE TABLE userDB.Address(\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  city VARCHAR(30) NOT NULL,\n" +
                "  street varchar(30) NOT NULL,\n" +
                "  house INT NOT NULL,\n" +
                "  userId INT NOT NULL,\n" +
                "  primary key (id),\n" +
                "  foreign key (userId) REFERENCES Users(id)\n" +
                ");\n";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(users);
            statement.executeUpdate(address);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public User saveUser(User user) {
        connection = getConnection();
        String sql = "INSERT INTO userDB.Users(id, firstName, lastName, birth) VALUES(?, ?, ?, ?)";
        String sqlAdr = "INSERT INTO userDB.Address(id, city, street, house, userId) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement statementUser = null;
        try {
            statementUser = connection.prepareStatement(sql);
            statementUser.setInt(1, user.getId());
            statementUser.setString(2, user.getFirstName());
            statementUser.setString(3, user.getLastName());
            statementUser.setDate(4, new Date(user.getBirth().getTime()));
            statementUser.executeUpdate();

            statementUser = connection.prepareStatement(sqlAdr);
            statementUser.setInt(1, user.getAddress().getId());
            statementUser.setString(2, user.getAddress().getCity());
            statementUser.setString(3, user.getAddress().getStreet());
            statementUser.setInt(4, user.getAddress().getHouse());
            statementUser.setInt(5, user.getId());
            statementUser.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statementUser != null) statementUser.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        connection = getConnection();
        String sql = "UPDATE userDB.Users SET firstName = ?, lastName = ?, birth = ? WHERE id = ?";
        String sqlAdr = "UPDATE userDB.Address SET city = ?, street = ?, house = ? WHERE userId = ?";
        PreparedStatement statementUser = null;
        try {

            statementUser = connection.prepareStatement(sqlAdr);
            statementUser.setString(1, user.getAddress().getCity());
            statementUser.setString(2, user.getAddress().getStreet());
            statementUser.setInt(3, user.getAddress().getHouse());
            statementUser.setInt(4, user.getId());
            statementUser.executeUpdate();

            statementUser = connection.prepareStatement(sql);
            statementUser.setString(1, user.getFirstName());
            statementUser.setString(2, user.getLastName());
            statementUser.setDate(3, new Date(user.getBirth().getTime()));
            statementUser.setInt(4, user.getId());
            statementUser.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statementUser != null) statementUser.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        connection = getConnection();
        String sql = "SELECT u.id, u.firstName, u.lastName, u.birth, a.id, a.city, a.street, a.house, a.userId " +
                "FROM userDB.Users u " +
                "LEFT JOIN userDB.Address a ON u.id=a.userId";
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt("u.id"), rs.getString("u.firstName"),
                        rs.getString("u.lastName"), rs.getDate("u.birth"),
                        new Address(rs.getInt("a.id"), rs.getString("a.city"),
                                rs.getString("a.street"), rs.getInt("a.house"),
                                rs.getInt("a.userId"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public int deleteUser(Integer id) {
        connection = getConnection();
        String sqlAdr = "DELETE FROM userDB.Address WHERE userId=?";
        String sql = "DELETE FROM userDB.Users WHERE id=?";
        PreparedStatement statement = null;
        int rows = 0;
        try {
            statement = connection.prepareStatement(sqlAdr);
            statement.setInt(1, id);
            statement.executeUpdate();

            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return rows;
    }

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/userDB";
        String name = "stas";
        String password = "$tasRyabokon97";
        try {
            return DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
