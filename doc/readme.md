## Project Description

[Toc]

### Demand analysis -- Design first and code later.

First of all, we should know what we are going to do. 
In this project, we want to build a website which support user to upload and download their favorite books.

This system will include different main module.

* Login and Registration Module

   Support all user and traveler to register and login into our website. The website system make sure that 
   the user who trying to login have registed. It will also supply the interface(URL) to logout.

   There will be three different user in this system -- the traveler(un-registed), the normal user(registed)
   and administrator.

   The default privilege of the registed user is `Normal user`. The administrator(Root User) which built in
   with the website when it start to run.

* Book Service Module

   PDF books upload, download, query, modificatioin and delete.

* Record Service Module

  Record used to store the information about who download or upload books.
  Normal users are able to query the records which are related to themselves. No one can access others 
  information except administrator.

  Record module supply the service to generate, query and delete the records.

* User Service Module

  Only open to administrator to create and delete


### Design database table with MySQL script

A book can be used by many people. A user can also have relations with many different books.
So it's clear that the relation between `Book` and `User` is many to many. As the designer,
I used a database table -- `Record` as the middle table to build the connection between 
user and books.

The detail of tables in database shown below there. (With the help of MySQLWorkbench)

![images](./images/bookOceanMySQLtables.png)

There are two `FOREIGN KEY` in the `record table` which store the primary key of the others
two table.


### User `MyBatis` to generate the ORM(Object Relation Mapping) Java files.

![images](./images/layyerModel.png)

After you configured the configure file (configure.xml) of MyBatis, mybatis will help you to 
generate the mapping java class according to the database table. Every table will have it own
java class file and the default interface(Mapper files) to manipulate the database.

The command to run MyBatis:

  java -jar mybatis-generator-core-1.3.2.jar -configfile generator.xml -overwrite

For a building on project, the default mapper files may not be suitable with the change of demand.
You can also learn how to write and modify the mapper files to generate the SQL statements which 
you want.


#### Mapper Interface

MyBatis will generate java interface files and XML files for database tables.
The XML mapper files will used to instance an java object which implement the java interface
 when the application is running. The user can use these java object with the interface to 
 manipulate the database and run the MySQL script statements. *The name of methods in mapper interface must be coincident with the statements id int XML files.*



### Service Layyer and Pagination Query

The Service layyer suppy the API for upper layer application to complete the work which related to the low-layyer implementation. It is as the middle layyer for insulating the upper layyer and low layyer. Keep the whole project with good encapsulation and modularization.

Every service implementation will maintain a `mapper` filed and `countsInDB` filed. The `countsInDB` is the cache value for the number of items of the object in the database table.

eg:

``` java

public class BookServiceImpl extends BaseDomain implements BookService {

    static private BookMapper bookMapper;

    static private long booksNumInDB;

    @Override
    public List<Book> pagedFuzzyQuery(BookSearchForm form, PageInfo pageInfo) throws Exception{
    	...
    }
}

```

What is the one of the important thing to declare is that the service will also supply a method to get items in the database with *Pagination*, which means query with pagination that is very helpful for the performance and user friendly.




### URL entrance design

For convenient, I put all URLs which can be accessed by user into java class ` org.sims.util.URLs`.

The URL construct with different level like:  "/first level/second level/third level/ ... "

If a user want to download a book, the user just send a HTTP request to the URL -- "/books/download/{book-id}"
and the back-end system will start to send a copy of the file to the client.

URL like "/login", "/home", "/users", "/books", "/records" and "/API" in the first level.
Back-end system should be responsible for mapping all bussiness logic with records into URL "/records".
The others service like the same.

I think it's important and helpful to build an management unit for URL in your system.



### Simple login validation with `OncePerRequestFilter`

Some website have a login module for validating the user who want to access the system. Only user who logged in the system will have the permission to access the resouces in the system.

eg:
A traveler who have not login yet are trying to send a request to the system with URL `/api/users/query/`. The system should redirect the request to login module and remind the traveler to login or register before accessing the resouces.

In Spring Web MVC, there is a filter `OncePerRequestFilter`. Filter base class that aims to guarantee a single execution per request dispatch, on any servlet container. We can put the validation logic code into this part.


### File upload and Download

In many web application, user may want to upload and download files.

In the back-end, Java code is response for receiving the data from the front-end and transform it into local file with the `transferTo` method of Java class `CommonsMultipartFile`.

``` java

  /* The demo code of how to upload a pdf file. */
  ... ... 

        //Authorized user will download the file
        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/static/books/pdf/");

        String path  = dataDirectory + book.getIsbn() + ".pdf";

        book.setPdfFilePath(path);

        if(book.getPdfFile() != null){
            File newFilePdf = new File(path);
            newFilePdf.createNewFile();
            book.getPdfFile().transferTo(newFilePdf);
        }

   ... ...

```

### Spring MVC

A classical model for the interactive between HTTP request and Spring MVC.

![images](/images/RequestWithSpringMVC.png)

I will recommend you to read all about the `5.1.1 Following the life of a request`  in the book `Spring In Action 4th edition` (page 132). It will help you a lot to understand how Spring process and response the HTTP request.


### Add object and attributes into model and session

In the front-end of the web system, there may need some variables in the back-end system to represent in the front-end views. Spring supply the class `Model` and developer can add attributes into the model with "Key-Value" pattern.

For the concision and consistence of the code, I put all "key" of the model into a individual class `org.sims.util.AttributesKey` with privilege __public static final__ 

``` java

package org.sims.util;


public class AttributesKey {

    private AttributesKey(){}

    public final static String SESSION_ATTRIBUTES_USER    = "user";
    public final static String SESSION_ATTRIBUTES_ADMIN   = "admin";

    ... ...
}

```

Every time we want to use a plain string as the key in model, we should not write it as plain text.
It's a good habbits to use a final static reference to use it in implementation.


### Developer friendly.

1. Many unit test to make sure the function run correctly.

2. Website integerated test data injection.


### Release and deploy the web application.

Environment: Ubuntu/Linux (Version), Java JDK8 JRE8, Tomcat (Version)

* Java Web Container -- Tomcat
  If you have a server which is belong to yourself, you can deploy the application on your own server.

* Google App Engine
  Instead of using Tomcat as the application container, we could also use Google App Engine to deploy our application.

* Spring Boot

### Performance test and optimization

Environment: Ubuntu/Linux (Version), Java JDK8 JRE8, Tomcat (Version), Maven(Version)



Helpful Reference:

[How to Pretty Print Your JSON With Spring and Jackson](http://springinpractice.com/2013/11/01/how-to-pretty-print-your-json-with-spring-and-jackson)

https://spring.io/blog/2013/06/03/content-negotiation-using-views

[Message Queue](http://www.cnblogs.com/linjiqin/p/5720865.html)

[Memcached for java](https://www.youtube.com/watch?v=0wnjcT2M6Ms)

https://github.com/fsouza/java-memcached-example

https://www.youtube.com/playlist?list=PLAwxTw4SYaPmKmNX-INgcxQWf30KuWa_A

[How to deploy your Java Web application with Google App Engine](https://www.youtube.com/watch?v=751iGaVX2Rc)