package sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import sims.form.BookSearchForm;
import sims.model.Book;
import sims.model.Record;
import sims.model.User;
import sims.service.BookService;
import sims.service.RecordService;
import sims.util.AttributesKey;
import sims.util.PageInfo;
import sims.util.URLs;
import sims.util.Views;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    /*Help to translate the date time string like "yyyy-mm-dd" into Date object in Java */
    @InitBinder
    protected void initBinder(WebDataBinder binder) throws ServletException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setLenient(false);
        binder.registerCustomEditor(Date.class, "publisheddate",
                new CustomDateEditor(dateFormatter, true));
    }

    @RequestMapping(value = {URLs.CREATE}, method = RequestMethod.GET)
    public String registerBookGet(){
        return Views.BOOK_CREATE;
    }

    @RequestMapping(value = URLs.CREATE, method = RequestMethod.POST)
    public String registerBookPost(Book book, BindingResult bindingResult, Model model, HttpServletRequest request) throws Exception{

        if(bindingResult.hasErrors()){
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "data binding error!");
            return Views.BOOK_CREATE;
        }

        if(bookService.getById(book.getIsbn()) != null){
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "book already exist!");
            return Views.BOOK_CREATE;
        }

        User user = (User) request.getSession().getAttribute(AttributesKey.SESSION_ATTRIBUTES_USER);

        //Authorized user will download the file
        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/static/books/pdf/");

        String path  = dataDirectory + book.getIsbn() + ".pdf";

        book.setPdfFilePath(path);

        if(book.getPdfFile() != null){
            File newFilePdf = new File(path);
            newFilePdf.createNewFile();
            //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
            book.getPdfFile().transferTo(newFilePdf);
        }

        if(book.getPreface() != null){
            path = dataDirectory + book.getIsbn() + ".png";
            book.setPdfFilePath(path);
            File newFileImage = new File(path);
            newFileImage.createNewFile();
            book.getPreface().transferTo(newFileImage);
        }

        /*
        * Generate a new record
        * */
        Date date = new Date();
        Record record = new Record();
        record.setBookId(book.getIsbn());
        record.setUserId(user.getEmail());
        record.setDate(date);
        record.setRecordtype(Record.Type.UPLOAD);

        recordService.add(record);

        //System.out.println("User: " + user.getEmail());
        try {
            bookService.add(book);
        }catch (DuplicateFormatFlagsException e){
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, e.toString());
            return Views.BOOK_CREATE;
        }

        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_BOOK, book);

        book.setViewTimes(book.getViewTimes() + 1);
        bookService.modify(book);
        return Views.BOOK_PROFILE;
    }

    @RequestMapping(value = {URLs.SEARCH})
    public String searchBook(HttpServletRequest request){
        request.getSession().setAttribute( AttributesKey.SESSION_ATTRIBUTES_BOOK_QUERY_FORM, null);
        return Views.BOOK_SEARCH;
    }


    @SuppressWarnings("rawtypes")
	@RequestMapping(value = URLs.QUERY)//, method = RequestMethod.POST)
    public String queryBookPost(BookSearchForm form, Model model,
                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                HttpServletRequest request) throws Exception{

        form = BookSearchForm.searchFormPreProcess(form);

        HttpSession session = request.getSession();
        BookSearchForm oldForm = (BookSearchForm)session.getAttribute( AttributesKey.SESSION_ATTRIBUTES_BOOK_QUERY_FORM);

        if(pageNum != null){
            form = oldForm;
        }else{
            session.setAttribute(AttributesKey.SESSION_ATTRIBUTES_BOOK_QUERY_FORM, form);
        }

        if(form.getIsbn() != null){
            return queryBookByISBN(form.getIsbn(), model);
        }

        PageInfo pageInfo;

        int pageSize = 1;
        if(pageNum == null){
            pageInfo = new PageInfo(0, pageSize, new ArrayList());
        }else{
            pageInfo = new PageInfo((pageNum.intValue() - 1) * pageSize, pageSize, new ArrayList());
        }

        pageInfo.setURL(request.getRequestURI());

        try {
            List<Book> booksInDB = bookService.pagedFuzzyQuery(form, pageInfo);

            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_BOOKS, booksInDB);
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_PAGEINFO, pageInfo);
        }catch (Exception ignore){
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "search Exception");
            return Views.BOOK_QUERY;
        }

        return Views.BOOK_RESULT_TABLE;
    }

    @RequestMapping(value = URLs.QUERY + "/{isbn}")
    public String queryBookByISBN(@PathVariable("isbn")String ISBN, Model model){

        Book bookInDb = bookService.getById(ISBN);

        if(bookInDb == null){
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "Book does not exist!");
            return Views.BOOK_SEARCH;
        }
        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_BOOK, bookInDb);

        bookInDb.setViewTimes(bookInDb.getViewTimes() + 1);
        bookService.modify(bookInDb);

        return Views.BOOK_PROFILE;
    }

    @RequestMapping(value = URLs.QUERY + "/type/{booktype}")
    public String queryBookByBookType(@PathVariable("booktype")int bookType,
                                      @RequestParam(name = "pageNum", required = false) Integer pageNum,
                                      Model model, HttpServletRequest request) throws Exception{

        BookSearchForm form = new BookSearchForm();
        form.setBooktype(bookType);
        return this.queryBookPost(form, model, pageNum, request);
    }


    @RequestMapping(value = URLs.DELETE + "/{isbn}")
    public String deleteBookPost(@PathVariable("isbn")String ISBN, Model model){

        bookService.delete(ISBN);
        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_BOOK, null);
        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, null);

        return Views.HOME;
    }

    @RequestMapping(value = URLs.MODIFY + "/{isbn}")
    public String modifyBook(@PathVariable("isbn")String ISBN, Model model){

        Book bookInDb = bookService.getById(ISBN);
        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_BOOK, bookInDb);
        return Views.BOOK_MODIFY;

    }

    @RequestMapping(value = URLs.MODIFY + URLs.UPDATE, method = RequestMethod.POST)
    public String modifyBookUpdatePost(@Valid Book book, BindingResult bindingResult, Model model, HttpServletRequest request) throws IOException{

        if(bindingResult.hasErrors()){
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "binding error");
            return modifyBook(book.getIsbn(), model);
        }

        Book oldBook = bookService.getById(book.getIsbn());

        //Authorized user will download the file
        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/static/books/pdf/");

        String path  = dataDirectory + book.getIsbn() + ".pdf";

        if( oldBook.getPdfFilePath()  == null
            || (! oldBook.getPdfFilePath().equals(book.getPdfFilePath())) ){

            book.setPdfFilePath(path);

            File newFilePdf = new File(path);
            newFilePdf.createNewFile();

            book.getPdfFile().transferTo(newFilePdf);
        }

        if( oldBook.getPrefacePath() == null
            || (! oldBook.getPrefacePath().equals(book.getPrefacePath())) ){

            path = dataDirectory + book.getIsbn() + ".png";
            book.setPdfFilePath(path);

            File newFileImage = new File(path);
            newFileImage.createNewFile();

            book.getPreface().transferTo(newFileImage);
        }

        bookService.modify(book);

        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_BOOK, book);

        return Views.BOOK_PROFILE;
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
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "ISBN requested!");
            return Views.BOOK_QUERY;
        }

        User user = (User) request.getSession().getAttribute(AttributesKey.SESSION_ATTRIBUTES_USER);

        /*
        * Generate a new record
        * */
        Date date = new Date();
        Record record = new Record();
        record.setBookId(book.getIsbn());
        record.setUserId(user.getEmail());
        record.setDate(date);
        record.setRecordtype(Record.Type.DOWNLOAD);

        recordService.add(record);

        String fileNameInDisk = book.getIsbn() + ".pdf";

        //Authorized user will download the file
        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/static/books/pdf/");
        Path file = Paths.get(dataDirectory, fileNameInDisk);
        if (Files.exists(file))
        {

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename="+ book.getBookname() + ".pdf");
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_BOOK, book);

        book.setDownloadTimes(book.getDownloadTimes() + 1);
        book.setViewTimes(book.getViewTimes() + 1);

        bookService.modify(book);

        return Views.BOOK_PROFILE;
    }
}
