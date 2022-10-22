package org.sims.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.DefaultFileItem;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import org.sims.dao.BookMapper;
import org.sims.exception.DuplicatedPrimaryKeyException;
import org.sims.form.BookSearchForm;
import org.sims.model.Book;
import org.sims.util.PageInfo;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    /**
     * Method under test: {@link BookServiceImpl#init()}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testInit() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.service.impl.BookServiceImpl.init(BookServiceImpl.java:37)
        //   See https://diff.blue/R013 to resolve this issue.

        (new BookServiceImpl()).init();
    }

    /**
     * Method under test: {@link BookServiceImpl#init()}
     */
    @Test
    public void testInit2() {
        when(bookMapper.countAll()).thenReturn(3);
        bookServiceImpl.init();
        verify(bookMapper).countAll();
    }

    /**
     * Method under test: {@link BookServiceImpl#getById(String)}
     */
    @Test
    public void testGetById() {
        assertNull((new BookServiceImpl()).getById("42"));
    }

    /**
     * Method under test: {@link BookServiceImpl#getById(String)}
     */
    @Test
    public void testGetById2() {
        DefaultFileItem defaultFileItem = mock(DefaultFileItem.class);
        when(defaultFileItem.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile = new CommonsMultipartFile(defaultFileItem);
        DefaultFileItem defaultFileItem1 = mock(DefaultFileItem.class);
        when(defaultFileItem1.getSize()).thenReturn(3L);
        CommonsMultipartFile preface = new CommonsMultipartFile(defaultFileItem1);
        Book book = mock(Book.class);
        doNothing().when(book).setPreface((CommonsMultipartFile) any());
        doNothing().when(book).setPrefacePath((String) any());
        doNothing().when(book).setPrice((Double) any());
        doNothing().when(book).setPublisheddate((Date) any());
        doNothing().when(book).setPublisher((String) any());
        doNothing().when(book).setViewTimes(anyInt());
        doNothing().when(book).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book).setPdfFilePath((String) any());
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookname((String) any());
        doNothing().when(book).setBooktype(anyInt());
        doNothing().when(book).setCodeinlib((String) any());
        doNothing().when(book).setDescription((String) any());
        doNothing().when(book).setDownloadTimes(anyInt());
        doNothing().when(book).setIsbn((String) any());
        doNothing().when(book).setLocationinlib((String) any());
        book.setAuthor("JaneDoe");
        book.setBookname("Bookname");
        book.setBooktype(1);
        book.setCodeinlib("Codeinlib");
        book.setDescription("The characteristics of someone or something");
        book.setDownloadTimes(1);
        book.setIsbn("Isbn");
        book.setLocationinlib("Locationinlib");
        book.setPdfFile(pdfFile);
        book.setPdfFilePath("/directory/foo.txt");
        book.setPreface(preface);
        book.setPrefacePath("Preface Path");
        book.setPrice(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPublisheddate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        book.setPublisher("Publisher");
        book.setViewTimes(1);
        when(bookMapper.selectByPrimaryKey((String) any())).thenReturn(book);
        bookServiceImpl.getById("42");
        verify(bookMapper).selectByPrimaryKey((String) any());
        verify(book).setAuthor((String) any());
        verify(book).setBookname((String) any());
        verify(book).setBooktype(anyInt());
        verify(book).setCodeinlib((String) any());
        verify(book).setDescription((String) any());
        verify(book).setDownloadTimes(anyInt());
        verify(book).setIsbn((String) any());
        verify(book).setLocationinlib((String) any());
        verify(book).setPdfFile((CommonsMultipartFile) any());
        verify(book).setPdfFilePath((String) any());
        verify(book).setPreface((CommonsMultipartFile) any());
        verify(book).setPrefacePath((String) any());
        verify(book).setPrice((Double) any());
        verify(book).setPublisheddate((Date) any());
        verify(book).setPublisher((String) any());
        verify(book).setViewTimes(anyInt());
        verify(defaultFileItem).getSize();
        verify(defaultFileItem1).getSize();
    }

    /**
     * Method under test: {@link BookServiceImpl#pagedFuzzyQuery(BookSearchForm, PageInfo)}
     */
    @Test
    public void testPagedFuzzyQuery() throws Exception {
        BookServiceImpl bookServiceImpl = new BookServiceImpl();

        BookSearchForm bookSearchForm = new BookSearchForm();
        bookSearchForm.setAuthor("JaneDoe");
        bookSearchForm.setBookname("Bookname");
        bookSearchForm.setBooktype(1);
        bookSearchForm.setIsbn("Isbn");
        bookSearchForm.setPriceLowBound(10.0d);
        bookSearchForm.setPriceUpBound(10.0d);
        assertEquals(1, bookServiceImpl.pagedFuzzyQuery(bookSearchForm, new PageInfo()).size());
    }

    /**
     * Method under test: {@link BookServiceImpl#pagedFuzzyQuery(BookSearchForm, PageInfo)}
     */
    @Test
    public void testPagedFuzzyQuery2() throws Exception {
        BookServiceImpl bookServiceImpl = new BookServiceImpl();

        BookSearchForm bookSearchForm = new BookSearchForm();
        bookSearchForm.setAuthor("JaneDoe");
        bookSearchForm.setBookname("Bookname");
        bookSearchForm.setBooktype(1);
        bookSearchForm.setIsbn("Isbn");
        bookSearchForm.setPriceLowBound(10.0d);
        bookSearchForm.setPriceUpBound(10.0d);
        assertEquals(1, bookServiceImpl.pagedFuzzyQuery(bookSearchForm, null).size());
    }

    /**
     * Method under test: {@link BookServiceImpl#pagedFuzzyQuery(BookSearchForm, PageInfo)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testPagedFuzzyQuery3() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.service.impl.BookServiceImpl.pagedFuzzyQuery(BookServiceImpl.java:81)
        //   See https://diff.blue/R013 to resolve this issue.

        BookServiceImpl bookServiceImpl = new BookServiceImpl();

        BookSearchForm bookSearchForm = new BookSearchForm();
        bookSearchForm.setAuthor("JaneDoe");
        bookSearchForm.setBookname("Bookname");
        bookSearchForm.setBooktype(1);
        bookSearchForm.setIsbn(null);
        bookSearchForm.setPriceLowBound(10.0d);
        bookSearchForm.setPriceUpBound(10.0d);
        bookServiceImpl.pagedFuzzyQuery(bookSearchForm, new PageInfo());
    }

    /**
     * Method under test: {@link BookServiceImpl#pagedFuzzyQuery(BookSearchForm, PageInfo)}
     */
    @Test
    public void testPagedFuzzyQuery4() throws Exception {
        DefaultFileItem defaultFileItem = mock(DefaultFileItem.class);
        when(defaultFileItem.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile = new CommonsMultipartFile(defaultFileItem);
        DefaultFileItem defaultFileItem1 = mock(DefaultFileItem.class);
        when(defaultFileItem1.getSize()).thenReturn(3L);
        CommonsMultipartFile preface = new CommonsMultipartFile(defaultFileItem1);
        Book book = mock(Book.class);
        doNothing().when(book).setPreface((CommonsMultipartFile) any());
        doNothing().when(book).setPrefacePath((String) any());
        doNothing().when(book).setPrice((Double) any());
        doNothing().when(book).setPublisheddate((Date) any());
        doNothing().when(book).setPublisher((String) any());
        doNothing().when(book).setViewTimes(anyInt());
        doNothing().when(book).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book).setPdfFilePath((String) any());
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookname((String) any());
        doNothing().when(book).setBooktype(anyInt());
        doNothing().when(book).setCodeinlib((String) any());
        doNothing().when(book).setDescription((String) any());
        doNothing().when(book).setDownloadTimes(anyInt());
        doNothing().when(book).setIsbn((String) any());
        doNothing().when(book).setLocationinlib((String) any());
        book.setAuthor("JaneDoe");
        book.setBookname("Bookname");
        book.setBooktype(1);
        book.setCodeinlib("Codeinlib");
        book.setDescription("The characteristics of someone or something");
        book.setDownloadTimes(1);
        book.setIsbn("Isbn");
        book.setLocationinlib("Locationinlib");
        book.setPdfFile(pdfFile);
        book.setPdfFilePath("/directory/foo.txt");
        book.setPreface(preface);
        book.setPrefacePath("Preface Path");
        book.setPrice(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPublisheddate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        book.setPublisher("Publisher");
        book.setViewTimes(1);
        when(bookMapper.selectByPrimaryKey((String) any())).thenReturn(book);

        BookSearchForm bookSearchForm = new BookSearchForm();
        bookSearchForm.setAuthor("JaneDoe");
        bookSearchForm.setBookname("Bookname");
        bookSearchForm.setBooktype(1);
        bookSearchForm.setIsbn("Isbn");
        bookSearchForm.setPriceLowBound(10.0d);
        bookSearchForm.setPriceUpBound(10.0d);
        assertEquals(1, bookServiceImpl.pagedFuzzyQuery(bookSearchForm, new PageInfo()).size());
        verify(bookMapper).selectByPrimaryKey((String) any());
        verify(book).setAuthor((String) any());
        verify(book).setBookname((String) any());
        verify(book).setBooktype(anyInt());
        verify(book).setCodeinlib((String) any());
        verify(book).setDescription((String) any());
        verify(book).setDownloadTimes(anyInt());
        verify(book).setIsbn((String) any());
        verify(book).setLocationinlib((String) any());
        verify(book).setPdfFile((CommonsMultipartFile) any());
        verify(book).setPdfFilePath((String) any());
        verify(book).setPreface((CommonsMultipartFile) any());
        verify(book).setPrefacePath((String) any());
        verify(book).setPrice((Double) any());
        verify(book).setPublisheddate((Date) any());
        verify(book).setPublisher((String) any());
        verify(book).setViewTimes(anyInt());
        verify(defaultFileItem).getSize();
        verify(defaultFileItem1).getSize();
    }

    /**
     * Method under test: {@link BookServiceImpl#pagedFuzzyQuery(BookSearchForm, PageInfo)}
     */
    @Test
    public void testPagedFuzzyQuery5() throws Exception {
        DefaultFileItem defaultFileItem = mock(DefaultFileItem.class);
        when(defaultFileItem.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile = new CommonsMultipartFile(defaultFileItem);
        DefaultFileItem defaultFileItem1 = mock(DefaultFileItem.class);
        when(defaultFileItem1.getSize()).thenReturn(3L);
        CommonsMultipartFile preface = new CommonsMultipartFile(defaultFileItem1);
        Book book = mock(Book.class);
        doNothing().when(book).setPreface((CommonsMultipartFile) any());
        doNothing().when(book).setPrefacePath((String) any());
        doNothing().when(book).setPrice((Double) any());
        doNothing().when(book).setPublisheddate((Date) any());
        doNothing().when(book).setPublisher((String) any());
        doNothing().when(book).setViewTimes(anyInt());
        doNothing().when(book).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book).setPdfFilePath((String) any());
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookname((String) any());
        doNothing().when(book).setBooktype(anyInt());
        doNothing().when(book).setCodeinlib((String) any());
        doNothing().when(book).setDescription((String) any());
        doNothing().when(book).setDownloadTimes(anyInt());
        doNothing().when(book).setIsbn((String) any());
        doNothing().when(book).setLocationinlib((String) any());
        book.setAuthor("JaneDoe");
        book.setBookname("Bookname");
        book.setBooktype(1);
        book.setCodeinlib("Codeinlib");
        book.setDescription("The characteristics of someone or something");
        book.setDownloadTimes(1);
        book.setIsbn("Isbn");
        book.setLocationinlib("Locationinlib");
        book.setPdfFile(pdfFile);
        book.setPdfFilePath("/directory/foo.txt");
        book.setPreface(preface);
        book.setPrefacePath("Preface Path");
        book.setPrice(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPublisheddate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        book.setPublisher("Publisher");
        book.setViewTimes(1);
        when(bookMapper.selectItemCount((Map) any())).thenReturn(3);
        when(bookMapper.selectFuzzy((Map) any())).thenReturn(new ArrayList<>());
        when(bookMapper.selectByPrimaryKey((String) any())).thenReturn(book);
        BookSearchForm bookSearchForm = mock(BookSearchForm.class);
        when(bookSearchForm.getIsbn()).thenReturn("Isbn");
        doNothing().when(bookSearchForm).setAuthor((String) any());
        doNothing().when(bookSearchForm).setBookname((String) any());
        doNothing().when(bookSearchForm).setBooktype(anyInt());
        doNothing().when(bookSearchForm).setIsbn((String) any());
        doNothing().when(bookSearchForm).setPriceLowBound((Double) any());
        doNothing().when(bookSearchForm).setPriceUpBound((Double) any());
        bookSearchForm.setAuthor("JaneDoe");
        bookSearchForm.setBookname("Bookname");
        bookSearchForm.setBooktype(1);
        bookSearchForm.setIsbn("Isbn");
        bookSearchForm.setPriceLowBound(10.0d);
        bookSearchForm.setPriceUpBound(10.0d);
        assertEquals(1, bookServiceImpl.pagedFuzzyQuery(bookSearchForm, null).size());
        verify(bookMapper).selectByPrimaryKey((String) any());
        verify(book).setAuthor((String) any());
        verify(book).setBookname((String) any());
        verify(book).setBooktype(anyInt());
        verify(book).setCodeinlib((String) any());
        verify(book).setDescription((String) any());
        verify(book).setDownloadTimes(anyInt());
        verify(book).setIsbn((String) any());
        verify(book).setLocationinlib((String) any());
        verify(book).setPdfFile((CommonsMultipartFile) any());
        verify(book).setPdfFilePath((String) any());
        verify(book).setPreface((CommonsMultipartFile) any());
        verify(book).setPrefacePath((String) any());
        verify(book).setPrice((Double) any());
        verify(book).setPublisheddate((Date) any());
        verify(book).setPublisher((String) any());
        verify(book).setViewTimes(anyInt());
        verify(defaultFileItem).getSize();
        verify(defaultFileItem1).getSize();
        verify(bookSearchForm, atLeast(1)).getIsbn();
        verify(bookSearchForm).setAuthor((String) any());
        verify(bookSearchForm).setBookname((String) any());
        verify(bookSearchForm).setBooktype(anyInt());
        verify(bookSearchForm).setIsbn((String) any());
        verify(bookSearchForm).setPriceLowBound((Double) any());
        verify(bookSearchForm).setPriceUpBound((Double) any());
    }

    /**
     * Method under test: {@link BookServiceImpl#pagedFuzzyQuery(BookSearchForm, PageInfo)}
     */
    @Test
    public void testPagedFuzzyQuery6() throws Exception {
        DefaultFileItem defaultFileItem = mock(DefaultFileItem.class);
        when(defaultFileItem.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile = new CommonsMultipartFile(defaultFileItem);
        DefaultFileItem defaultFileItem1 = mock(DefaultFileItem.class);
        when(defaultFileItem1.getSize()).thenReturn(3L);
        CommonsMultipartFile preface = new CommonsMultipartFile(defaultFileItem1);
        Book book = mock(Book.class);
        doNothing().when(book).setPreface((CommonsMultipartFile) any());
        doNothing().when(book).setPrefacePath((String) any());
        doNothing().when(book).setPrice((Double) any());
        doNothing().when(book).setPublisheddate((Date) any());
        doNothing().when(book).setPublisher((String) any());
        doNothing().when(book).setViewTimes(anyInt());
        doNothing().when(book).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book).setPdfFilePath((String) any());
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookname((String) any());
        doNothing().when(book).setBooktype(anyInt());
        doNothing().when(book).setCodeinlib((String) any());
        doNothing().when(book).setDescription((String) any());
        doNothing().when(book).setDownloadTimes(anyInt());
        doNothing().when(book).setIsbn((String) any());
        doNothing().when(book).setLocationinlib((String) any());
        book.setAuthor("JaneDoe");
        book.setBookname("Bookname");
        book.setBooktype(1);
        book.setCodeinlib("Codeinlib");
        book.setDescription("The characteristics of someone or something");
        book.setDownloadTimes(1);
        book.setIsbn("Isbn");
        book.setLocationinlib("Locationinlib");
        book.setPdfFile(pdfFile);
        book.setPdfFilePath("/directory/foo.txt");
        book.setPreface(preface);
        book.setPrefacePath("Preface Path");
        book.setPrice(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPublisheddate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        book.setPublisher("Publisher");
        book.setViewTimes(1);
        when(bookMapper.selectItemCount((Map) any())).thenReturn(3);
        ArrayList<Book> bookList = new ArrayList<>();
        when(bookMapper.selectFuzzy((Map) any())).thenReturn(bookList);
        when(bookMapper.selectByPrimaryKey((String) any())).thenReturn(book);
        BookSearchForm bookSearchForm = mock(BookSearchForm.class);
        when(bookSearchForm.getIsbn()).thenReturn(null);
        doNothing().when(bookSearchForm).setAuthor((String) any());
        doNothing().when(bookSearchForm).setBookname((String) any());
        doNothing().when(bookSearchForm).setBooktype(anyInt());
        doNothing().when(bookSearchForm).setIsbn((String) any());
        doNothing().when(bookSearchForm).setPriceLowBound((Double) any());
        doNothing().when(bookSearchForm).setPriceUpBound((Double) any());
        bookSearchForm.setAuthor("JaneDoe");
        bookSearchForm.setBookname("Bookname");
        bookSearchForm.setBooktype(1);
        bookSearchForm.setIsbn("Isbn");
        bookSearchForm.setPriceLowBound(10.0d);
        bookSearchForm.setPriceUpBound(10.0d);
        List<Book> actualPagedFuzzyQueryResult = bookServiceImpl.pagedFuzzyQuery(bookSearchForm, null);
        assertSame(bookList, actualPagedFuzzyQueryResult);
        assertTrue(actualPagedFuzzyQueryResult.isEmpty());
        verify(bookMapper).selectItemCount((Map) any());
        verify(bookMapper).selectFuzzy((Map) any());
        verify(book).setAuthor((String) any());
        verify(book).setBookname((String) any());
        verify(book).setBooktype(anyInt());
        verify(book).setCodeinlib((String) any());
        verify(book).setDescription((String) any());
        verify(book).setDownloadTimes(anyInt());
        verify(book).setIsbn((String) any());
        verify(book).setLocationinlib((String) any());
        verify(book).setPdfFile((CommonsMultipartFile) any());
        verify(book).setPdfFilePath((String) any());
        verify(book).setPreface((CommonsMultipartFile) any());
        verify(book).setPrefacePath((String) any());
        verify(book).setPrice((Double) any());
        verify(book).setPublisheddate((Date) any());
        verify(book).setPublisher((String) any());
        verify(book).setViewTimes(anyInt());
        verify(defaultFileItem).getSize();
        verify(defaultFileItem1).getSize();
        verify(bookSearchForm).getIsbn();
        verify(bookSearchForm).setAuthor((String) any());
        verify(bookSearchForm).setBookname((String) any());
        verify(bookSearchForm).setBooktype(anyInt());
        verify(bookSearchForm).setIsbn((String) any());
        verify(bookSearchForm).setPriceLowBound((Double) any());
        verify(bookSearchForm).setPriceUpBound((Double) any());
    }

    /**
     * Method under test: {@link BookServiceImpl#add(Book)}
     */
    @Test
    public void testAdd() throws Exception {
        BookServiceImpl bookServiceImpl = new BookServiceImpl();
        bookServiceImpl.add(null);
        assertNull(bookServiceImpl.getBookMapper());
    }

    /**
     * Method under test: {@link BookServiceImpl#add(Book)}
     */
    @Test
    public void testAdd2() throws Exception {
        DefaultFileItem defaultFileItem = mock(DefaultFileItem.class);
        when(defaultFileItem.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile = new CommonsMultipartFile(defaultFileItem);
        DefaultFileItem defaultFileItem1 = mock(DefaultFileItem.class);
        when(defaultFileItem1.getSize()).thenReturn(3L);
        CommonsMultipartFile preface = new CommonsMultipartFile(defaultFileItem1);
        Book book = mock(Book.class);
        doNothing().when(book).setPreface((CommonsMultipartFile) any());
        doNothing().when(book).setPrefacePath((String) any());
        doNothing().when(book).setPrice((Double) any());
        doNothing().when(book).setPublisheddate((Date) any());
        doNothing().when(book).setPublisher((String) any());
        doNothing().when(book).setViewTimes(anyInt());
        doNothing().when(book).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book).setPdfFilePath((String) any());
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookname((String) any());
        doNothing().when(book).setBooktype(anyInt());
        doNothing().when(book).setCodeinlib((String) any());
        doNothing().when(book).setDescription((String) any());
        doNothing().when(book).setDownloadTimes(anyInt());
        doNothing().when(book).setIsbn((String) any());
        doNothing().when(book).setLocationinlib((String) any());
        book.setAuthor("JaneDoe");
        book.setBookname("Bookname");
        book.setBooktype(1);
        book.setCodeinlib("Codeinlib");
        book.setDescription("The characteristics of someone or something");
        book.setDownloadTimes(1);
        book.setIsbn("Isbn");
        book.setLocationinlib("Locationinlib");
        book.setPdfFile(pdfFile);
        book.setPdfFilePath("/directory/foo.txt");
        book.setPreface(preface);
        book.setPrefacePath("Preface Path");
        book.setPrice(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPublisheddate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        book.setPublisher("Publisher");
        book.setViewTimes(1);
        when(bookMapper.insert((Book) any())).thenReturn(1);
        when(bookMapper.selectByPrimaryKey((String) any())).thenReturn(book);
        DefaultFileItem defaultFileItem2 = mock(DefaultFileItem.class);
        when(defaultFileItem2.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile1 = new CommonsMultipartFile(defaultFileItem2);
        DefaultFileItem defaultFileItem3 = mock(DefaultFileItem.class);
        when(defaultFileItem3.getSize()).thenReturn(3L);
        CommonsMultipartFile preface1 = new CommonsMultipartFile(defaultFileItem3);
        Book book1 = mock(Book.class);
        when(book1.getIsbn()).thenReturn("Isbn");
        doNothing().when(book1).setPreface((CommonsMultipartFile) any());
        doNothing().when(book1).setPrefacePath((String) any());
        doNothing().when(book1).setPrice((Double) any());
        doNothing().when(book1).setPublisheddate((Date) any());
        doNothing().when(book1).setPublisher((String) any());
        doNothing().when(book1).setViewTimes(anyInt());
        doNothing().when(book1).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book1).setPdfFilePath((String) any());
        doNothing().when(book1).setAuthor((String) any());
        doNothing().when(book1).setBookname((String) any());
        doNothing().when(book1).setBooktype(anyInt());
        doNothing().when(book1).setCodeinlib((String) any());
        doNothing().when(book1).setDescription((String) any());
        doNothing().when(book1).setDownloadTimes(anyInt());
        doNothing().when(book1).setIsbn((String) any());
        doNothing().when(book1).setLocationinlib((String) any());
        book1.setAuthor("JaneDoe");
        book1.setBookname("Bookname");
        book1.setBooktype(1);
        book1.setCodeinlib("Codeinlib");
        book1.setDescription("The characteristics of someone or something");
        book1.setDownloadTimes(1);
        book1.setIsbn("Isbn");
        book1.setLocationinlib("Locationinlib");
        book1.setPdfFile(pdfFile1);
        book1.setPdfFilePath("/directory/foo.txt");
        book1.setPreface(preface1);
        book1.setPrefacePath("Preface Path");
        book1.setPrice(10.0d);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        book1.setPublisheddate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        book1.setPublisher("Publisher");
        book1.setViewTimes(1);
        thrown.expect(DuplicatedPrimaryKeyException.class);
        bookServiceImpl.add(book1);
        verify(bookMapper).selectByPrimaryKey((String) any());
        verify(book).setAuthor((String) any());
        verify(book).setBookname((String) any());
        verify(book).setBooktype(anyInt());
        verify(book).setCodeinlib((String) any());
        verify(book).setDescription((String) any());
        verify(book).setDownloadTimes(anyInt());
        verify(book).setIsbn((String) any());
        verify(book).setLocationinlib((String) any());
        verify(book).setPdfFile((CommonsMultipartFile) any());
        verify(book).setPdfFilePath((String) any());
        verify(book).setPreface((CommonsMultipartFile) any());
        verify(book).setPrefacePath((String) any());
        verify(book).setPrice((Double) any());
        verify(book).setPublisheddate((Date) any());
        verify(book).setPublisher((String) any());
        verify(book).setViewTimes(anyInt());
        verify(defaultFileItem).getSize();
        verify(defaultFileItem1).getSize();
        verify(book1).getIsbn();
        verify(book1).setAuthor((String) any());
        verify(book1).setBookname((String) any());
        verify(book1).setBooktype(anyInt());
        verify(book1).setCodeinlib((String) any());
        verify(book1).setDescription((String) any());
        verify(book1).setDownloadTimes(anyInt());
        verify(book1).setIsbn((String) any());
        verify(book1).setLocationinlib((String) any());
        verify(book1).setPdfFile((CommonsMultipartFile) any());
        verify(book1).setPdfFilePath((String) any());
        verify(book1).setPreface((CommonsMultipartFile) any());
        verify(book1).setPrefacePath((String) any());
        verify(book1).setPrice((Double) any());
        verify(book1).setPublisheddate((Date) any());
        verify(book1).setPublisher((String) any());
        verify(book1).setViewTimes(anyInt());
        verify(defaultFileItem2).getSize();
        verify(defaultFileItem3).getSize();
    }

    /**
     * Method under test: {@link BookServiceImpl#delete(String)}
     */
    @Test
    public void testDelete() {
        BookServiceImpl bookServiceImpl = new BookServiceImpl();
        bookServiceImpl.delete("42");
        assertNull(bookServiceImpl.getBookMapper());
    }

    /**
     * Method under test: {@link BookServiceImpl#delete(String)}
     */
    @Test
    public void testDelete2() {
        BookServiceImpl bookServiceImpl = new BookServiceImpl();
        bookServiceImpl.delete(null);
        assertNull(bookServiceImpl.getBookMapper());
    }

    /**
     * Method under test: {@link BookServiceImpl#delete(String)}
     */
    @Test
    public void testDelete3() {
        DefaultFileItem defaultFileItem = mock(DefaultFileItem.class);
        when(defaultFileItem.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile = new CommonsMultipartFile(defaultFileItem);
        DefaultFileItem defaultFileItem1 = mock(DefaultFileItem.class);
        when(defaultFileItem1.getSize()).thenReturn(3L);
        CommonsMultipartFile preface = new CommonsMultipartFile(defaultFileItem1);
        Book book = mock(Book.class);
        doNothing().when(book).setPreface((CommonsMultipartFile) any());
        doNothing().when(book).setPrefacePath((String) any());
        doNothing().when(book).setPrice((Double) any());
        doNothing().when(book).setPublisheddate((Date) any());
        doNothing().when(book).setPublisher((String) any());
        doNothing().when(book).setViewTimes(anyInt());
        doNothing().when(book).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book).setPdfFilePath((String) any());
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookname((String) any());
        doNothing().when(book).setBooktype(anyInt());
        doNothing().when(book).setCodeinlib((String) any());
        doNothing().when(book).setDescription((String) any());
        doNothing().when(book).setDownloadTimes(anyInt());
        doNothing().when(book).setIsbn((String) any());
        doNothing().when(book).setLocationinlib((String) any());
        book.setAuthor("JaneDoe");
        book.setBookname("Bookname");
        book.setBooktype(1);
        book.setCodeinlib("Codeinlib");
        book.setDescription("The characteristics of someone or something");
        book.setDownloadTimes(1);
        book.setIsbn("Isbn");
        book.setLocationinlib("Locationinlib");
        book.setPdfFile(pdfFile);
        book.setPdfFilePath("/directory/foo.txt");
        book.setPreface(preface);
        book.setPrefacePath("Preface Path");
        book.setPrice(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPublisheddate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        book.setPublisher("Publisher");
        book.setViewTimes(1);
        when(bookMapper.deleteByPrimaryKey((String) any())).thenReturn(1);
        when(bookMapper.selectByPrimaryKey((String) any())).thenReturn(book);
        bookServiceImpl.delete("42");
        verify(bookMapper).deleteByPrimaryKey((String) any());
        verify(bookMapper).selectByPrimaryKey((String) any());
        verify(book).setAuthor((String) any());
        verify(book).setBookname((String) any());
        verify(book).setBooktype(anyInt());
        verify(book).setCodeinlib((String) any());
        verify(book).setDescription((String) any());
        verify(book).setDownloadTimes(anyInt());
        verify(book).setIsbn((String) any());
        verify(book).setLocationinlib((String) any());
        verify(book).setPdfFile((CommonsMultipartFile) any());
        verify(book).setPdfFilePath((String) any());
        verify(book).setPreface((CommonsMultipartFile) any());
        verify(book).setPrefacePath((String) any());
        verify(book).setPrice((Double) any());
        verify(book).setPublisheddate((Date) any());
        verify(book).setPublisher((String) any());
        verify(book).setViewTimes(anyInt());
        verify(defaultFileItem).getSize();
        verify(defaultFileItem1).getSize();
    }

    /**
     * Method under test: {@link BookServiceImpl#delete(String)}
     */
    @Test
    public void testDelete4() {
        DefaultFileItem defaultFileItem = mock(DefaultFileItem.class);
        when(defaultFileItem.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile = new CommonsMultipartFile(defaultFileItem);
        DefaultFileItem defaultFileItem1 = mock(DefaultFileItem.class);
        when(defaultFileItem1.getSize()).thenReturn(3L);
        CommonsMultipartFile preface = new CommonsMultipartFile(defaultFileItem1);
        Book book = mock(Book.class);
        doNothing().when(book).setPreface((CommonsMultipartFile) any());
        doNothing().when(book).setPrefacePath((String) any());
        doNothing().when(book).setPrice((Double) any());
        doNothing().when(book).setPublisheddate((Date) any());
        doNothing().when(book).setPublisher((String) any());
        doNothing().when(book).setViewTimes(anyInt());
        doNothing().when(book).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book).setPdfFilePath((String) any());
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookname((String) any());
        doNothing().when(book).setBooktype(anyInt());
        doNothing().when(book).setCodeinlib((String) any());
        doNothing().when(book).setDescription((String) any());
        doNothing().when(book).setDownloadTimes(anyInt());
        doNothing().when(book).setIsbn((String) any());
        doNothing().when(book).setLocationinlib((String) any());
        book.setAuthor("JaneDoe");
        book.setBookname("Bookname");
        book.setBooktype(1);
        book.setCodeinlib("Codeinlib");
        book.setDescription("The characteristics of someone or something");
        book.setDownloadTimes(1);
        book.setIsbn("Isbn");
        book.setLocationinlib("Locationinlib");
        book.setPdfFile(pdfFile);
        book.setPdfFilePath("/directory/foo.txt");
        book.setPreface(preface);
        book.setPrefacePath("Preface Path");
        book.setPrice(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPublisheddate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        book.setPublisher("Publisher");
        book.setViewTimes(1);
        when(bookMapper.deleteByPrimaryKey((String) any())).thenReturn(1);
        when(bookMapper.selectByPrimaryKey((String) any())).thenReturn(book);
        bookServiceImpl.delete(null);
        verify(book).setAuthor((String) any());
        verify(book).setBookname((String) any());
        verify(book).setBooktype(anyInt());
        verify(book).setCodeinlib((String) any());
        verify(book).setDescription((String) any());
        verify(book).setDownloadTimes(anyInt());
        verify(book).setIsbn((String) any());
        verify(book).setLocationinlib((String) any());
        verify(book).setPdfFile((CommonsMultipartFile) any());
        verify(book).setPdfFilePath((String) any());
        verify(book).setPreface((CommonsMultipartFile) any());
        verify(book).setPrefacePath((String) any());
        verify(book).setPrice((Double) any());
        verify(book).setPublisheddate((Date) any());
        verify(book).setPublisher((String) any());
        verify(book).setViewTimes(anyInt());
        verify(defaultFileItem).getSize();
        verify(defaultFileItem1).getSize();
    }

    /**
     * Method under test: {@link BookServiceImpl#modify(Book)}
     */
    @Test
    public void testModify() {
        BookServiceImpl bookServiceImpl = new BookServiceImpl();
        bookServiceImpl.modify(null);
        assertNull(bookServiceImpl.getBookMapper());
    }

    /**
     * Method under test: {@link BookServiceImpl#modify(Book)}
     */
    @Test
    public void testModify2() {
        DefaultFileItem defaultFileItem = mock(DefaultFileItem.class);
        when(defaultFileItem.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile = new CommonsMultipartFile(defaultFileItem);
        DefaultFileItem defaultFileItem1 = mock(DefaultFileItem.class);
        when(defaultFileItem1.getSize()).thenReturn(3L);
        CommonsMultipartFile preface = new CommonsMultipartFile(defaultFileItem1);
        Book book = mock(Book.class);
        doNothing().when(book).setPreface((CommonsMultipartFile) any());
        doNothing().when(book).setPrefacePath((String) any());
        doNothing().when(book).setPrice((Double) any());
        doNothing().when(book).setPublisheddate((Date) any());
        doNothing().when(book).setPublisher((String) any());
        doNothing().when(book).setViewTimes(anyInt());
        doNothing().when(book).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book).setPdfFilePath((String) any());
        doNothing().when(book).setAuthor((String) any());
        doNothing().when(book).setBookname((String) any());
        doNothing().when(book).setBooktype(anyInt());
        doNothing().when(book).setCodeinlib((String) any());
        doNothing().when(book).setDescription((String) any());
        doNothing().when(book).setDownloadTimes(anyInt());
        doNothing().when(book).setIsbn((String) any());
        doNothing().when(book).setLocationinlib((String) any());
        book.setAuthor("JaneDoe");
        book.setBookname("Bookname");
        book.setBooktype(1);
        book.setCodeinlib("Codeinlib");
        book.setDescription("The characteristics of someone or something");
        book.setDownloadTimes(1);
        book.setIsbn("Isbn");
        book.setLocationinlib("Locationinlib");
        book.setPdfFile(pdfFile);
        book.setPdfFilePath("/directory/foo.txt");
        book.setPreface(preface);
        book.setPrefacePath("Preface Path");
        book.setPrice(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        book.setPublisheddate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        book.setPublisher("Publisher");
        book.setViewTimes(1);
        when(bookMapper.updateByPrimaryKeySelective((Book) any())).thenReturn(1);
        when(bookMapper.selectByPrimaryKey((String) any())).thenReturn(book);
        DefaultFileItem defaultFileItem2 = mock(DefaultFileItem.class);
        when(defaultFileItem2.getSize()).thenReturn(3L);
        CommonsMultipartFile pdfFile1 = new CommonsMultipartFile(defaultFileItem2);
        DefaultFileItem defaultFileItem3 = mock(DefaultFileItem.class);
        when(defaultFileItem3.getSize()).thenReturn(3L);
        CommonsMultipartFile preface1 = new CommonsMultipartFile(defaultFileItem3);
        Book book1 = mock(Book.class);
        when(book1.getIsbn()).thenReturn("Isbn");
        doNothing().when(book1).setPreface((CommonsMultipartFile) any());
        doNothing().when(book1).setPrefacePath((String) any());
        doNothing().when(book1).setPrice((Double) any());
        doNothing().when(book1).setPublisheddate((Date) any());
        doNothing().when(book1).setPublisher((String) any());
        doNothing().when(book1).setViewTimes(anyInt());
        doNothing().when(book1).setPdfFile((CommonsMultipartFile) any());
        doNothing().when(book1).setPdfFilePath((String) any());
        doNothing().when(book1).setAuthor((String) any());
        doNothing().when(book1).setBookname((String) any());
        doNothing().when(book1).setBooktype(anyInt());
        doNothing().when(book1).setCodeinlib((String) any());
        doNothing().when(book1).setDescription((String) any());
        doNothing().when(book1).setDownloadTimes(anyInt());
        doNothing().when(book1).setIsbn((String) any());
        doNothing().when(book1).setLocationinlib((String) any());
        book1.setAuthor("JaneDoe");
        book1.setBookname("Bookname");
        book1.setBooktype(1);
        book1.setCodeinlib("Codeinlib");
        book1.setDescription("The characteristics of someone or something");
        book1.setDownloadTimes(1);
        book1.setIsbn("Isbn");
        book1.setLocationinlib("Locationinlib");
        book1.setPdfFile(pdfFile1);
        book1.setPdfFilePath("/directory/foo.txt");
        book1.setPreface(preface1);
        book1.setPrefacePath("Preface Path");
        book1.setPrice(10.0d);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        book1.setPublisheddate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        book1.setPublisher("Publisher");
        book1.setViewTimes(1);
        bookServiceImpl.modify(book1);
        verify(bookMapper).updateByPrimaryKeySelective((Book) any());
        verify(bookMapper).selectByPrimaryKey((String) any());
        verify(book).setAuthor((String) any());
        verify(book).setBookname((String) any());
        verify(book).setBooktype(anyInt());
        verify(book).setCodeinlib((String) any());
        verify(book).setDescription((String) any());
        verify(book).setDownloadTimes(anyInt());
        verify(book).setIsbn((String) any());
        verify(book).setLocationinlib((String) any());
        verify(book).setPdfFile((CommonsMultipartFile) any());
        verify(book).setPdfFilePath((String) any());
        verify(book).setPreface((CommonsMultipartFile) any());
        verify(book).setPrefacePath((String) any());
        verify(book).setPrice((Double) any());
        verify(book).setPublisheddate((Date) any());
        verify(book).setPublisher((String) any());
        verify(book).setViewTimes(anyInt());
        verify(defaultFileItem).getSize();
        verify(defaultFileItem1).getSize();
        verify(book1).getIsbn();
        verify(book1).setAuthor((String) any());
        verify(book1).setBookname((String) any());
        verify(book1).setBooktype(anyInt());
        verify(book1).setCodeinlib((String) any());
        verify(book1).setDescription((String) any());
        verify(book1).setDownloadTimes(anyInt());
        verify(book1).setIsbn((String) any());
        verify(book1).setLocationinlib((String) any());
        verify(book1).setPdfFile((CommonsMultipartFile) any());
        verify(book1).setPdfFilePath((String) any());
        verify(book1).setPreface((CommonsMultipartFile) any());
        verify(book1).setPrefacePath((String) any());
        verify(book1).setPrice((Double) any());
        verify(book1).setPublisheddate((Date) any());
        verify(book1).setPublisher((String) any());
        verify(book1).setViewTimes(anyInt());
        verify(defaultFileItem2).getSize();
        verify(defaultFileItem3).getSize();
    }

    /**
     * Method under test: {@link BookServiceImpl#getPopularBook(long)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetPopularBook() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.service.impl.BookServiceImpl.getPopularBook(BookServiceImpl.java:136)
        //   See https://diff.blue/R013 to resolve this issue.

        (new BookServiceImpl()).getPopularBook(1L);
    }

    /**
     * Method under test: {@link BookServiceImpl#getPopularBook(long)}
     */
    @Test
    public void testGetPopularBook2() {
        assertTrue((new BookServiceImpl()).getPopularBook(0L).isEmpty());
    }

    /**
     * Method under test: {@link BookServiceImpl#getPopularBook(long)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetPopularBook3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.service.impl.BookServiceImpl.getPopularBook(BookServiceImpl.java:136)
        //   See https://diff.blue/R013 to resolve this issue.

        (new BookServiceImpl()).getPopularBook(Long.MAX_VALUE);
    }

    /**
     * Method under test: {@link BookServiceImpl#getPopularBook(long)}
     */
    @Test
    public void testGetPopularBook4() {
        ArrayList<Book> bookList = new ArrayList<>();
        when(bookMapper.selectPopularBooks(anyInt())).thenReturn(bookList);
        List<Book> actualPopularBook = bookServiceImpl.getPopularBook(1L);
        assertSame(bookList, actualPopularBook);
        assertTrue(actualPopularBook.isEmpty());
        verify(bookMapper).selectPopularBooks(anyInt());
    }

    /**
     * Method under test: {@link BookServiceImpl#getPopularBook(long)}
     */
    @Test
    public void testGetPopularBook5() {
        when(bookMapper.selectPopularBooks(anyInt())).thenReturn(new ArrayList<>());
        assertTrue(bookServiceImpl.getPopularBook(-276L).isEmpty());
    }

    /**
     * Method under test: {@link BookServiceImpl#totalCountInDB()}
     */
    @Test
    public void testTotalCountInDB() {
        assertEquals(1L, (new BookServiceImpl()).totalCountInDB());
        assertEquals(3L, bookServiceImpl.totalCountInDB());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link BookServiceImpl}
     *   <li>{@link BookServiceImpl#setBookMapper(BookMapper)}
     *   <li>{@link BookServiceImpl#getBooksNumInDB()}
     *   <li>{@link BookServiceImpl#setBooksNumInDB(long)}
     *   <li>{@link BookServiceImpl#getBookMapper()}
     * </ul>
     */
    @Test
    public void testConstructor() {
        BookServiceImpl actualBookServiceImpl = new BookServiceImpl();
        actualBookServiceImpl.setBookMapper(null);
        long actualBooksNumInDB = actualBookServiceImpl.getBooksNumInDB();
        actualBookServiceImpl.setBooksNumInDB(1L);
        assertNull(actualBookServiceImpl.getBookMapper());
        assertEquals(1L, actualBooksNumInDB);
    }
}

