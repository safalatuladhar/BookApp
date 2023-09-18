package repository;

import connection.DatabaseConnection;
import dto.BookDTO;
import dto.GenreDTO;
import model.Book;
import model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookRepository extends DatabaseConnection {

    private final GenreRepository genreRepository;

    private String query = "";

    public BookRepository() {
        this.genreRepository = new GenreRepository();
    }

    public List<BookDTO> getAllBooks() throws SQLException {
        List<BookDTO> bookList = new ArrayList<>();
        createConnection();
        query = "SELECT b.id, b.title, b.description, b.publisher, b.image, b.price, g.id as genre_id, g.genre_name as genre_name \n" +
                "FROM books b INNER JOIN genre g on b.genre_id = g.id;";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String publisher = rs.getString("publisher");
                String image = rs.getString("image");
                Double price = rs.getDouble("price");
                int genreId = rs.getInt("genre_id");
                String genreName = rs.getString("genre_name");
                Genre genre = new Genre(genreId,genreName);

                bookList.add(new BookDTO(id, title,description,publisher,image,price,genre));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return bookList;
    }

//    public List<BookDTO> getAllBooksWithGenreName() throws SQLException {
//        List<BookDTO> bookList = new ArrayList<>();
//        createConnection();
//        query = "SELECT b.id, b.title,g.id as genre_id, g.genre_name AS genre_name\n" +
//                "FROM books b INNER JOIN genre g ON b.genre_id = g.id;";
//        try {
//            Statement statement = getConnection().createStatement();
//            ResultSet rs = statement.executeQuery(query);
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String title = rs.getString("title");
//                int genreId = rs.getInt("genre_id");
//                String genreName = rs.getString("genre_name");
//                GenreDTO genre = new GenreDTO(genreId,genreName);
//                bookList.add(new BookDTO(id, title,genre));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            closeConnection();
//        }
//        return bookList;
//    }

    public List<BookDTO> getAllBooksByGenre(int genreId) throws SQLException {
        List<BookDTO> bookList = new ArrayList<>();
        Genre genre = genreRepository.getGenreById(genreId);
        createConnection();
        query = "SELECT * from books where genre_id=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setInt(1,genreId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String publisher = rs.getString("publisher");
                String image = rs.getString("image");
                Double price = rs.getDouble("price");

                bookList.add(new BookDTO(id, title,description,publisher,image,price,genre));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return bookList;
    }


    public BookDTO getAllBookById(int bookId) throws SQLException {
        createConnection();
         BookDTO bookDTO = new BookDTO();
        query = "SELECT b.id, b.title, b.description, b.publisher, b.image, b.price, g.id as genre_id, g.genre_name as genre_name \n" +
                "FROM books b INNER JOIN genre g on b.genre_id = g.id where b.id=?;";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String publisher = rs.getString("publisher");
                String image = rs.getString("image");
                Double price = rs.getDouble("price");
                int gid = rs.getInt("genre_id");
                String genreName = rs.getString("genre_name");
                Genre genre = new Genre(gid,genreName);

                bookDTO.setId(id);
                bookDTO.setTitle(title);
                bookDTO.setDescription(description);
                bookDTO.setPublisher(publisher);
                bookDTO.setImage(image);
                bookDTO.setPrice(price);
                bookDTO.setGenre(genre);
            }
        } finally {
            closeConnection();
        }
        return bookDTO;
    }

    public Book addBook(Book book){
        createConnection();

        try {
            query = "INSERT INTO books (title,description, publisher, image, price, genre_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2,book.getDescription());
            ps.setString(3, book.getPublisher());
            ps.setString(4, book.getImage());
            ps.setDouble(5,book.getPrice());
            ps.setInt(6,book.getGenreId());

             ps.executeUpdate();


            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()){
                int generatedId = generatedKeys.getInt(1);
                book.setId(generatedId);
                book.setTitle(book.getTitle());
                book.setDescription(book.getDescription());
                book.setPublisher(book.getPublisher());
                book.setImage(book.getImage());
                book.setPrice(book.getPrice());
                book.setGenreId(book.getGenreId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return book;
    }

    public Book updateBook(Book book) {
        createConnection();
        try {
            query = "UPDATE books set title=?,description=?, publisher=?, image=?, price=?, genre_id=? WHERE id=?";
            PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2,book.getDescription());
            ps.setString(3, book.getPublisher());
            ps.setString(4, book.getImage());
            ps.setDouble(5,book.getPrice());
            ps.setInt(6,book.getGenreId());
            ps.setInt(7,book.getId());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()){
                book.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return book;
    }

    public void deleteBook(int id) throws SQLException {
        createConnection();
        try {
            query = "DELETE from books where id=?";
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
