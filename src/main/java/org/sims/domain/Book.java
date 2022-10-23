package org.sims.domain;

import io.mybatis.provider.Entity;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.commons.lang3.builder.EqualsBuilder;
import java.util.Date;

@Entity.Table("books")
public class Book {

    @Entity.Column(id = true, remark = "主键", updatable = false, insertable = false)
    private String isbn;

    @Entity.Column(value = "bookname", remark = "书籍名称")
    private String bookname;

    @Entity.Column(value = "author", remark = "作者")
    private String author;

    @Entity.Column(value = "price", remark = "价格")
    private Double price;

    @Entity.Column(value = "publisher", remark = "发布者")
    private String publisher;

    @Entity.Column(value = "publisheddate", remark = "发行日期")
    private Date   publisheddate;

    @Entity.Column(value = "booktype", remark = "书籍类型")
    private int booktype;

    @Entity.Column(value = "codeinlib", remark = "编码")
    private String codeinlib;

    @Entity.Column(value = "locationinlib", remark = "地址")
    private String locationinlib;

    @Entity.Column(value = "locationinlib", remark = "描述")
    private String description;

    private CommonsMultipartFile preface; // A image file for the preface of the book

    private CommonsMultipartFile pdfFile; // The pdf file of this book

    private int downloadTimes;  // How many user have downloaded this book

    private int viewTimes;      // How many user have seen the profile of this book

    private String prefacePath;

    private String pdfFilePath;

    public enum BookType{
        CS, MACHINELEARING, NOVEL, OTHERS
    }

    public Book(){}

    @Override
    public boolean equals(Object obj) {
        Book book = (Book)obj;
        return new EqualsBuilder()
                .append(isbn,       book.isbn)
                .append(bookname,   book.bookname)
                .append(author,     book.author)
                .isEquals();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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
        this.codeinlib = codeinlib;
    }

    public String getLocationinlib() {
        return locationinlib;
    }

    public void setLocationinlib(String locationinlib) {
        this.locationinlib = locationinlib;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(int downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public int getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(int viewTimes) {
        this.viewTimes = viewTimes;
    }

    public String getPrefacePath() {
        return prefacePath;
    }

    public void setPrefacePath(String prefacePath) {
        this.prefacePath = prefacePath;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
    }
}