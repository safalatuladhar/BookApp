package service;

import com.google.gson.Gson;
import dto.BookDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import model.Book;
import model.Genre;
import repository.BookRepository;
import repository.GenreRepository;
import utils.HTTPUtils;
import utils.JavaUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final BookRepository bookRepository;
    private final Gson gson;
    private final GenreRepository genreRepository;


    public BookService() {
        this.bookRepository = new BookRepository();
        this.gson = new Gson();
        this.genreRepository = new GenreRepository();
    }

    public List<BookDTO> getAllBooks() throws SQLException {
        return bookRepository.getAllBooks();
    }

    public List<BookDTO> getBooksByGenre(int genreId) throws SQLException {
        return bookRepository.getAllBooksByGenre(genreId);
    }

    public BookDTO getBooksById(int bookId) throws SQLException {
        return bookRepository.getAllBookById(bookId);
    }

    public Book addBook(HttpServletRequest request) throws ServletException, IOException {
        String requestBody = HTTPUtils.jsonParser(request);
        Book book = gson.fromJson(requestBody, Book.class);

        Book book1 = bookRepository.addBook(book);
        book1.setId(book1.getId());
        book1.setTitle(book1.getTitle());
        book1.setDescription(book1.getDescription());
        book1.setPublisher(book1.getPublisher());
//        book1.setImage(JavaUtils.fileUpload(request));
        book1.setImage(book1.getImage());
        book1.setPrice(book1.getPrice());
        book1.setGenreId(book1.getGenreId());
        return book1;
    }

    public Book updateBook(HttpServletRequest request) throws ServletException, IOException {
        String requestBody = HTTPUtils.jsonParser(request);
        Book book = gson.fromJson(requestBody, Book.class);
        System.out.println(book);
        if (request.getPart("file")!=null){
            book.setImage(JavaUtils.fileUpload(request));
        }
        return bookRepository.updateBook(book);
    }

    public void deleteBook(int id) throws SQLException {
        bookRepository.deleteBook(id);
    }
}
