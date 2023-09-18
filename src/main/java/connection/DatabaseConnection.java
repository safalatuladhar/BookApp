package connection;

import utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {

    }

    public Connection getConnection(){
        return connection;
    }

    public void createConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_app", "root", Constants.env.get("MYSQL_PW"));
                System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        if (connection !=null){
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

}
