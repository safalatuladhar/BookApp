package model;

import java.io.Serializable;

public class Genre {
    private int id;
    private String genreName;

    public Genre(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }


    public Genre() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
