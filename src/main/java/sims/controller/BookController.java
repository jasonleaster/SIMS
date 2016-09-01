package sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
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
import java.util.LinkedList;
import java.util.Map;

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
    public ModelAndView queryBookPost(Book book, Model model){
        Book bookInDb = bookService.getById(book.getIsbn());
        LinkedList<Book> books = new LinkedList<>();

        if(bookInDb != null){
            books.add(bookInDb);
        }

        if(books.size() > 0){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_BOOKS, books);
            return new ModelAndView(Views.BOOK_SHOW, (Map) model);

        }else {
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "book does not exist!");
            return new ModelAndView(Views.BOOK_SEARCH, (Map) model);
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

    /**
     * 借书控制逻辑
     * 1. 用户根据ISBN来申请借阅图书
     * 2. 首先查看图书馆内是否有这本书，如果没有返回错误信息. 如果有转到3
     * 3. 判断当前该书籍在图书馆中是否还有存货，如果没有存货，返回错误信息，
     *    提示用户，这本书被借完了等待别人归还后再来借阅。
     *    (这个地方可以在后续加功能，提醒用户有人来还这本书了，或者直接让用户参与电子排队)
     * 4. 如果图书馆内还有这本书，生成相应的借阅信息，并将图书在馆数量减一。返回正确的借阅信息。
     *
     * */
    @RequestMapping(value = URLs.BORROW + "/{isbn}")
    public String borrowBook(@PathVariable("isbn") String ISBN, HttpServletRequest request){
        Book book = bookService.getById(ISBN);
        if(book == null){
            return "Error";
        }
        int bookRemaining = book.getBookRemaining();
        if(bookRemaining <= 0){
            return "Error: there is no more book remaining. Try again later";
        }

        book.setBookRemaining(bookRemaining - 1);

        bookService.modify(book);

        User user = (User) request.getSession().getAttribute(MsgAndContext.SESSION_CONTEXT_USER);

        Date date = new Date();
        Record record = new Record();
        record.setBookId(book.getIsbn());
        record.setUserId(user.getEmail());
        record.setDate(date);
        record.setRecordtype(MsgAndContext.RECORD_TYPE_BORROW);

        recordService.add(record);

        return Views.HOME;
    }

    @RequestMapping(value = URLs.RETURN, method = RequestMethod.GET)
    public String returnBookPost(){
        return Views.BOOK_SEARCH;
    }

    /**
     * 还书控制逻辑
     * 1. 如果馆内图书数据库发现，没有这本书。提示用户，这本书之前没有注册过，跳转到图书注册页面。
     * 2. 如果馆内图书数据库有这本书的注册记录，则相应的对在馆数量加一(这个地方要控制好图书总量的逻辑)
     * 3. 生成相应的图书归还记录。
     * */
    @RequestMapping(value = URLs.RETURN, method = RequestMethod.GET)
    public String returnBookPost(String ISBN, HttpServletRequest request){
        Book book = bookService.getById(ISBN);
        if(book == null){
            return "Error";
        }
        int bookRemaining = book.getBookRemaining();
        book.setBookRemaining(bookRemaining + 1);

        bookService.modify(book);

        User user = (User) request.getSession().getAttribute(MsgAndContext.SESSION_CONTEXT_USER);
        Date date = new Date();
        Record record = new Record();
        record.setBookId(book.getIsbn());
        record.setUserId(user.getEmail());
        record.setDate(date);
        record.setRecordtype(MsgAndContext.RECORD_TYPE_RETURN);

        recordService.add(record);
        return Views.HOME;
    }
}
