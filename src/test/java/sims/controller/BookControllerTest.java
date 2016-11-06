/**
 * Author   : Jason Leaster
 * Date     : 2016-08-15
 * Purpose  : Spring MVC Test Integration
 * */
package sims.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sims.model.Book;
import sims.service.BookService;
import sims.util.SupplementaryDataFactory;
import sims.util.AttributesKey;
import sims.util.URLs;
import sims.util.Views;
import sims.web.config.DataConfig;

@SuppressWarnings({"unused"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class BookControllerTest {

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    BookService bookService;

    @Autowired
    DriverManagerDataSource dataSource;

    MockMvc mockMvc;

    Book bookExisted;
    Book bookNotExisted;

    private static String ISBN_USED;
    private static String ISBN_UNUSED = "978-7-5086-4363-2";

    @Before
    public void before() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        DataConfig.initDB(dataSource);

        Book[] books = SupplementaryDataFactory.getBooks();

        for (int i = 0; i < books.length; i++){
            bookService.add(books[i]);
        }

        bookExisted = books[0];
        ISBN_USED   = books[0].getIsbn();

        bookNotExisted = new Book();
        bookNotExisted.setIsbn(ISBN_UNUSED);
        bookNotExisted.setAuthor("Jason");
        bookNotExisted.setBookname("Java Cook Book");

        bookService.add(bookExisted);
    }

    @Test
    public void registerBookTest() throws Exception{

        /**
         * 控制器逻辑:
         *   1.用户通过 url = URLs.BOOKS + URLs.CREATE 向服务器发出GET请求
         *   2.服务端返回对应的视图 view = Views.BOOK_CREATE
         * */
        String url = URLs.BOOKS + URLs.CREATE;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
        .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_CREATE))
        .andExpect(MockMvcResultMatchers.status().isOk());


        /**
         * ## 向系统注册一本书，但是这本书的ISBN与数据库中数据的ISBN冲突
         * 控制器逻辑
         * 1. 向服务端发送POST请求
         * 2. 服务端截获这个HTTP请求，判断书籍的ISBN是否存在于数据库中
         * 3. 如果书籍的ISBN于数据库中已有书籍的ISBN重复，则认为该类书籍已经注册过了
         *     将error变量添加到Spring MVC模型中， 将HTTP请求重定向到书籍注册页面，
         *     提示用户ISBN冲突。
         * 4. 返回视图 Views.BOOK_CREATE 提示用户重新修改之前的错误参数
         */
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("isbn",      bookExisted.getIsbn())
                .param("bookname", bookExisted.getBookname())
                .param("author",    bookExisted.getAuthor()))
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_CREATE))
                .andExpect(MockMvcResultMatchers.status().isOk());

        /**
         * ## 向系统注册一本书，该书不存在于数据库中
         * 正确的展示注册后的书籍信息
         */
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .param("isbn",     bookNotExisted.getIsbn())
                    .param("bookname", bookNotExisted.getBookname())
                    .param("author",   bookNotExisted.getAuthor())  )
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_BOOK))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_PROFILE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void queryBookTest() throws Exception{
        String url = URLs.BOOKS + URLs.QUERY;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SEARCH));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .param("isbn", bookExisted.getIsbn()))
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_BOOK))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_PROFILE));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("isbn", bookNotExisted.getIsbn()))
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SEARCH));


        url = URLs.BOOKS + URLs.QUERY + "/type/" + Book.BookType.OTHERS.ordinal();
        mockMvc.perform(MockMvcRequestBuilders.post(url))
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_BOOKS));


        url = URLs.BOOKS + URLs.QUERY + "/type/" + Book.BookType.CS.ordinal();
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("pageNum", "2"))
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_BOOKS))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_RESULT_TABLE));

    }


    //@Test
    public void deleteBookTest() throws Exception{
        /**
         * 1. 以form的形式提供希望删除书籍的ISBN
         * 2. 依据ISBN删除数据库中的书籍
         * 3. 再次查询之前删除过的书籍，断言该书籍不存在
         * */
        String url = URLs.BOOKS + URLs.DELETE;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_DELETE));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("isbn", bookExisted.getIsbn()))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(AttributesKey.MODEL_ATTRIBUTES_BOOK))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.HOME));

        url = URLs.BOOKS + URLs.QUERY;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SEARCH));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("isbn", bookExisted.getIsbn()))
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SEARCH));
    }

    //@Test
    public void modifyBookTest() throws Exception{
        String url = URLs.BOOKS + URLs.MODIFY;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SEARCH));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("isbn", bookExisted.getIsbn())  )
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_BOOK))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_MODIFY));

        url += URLs.UPDATE;

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("isbn",     bookExisted.getIsbn())
                .param("bookname", "newBook")
                .param("author",   "newAuthor") )
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_BOOK))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_PROFILE));

    }

    //@Test
    public void downloadBookTest() throws Exception{
        /**
         * 缺省没有对应的ISBN
         * */
        String url = URLs.BOOKS + URLs.DOWNLOAD + "/";

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_QUERY))
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG));

        /**
         * ISBN对应的书籍在数据库中不存在
         * */
        url = URLs.BOOKS + URLs.DOWNLOAD + "/" + bookNotExisted.getIsbn();

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_QUERY))
                .andExpect(MockMvcResultMatchers.model().attributeExists(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG));

        /**
         * ISBN对应的书籍存在于数据库中
         * */
        url = URLs.BOOKS + URLs.DOWNLOAD + "/" + bookExisted.getIsbn();

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.HOME))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG));
    }

}
