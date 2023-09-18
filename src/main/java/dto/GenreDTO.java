package dto;

import model.Book;

import java.util.List;

public class GenreDTO {

    private int id;
    private String genreName;


    public GenreDTO(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GenreDTO(String genreName) {

    }

    public GenreDTO() {

    }
//    private List<Book> books;


    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
