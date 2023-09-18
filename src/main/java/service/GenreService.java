package service;

import com.google.gson.Gson;
import dto.GenreDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import model.Genre;
import repository.GenreRepository;
import utils.HTTPUtils;
import utils.Validation;

import java.sql.SQLException;
import java.util.List;

public class GenreService {
    private final GenreRepository genreRepository;
    private final Gson gson;

    public GenreService() {
        this.genreRepository = new GenreRepository();
        this.gson = new Gson();
    }

    public List<Genre> getAllGenre() throws SQLException {
        List<Genre> genres = genreRepository.getAllGenre();
        return genres;
    }


    public Genre addGenre(HttpServletRequest request) throws ServletException {
        String requestBody = HTTPUtils.jsonParser(request);
        Genre genre = gson.fromJson(requestBody,Genre.class);
        if (Validation.isEmpty(genre.getGenreName())){
            throw new ServletException("Invalid genre name");
        }
        Genre genre1 =  genreRepository.addGenre(genre.getGenreName());
        genre1.setId(genre1.getId());
        genre1.setGenreName(genre1.getGenreName());
        return genre1;
    }

    public Genre updateGenre(HttpServletRequest request){
        String requestBody = HTTPUtils.jsonParser(request);
        Genre genre = gson.fromJson(requestBody,Genre.class);
        System.out.println(genre);
        return genreRepository.updateGenre(genre);
    }

    public void deleteGenre(int id) throws SQLException {
        genreRepository.deleteGenre(id);
    }
}
