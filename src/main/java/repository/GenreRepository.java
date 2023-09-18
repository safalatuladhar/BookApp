package repository;

import connection.DatabaseConnection;
import dto.GenreDTO;
import model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository extends DatabaseConnection {
    private String query = "";

    public List<Genre> getAllGenre() throws SQLException {
        List<Genre> genreList = new ArrayList<>();
        createConnection();
        query = "select * from genre";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String genreName = rs.getString("genre_name");
                genreList.add(new Genre(id, genreName));
            }
        } finally {
            closeConnection();
        }
        return genreList;
    }

    public Genre getGenreById(int genreId) throws SQLException {
        createConnection();
        Genre genre = null;
        query = "select * from genre where id=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setInt(1, genreId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String genreName = rs.getString("genre_name");
                genre = new Genre(id, genreName);
            }
        } finally {
            closeConnection();
        }
        return genre;
    }

    public Genre addGenre(String name){
        createConnection();
        Genre genre = null;
        try {
            query = "INSERT INTO genre(genre_name) VALUES(?)";
            PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, genre.getId());
            ps.setString(1, name);
            ps.executeUpdate();

            ResultSet genreratedKeys = ps.getGeneratedKeys();
            if (genreratedKeys.next()){
                int generatedId = genreratedKeys.getInt(1);
                genre = new Genre();
                genre.setId(generatedId);
                genre.setGenreName(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return genre;
    }

    public Genre updateGenre(Genre genre) {
        createConnection();
        try {
            query = "UPDATE genre set genre_name=? WHERE id=?";
            PreparedStatement pst = getConnection().prepareStatement(query);
            pst.setString(1, genre.getGenreName());
            pst.setInt(2, genre.getId());
            int i = pst.executeUpdate();
            if (i==1){
                System.out.println("updated successfully");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return genre;
    }

    public void deleteGenre(int id) throws SQLException {
        createConnection();
        try {
            query = "DELETE from genre where id=?";
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setLong(1, id);
            int isDeleted = ps.executeUpdate();

            if (isDeleted == 1){
                System.out.println("deleted successfully");
            }
        }
        finally {
            closeConnection();
        }
    }


}
