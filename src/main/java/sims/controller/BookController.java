package sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public String registerBookPost(Book book, BindingResult bindingResult, Model model, HttpServletRequest request) throws IOException{

        if(bindingResult.hasErrors()){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "data binding error!");
            return Views.BOOK_CREATE;
        }

        if(bookService.getById(book.getIsbn()) != null){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "book already exist!");
            return Views.BOOK_CREATE;
        }

        User user = (User) request.getSession().getAttribute(MsgAndContext.SESSION_CONTEXT_USER);

        //Authorized user will download the file
        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/books/pdf/");

        String path  = dataDirectory + book.getFile().getOriginalFilename();

        File newFile = new File(path);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        book.getFile().transferTo(newFile);

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

    @RequestMapping(value = URLs.UPLOAD, method = RequestMethod.GET)
    public String uploadBookGet(){
        return Views.BOOK_UPLOAD;
    }

    /**
     * 借书控制逻辑
     * 1. 用户根据ISBN来申请借阅图书
     * 2. 首先查看服务器内是否有这本书，如果没有返回错误信息. 如果有转到3
     * 3. 打开新的tab，下载这本书，并生成record日志
     *
     * */
    @RequestMapping(value = URLs.DOWNLOAD + "/{isbn}")
    public String downloadBook(@PathVariable("isbn") String ISBN, Model model,
                               HttpServletRequest request,
                               HttpServletResponse response){

        Book book = bookService.getById(ISBN);
        if(book == null){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "ISBN requested!");
            return Views.BOOK_QUERY;
        }

        User user = (User) request.getSession().getAttribute(MsgAndContext.SESSION_CONTEXT_USER);

        Date date = new Date();
        Record record = new Record();
        record.setBookId(book.getIsbn());
        record.setUserId(user.getEmail());
        record.setDate(date);
        record.setRecordtype(MsgAndContext.RECORD_TYPE_BORROW);

        recordService.add(record);

        //@PathVariable("fileName") String fileName)
        String fileName = book.getBookname();
        //If user is not authorized - he should be thrown out from here itself

        //Authorized user will download the file
        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/books/pdf/");
        Path file = Paths.get(dataDirectory, fileName + ".pdf");
        if (Files.exists(file))
        {

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return Views.BOOK_SHOW;
    }
}
