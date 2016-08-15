package sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sims.model.Book;
import sims.service.BookService;
import sims.util.URLs;
import sims.util.Views;

import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = URLs.BOOKS)
public class BookController {

    private BookService bookService;

    public BookService getBookService() {
        return bookService;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws ServletException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setLenient(false);
        binder.registerCustomEditor(Date.class, "publisheddate",
                new CustomDateEditor(dateFormatter, true));
    }

    /**
     * 注册新的书籍
     *
     * @param
     * @return String
     */
    @RequestMapping(value = URLs.CREATE, method = RequestMethod.GET)
    public String registerBookGet(){
        return Views.BOOK_CREATE;
    }

    @RequestMapping(value = URLs.CREATE, method = RequestMethod.POST)
    public String registerBookPost(Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return Views.BOOK_CREATE;
        }
        bookService.add(book);
        model.addAttribute("book", book);
        return Views.BOOK_SHOW;
    }

    /**
     * 查询书籍
     *
     * @param
     * @return String
     */
    @RequestMapping(value = URLs.QUERY, method = RequestMethod.GET)
    public String queryBookGet(Book book, Model model){
        return Views.BOOK_SEARCH;
    }

    @RequestMapping(value = URLs.QUERY, method = RequestMethod.POST)
    public String queryBookPost(Book book, Model model){
        Book bookInDb = bookService.getById(book.getIsbn());
        model.addAttribute("book", bookInDb);
        return Views.BOOK_SHOW;
    }

    @RequestMapping(value = URLs.DELETE, method = RequestMethod.GET)
    public String deleteBookGet(){
        return Views.BOOK_DELETE;
    }

    @RequestMapping(value = URLs.DELETE, method = RequestMethod.POST)
    public String deleteBookPost(Book book, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors("isbn")){
            return deleteBookGet();
        }
        bookService.delete(book.getIsbn());
        return Views.HOME;
    }

    @RequestMapping(value = URLs.MODIFY, method = RequestMethod.GET)
    public String modifyBookSearchGet(){
        return Views.BOOK_SEARCH;
    }

    @RequestMapping(value = URLs.MODIFY, method = RequestMethod.POST)
    public String modifyBookSearchPost(Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasFieldErrors("isbn")){
            return modifyBookSearchGet();
        }
        Book bookInDb = bookService.getById(book.getIsbn());
        model.addAttribute("book", bookInDb);
        return Views.BOOK_MODIFY;
    }

    @RequestMapping(value = URLs.MODIFY + URLs.UPDATE, method = RequestMethod.POST)
    public String modifyBookUpdatePost(Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return modifyBookSearchPost(book, bindingResult, model);
        }
        bookService.modify(book);
        model.addAttribute("book", book);
        return Views.BOOK_SHOW;
    }
}
