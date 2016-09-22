package sims.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Book {
    @NotNull
    private String isbn;

    @NotNull
    private String bookname;

    @NotNull
    private String author;

    private Double price;

    private String publisher;

    private Date   publisheddate;

    private int booktype;

    private String codeinlib;

    private String locationinlib;

    private String description;

    private CommonsMultipartFile preface;

    private CommonsMultipartFile pdfFile;

    public enum BookType{
        CS, MACHINELEARING, NOVEL, OTHERS
    }

    public Book(){

    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.trim();
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname == null ? null : bookname.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public Date getPublisheddate() {
        return publisheddate;
    }

    public void setPublisheddate(Date publisheddate) {
        this.publisheddate = publisheddate;
    }

    public int getBooktype() {
        return booktype;
    }

    public void setBooktype(int booktype) {
        this.booktype = booktype;
    }

    public String getCodeinlib() {
        return codeinlib;
    }

    public void setCodeinlib(String codeinlib) {
        this.codeinlib = codeinlib == null ? null : codeinlib.trim();
    }

    public String getLocationinlib() {
        return locationinlib;
    }

    public void setLocationinlib(String locationinlib) {
        this.locationinlib = locationinlib == null ? null : locationinlib.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CommonsMultipartFile getPreface() {
        return preface;
    }

    public void setPreface(CommonsMultipartFile preface) {
        this.preface = preface;
    }

    public CommonsMultipartFile getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(CommonsMultipartFile pdfFile) {
        this.pdfFile = pdfFile;
    }
}