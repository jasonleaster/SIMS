package sims.form;


public class BookSearchForm {

    private String isbn;

    private String bookname;

    private String author;

    private Double priceLowBound;

    private Double priceUpBound;

    private int booktype;

    public BookSearchForm(){

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

    public Double getPriceLowBound() {
        return priceLowBound;
    }

    public void setPriceLowBound(Double priceLowBound) {
        this.priceLowBound = priceLowBound;
    }

    public Double getPriceUpBound() {
        return priceUpBound;
    }

    public void setPriceUpBound(Double priceUpBound) {
        this.priceUpBound = priceUpBound;
    }

    public int getBooktype() {
        return booktype;
    }

    public void setBooktype(int booktype) {
        this.booktype = booktype;
    }
}
