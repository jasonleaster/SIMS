package sims.controller.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sims.model.Book;
import sims.util.URLs;

@RestController
@RequestMapping(value = URLs.API + URLs.BOOKS)
public class RestfulBookController {

    @RequestMapping(value = URLs.QUERY)
    public Book queryByBookAuthor(){
        return null;
    }
}
