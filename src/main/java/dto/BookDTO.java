package dto;

import model.Genre;

public class BookDTO {
    private int id;
    private String title;
    private String description;
    private String publisher;
    private String image;
    private double price;
    private Genre genre;

    public BookDTO(int id, String title, String description, String publisher, String image, double price, Genre genre) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.image = image;
        this.price = price;
        this.genre = genre;
    }

    public BookDTO(int id, String title, String description, String publisher, String image, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.image = image;
        this.price = price;
    }

    public BookDTO() {

    }

    public BookDTO(int id, String title, GenreDTO genre) {

    }

    public BookDTO(int id, String title, Genre genre) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
