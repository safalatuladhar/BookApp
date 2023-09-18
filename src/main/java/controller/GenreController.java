package controller;

import com.google.gson.Gson;
import dto.GenreDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Genre;
import service.GenreService;
import utils.HTTPUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/genre/*")
public class GenreController extends HttpServlet {
    private final GenreService genreService;
    private final Gson gson;

    public GenreController() {
        this.genreService = new GenreService();
        this.gson = new Gson();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Genre> genreList = new ArrayList<>();

        try {
            genreList = genreService.getAllGenre();
            HTTPUtils.sendResponse(response,genreList);
        } catch (SQLException e) {
            HTTPUtils.sendErrorResponse(response,404,e.getMessage());
            e.printStackTrace();
        }
        response.getWriter().print(new Gson().toJson(genreList));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Genre genre = new Genre();
        PrintWriter writer = response.getWriter();
        genre = genreService.addGenre(request);
        writer.print(gson.toJson(genre));

        response.getWriter().print("Success");
        System.out.println("added");


    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Genre genre = null;

        PrintWriter writer = response.getWriter();
        genre = genreService.updateGenre(request);
        writer.print(gson.toJson(genre));

        response.getWriter().print("Success");
        System.out.println("updated");

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        int index = Integer.parseInt(request.getPathInfo().split("/")[1]);

        try {
            genreService.deleteGenre(index);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        response.getWriter().print("Success");
        System.out.println("deleted");
    }

}
