package controller;

import com.google.gson.Gson;
import dto.BookDTO;
import dto.GenreBookDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import service.BookService;
import utils.JavaUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/book/*")
@MultipartConfig(fileSizeThreshold=1024*1024*10,
        maxFileSize=1024*1024*50,
        maxRequestSize=1024*1024*100)

public class BookController extends HttpServlet {
    private final BookService bookService;
    private final Gson gson;
//    private final JavaUtils javaUtils;

    public BookController() {
        this.bookService = new BookService();
        this.gson = new Gson();
//        this.javaUtils = new JavaUtils();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<BookDTO> bookDTOList = new ArrayList<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String[] index = request.getPathInfo() == null ? new String[0] : request.getPathInfo().split("/");

        if (index.length == 3 && "genre".equals(index[1])) {
            int genreId = Integer.parseInt(index[2]);
            try {
                bookDTOList = bookService.getBooksByGenre(genreId);
            } catch (SQLException | NumberFormatException exception) {
                exception.printStackTrace();
//                System.err.println("number format exception: " + exception.getMessage());
            }
        }
        else if (index.length == 2 && "genre".equals(index[1])){
            try {
                bookDTOList = bookService.getAllBooks();

                Map<String, List<String>> genreMap = new HashMap<>();
                for (BookDTO bookDTO: bookDTOList){
                    String genreName = bookDTO.getGenre().getGenreName();
                    String bookTitle = bookDTO.getTitle();

                    if (genreMap.containsKey(genreName)){
                        genreMap.get(genreName).add(bookTitle);
                    } else {
                        List<String> bookList = new ArrayList<>();
                        bookList.add(bookTitle);
                        genreMap.put(genreName, bookList);
                    }
                }
//                Map<String, List<GenreBookDTO>> genreMap = new HashMap<>();
                /*for (BookDTO bookDTO: bookDTOList){
                    String genreName = bookDTO.getGenre().getGenreName();
                    String bookTitle = bookDTO.getTitle();

                    if (genreMap.containsKey(genreName)){
                        genreMap.get(genreName).add(new GenreBookDTO(bookTitle));
                    } else {
                        List<GenreBookDTO> bookList = new ArrayList<>();
                        bookList.add(new GenreBookDTO(bookTitle));
                        genreMap.put(genreName, bookList);
                    }
                }*/

                List<Map<String, List<Map<String, String>>>> result = new ArrayList<>();
                for (Map.Entry<String, List<String>> entry: genreMap.entrySet()){
                    String genreName = entry.getKey();
                    List<String> bookTitle = entry.getValue();

                    List<Map<String,String>> booksList = new ArrayList<>();
                    for (String title: bookTitle){
                        Map<String, String> bookMap = new HashMap<>();
                        bookMap.put("bookName", title);
                        booksList.add(bookMap);
                    }

                    Map<String, List<Map<String, String>>> genreEntry = new HashMap<>();
                    genreEntry.put(genreName,booksList);
                    result.add(genreEntry);
                }
                response.getWriter().print(gson.toJson(result));
                return;

            } catch (SQLException exception) {
                exception.printStackTrace();
            }

        }
        else if (index.length > 1) {
            int bookId = Integer.parseInt(index[1]);
            try {
                BookDTO bookDTO = bookService.getBooksById(bookId);
                if (bookDTO != null) {
                    bookDTOList.add(bookDTO);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } else if (index.length == 0) {
            try {
                bookDTOList = bookService.getAllBooks();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        response.getWriter().print(gson.toJson(bookDTOList));
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        JavaUtils javaUtils = new JavaUtils();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Book book = null;
        PrintWriter writer = response.getWriter();
        book = bookService.addBook(request);
//        book.setImage(JavaUtils.fileUpload(request));
        writer.print(gson.toJson(book));

        response.getWriter().print("Success");
        System.out.println("added");

    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Book book = null;
        PrintWriter writer = response.getWriter();
        book = bookService.updateBook(request);
        writer.print(gson.toJson(book));

        response.getWriter().print("Success");
        System.out.println("updated");

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        int index = Integer.parseInt(request.getPathInfo().split("/")[1]);

        try {
            bookService.deleteBook(index);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        response.getWriter().print("Success");
        System.out.println("deleted");
    }

}
