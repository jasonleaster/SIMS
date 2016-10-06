package sims.form;


import java.lang.reflect.Field;

public class BookSearchForm {

    private String isbn;

    private String bookname;

    private String author;

    private Double priceLowBound;

    private Double priceUpBound;

    private Integer booktype;

    public BookSearchForm(){
        priceLowBound = -1.;
        priceUpBound  = -1.;
    }

    /*
     * Set all empty String ("") into null for @BookSearchForm;
     * */
    public static BookSearchForm searchFormPreProcess(BookSearchForm form) throws Exception{
        Field[] fields = form.getClass().getDeclaredFields();
        for(Field field: fields){
            if(field.getType().equals(String.class)){
                field.setAccessible(true);
                String string = (String) field.get(form);
                if(string != null && string.length() == 0){
                    field.set(form, null);
                }
            }
        }
        return form;
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
