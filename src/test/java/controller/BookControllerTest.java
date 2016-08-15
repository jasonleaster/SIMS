/**
 * Author   : Jason Leaster
 * Date     : 2016-08-15
 * Purpose  : Spring MVC Test Integration
 * */
package controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sims.controller.BookController;
import sims.model.Book;
import sims.util.URLs;
import sims.util.Views;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class BookControllerTest {

    @Autowired
    BookController controller;

    @Autowired
    DriverManagerDataSource dataSource;

    MockMvc mockMvc;


    private void initDB(){
        Resource resource = new ClassPathResource("schema.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(resource);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Before
    public void before(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        initDB();

        Book bookExisted = new Book();
        bookExisted.setIsbn("978-7-5086-4363-2");
        bookExisted.setAuthor("小林章");
        bookExisted.setBookname("西文字体");

        controller.getBookService().add(bookExisted);
    }

    @Test
    public void registerBookTest() throws Exception{

        String url = URLs.BOOKS + URLs.CREATE;

        mockMvc.perform(MockMvcRequestBuilders.get( url ))
        .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_CREATE))
        .andExpect(MockMvcResultMatchers.status().isOk());

        // Register a book which does not exist in DB.
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .param("isbn",          "182-788-12")
                    .param("bookname",      "Thinking in Java")
                    .param("author",        "jason")
                    .param("publisher",     "机械工业出版社")
                    .param("publisheddate", "2014-10-10")
                    .param("booktype", "programming")
                    .param("codeinlib",     "4332")
                    .param("locationinlib", "二楼")
                    .param("description",   "好书啊")
                    .param("price",         "88.8"))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SHOW))
        .andExpect(MockMvcResultMatchers.status().isOk());

        // Register a book which have existed in DB.
        // TODO XXX
    }

    @Test
    public void queryBookTest() throws Exception{
        String url = URLs.BOOKS + URLs.QUERY;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SEARCH));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .param("isbn", "182-788-12"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SHOW));

    }

    @Test
    public void deleteBookTest() throws Exception{
        String url = URLs.BOOKS + URLs.DELETE;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_DELETE));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("isbn", "182-788-12"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SHOW));

    }

    @Test
    public void modifyBookTest() throws Exception{
        String url = URLs.BOOKS + URLs.DELETE;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_DELETE));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("isbn", "182-788-12"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andExpect(MockMvcResultMatchers.view().name(Views.BOOK_SHOW));

    }


}
