package repository;

import connection.DatabaseConnection;
import model.User;
import utils.HTTPUtils;

import java.sql.*;

public class AuthRepository extends DatabaseConnection {


    public void register(User user) throws SQLException, ClassNotFoundException {
        createConnection();
        String query = "INSERT INTO users (email, password) VALUES (?,?);";
        PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try {
            ps.setString(1, user.getEmail());
            String passwordHash = HTTPUtils.encodePassword(user.getPassword());
            ps.setString(2, passwordHash);
            ps.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public User login(User userDTO) {
        createConnection();
        User user = null;
        String query = "SELECT * from users where email=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1,userDTO.getEmail());
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                user = new User(id,email,password);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            closeConnection();
        }
        return user;
    }
}
