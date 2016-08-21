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
import sims.model.Record;
import sims.model.User;
import sims.service.BookService;
import sims.service.RecordService;
import sims.util.MsgAndContext;
import sims.util.URLs;
import sims.util.Views;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = URLs.BOOKS)
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RecordService recordService;

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
    public String registerBookPost(Book book, BindingResult bindingResult, Model model, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "data binding error!");
            return Views.BOOK_CREATE;
        }

        if(bookService.getById(book.getIsbn()) != null){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "book already exist!");
            return Views.BOOK_CREATE;
        }

        User user = (User) request.getSession().getAttribute(MsgAndContext.SESSION_CONTEXT_USER);

        //System.out.println("User: " + user.getEmail());
        bookService.add(book);
        model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_BOOK, book);
        return Views.BOOK_SHOW;
    }

    /**
     * 查询书籍
     *
     * @param
     * @return String
     */
    @RequestMapping(value = URLs.QUERY, method = RequestMethod.GET)
    public String queryBookGet(){
        return Views.BOOK_SEARCH;
    }

    @RequestMapping(value = URLs.QUERY, method = RequestMethod.POST)
    public String queryBookPost(Book book, Model model){
        Book bookInDb = bookService.getById(book.getIsbn());
        if(bookInDb != null){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_BOOK, bookInDb);
            return Views.BOOK_SHOW;
        }else {
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "book does not exist!");
            return Views.BOOK_SEARCH;
        }
    }

    @RequestMapping(value = URLs.DELETE, method = RequestMethod.GET)
    public String deleteBookGet(){
        return Views.BOOK_DELETE;
    }

    @RequestMapping(value = URLs.DELETE, method = RequestMethod.POST)
    public String deleteBookPost(Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasFieldErrors("isbn")){
            return deleteBookGet();
        }
        bookService.delete(book.getIsbn());
        model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_BOOK, null);
        model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, null);
        return Views.HOME;
    }

    @RequestMapping(value = URLs.MODIFY, method = RequestMethod.GET)
    public String modifyBookSearchGet(){
        return Views.BOOK_SEARCH;
    }

    @RequestMapping(value = URLs.MODIFY, method = RequestMethod.POST)
    public String modifyBookSearchPost(@Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasFieldErrors("isbn")){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "ISBN can not be NULL");
            return modifyBookSearchGet();
        }
        Book bookInDb = bookService.getById(book.getIsbn());
        model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_BOOK, bookInDb);
        return Views.BOOK_MODIFY;
    }

    @RequestMapping(value = URLs.MODIFY + URLs.UPDATE, method = RequestMethod.POST)
    public String modifyBookUpdatePost(@Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "form error!");
            return modifyBookSearchPost(book, bindingResult, model);
        }
        bookService.modify(book);
        model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_BOOK, book);
        return Views.BOOK_SHOW;
    }

    @RequestMapping(value = URLs.BORROW)
    public String borrowBook(String ISBN, HttpServletRequest request){
        Book book = bookService.getById(ISBN);
        User user = (User) request.getSession().getAttribute(MsgAndContext.SESSION_CONTEXT_USER);
        Record record = new Record();
        record.setBookId(book.getIsbn());
        record.setUserId(user.getEmail());

        //recordService.
        return "TODO XXX";
    }

    @RequestMapping(value = URLs.RETURN)
    public String returnBook(){
        return "XXX";
    }
}
