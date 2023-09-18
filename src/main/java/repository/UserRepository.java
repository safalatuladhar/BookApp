package repository;

import connection.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends DatabaseConnection {
    public List<String>getUserRoles(String email){
        createConnection();
        List<String> roles = new ArrayList<>();
        String query = "select * from roles r inner join user_roles u on r.id = u.role_id inner join" +
                " users on user_id = u.user_id where users.email = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String role = rs.getString("name");
                roles.add(role);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            closeConnection();
        }
        return roles;
    }

}
