/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

import com.jayway.restassured.RestAssured;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.http.ContentType.JSON;

import configuration.TestDatabaseConfig;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.transaction.Transactional;

import reviewbot.Application;
import reviewbot.dao.BookDAO;
import reviewbot.dao.UserDAO;
import reviewbot.entity.*;

import org.json.simple.JSONObject;

/**
 * Created by jtidwell on 4/10/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, TestDatabaseConfig.class})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
@IntegrationTest("server.port:9001")
@PropertySource("classpath:/test.properties")
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTest {


    @Autowired
    BookDAO bookDAO;

    @Autowired
    UserDAO userDAO;

    private Book _book;
    private Genre _genre1;
    private Subgenre _subgenre1;
    private Theme _theme1;

    private User _user;

    private boolean needsCleaning = true;

    @Value("${local.server.port}")
            int port;


    @Before
    public void setUp() {

        RestAssured.port = port;
        _book = TestDataGenerator.createBook();
        _genre1 = TestDataGenerator.createGenre();
        _subgenre1 = TestDataGenerator.createSubgenre();
        _theme1 = TestDataGenerator.createTheme();

        _user = userDAO.readOne(1);

    }

    @Test
    public void test1CanCreateBook() {

        //System.out.println("\n\n\nBOOK:" + _book.getId() + ":" + _book.getTitle() + "\n\n\n");

        //_user = userDAO.readOne(1);

        JSONObject userJson = new JSONObject();
        userJson.put("id", _user.getId());
        userJson.put("username", _user.getUsername());

        JSONObject bookJson = new JSONObject();
        bookJson.put("title", _book.getTitle());
        bookJson.put("author", _book.getAuthor());
        bookJson.put("publisher", _book.getPublisher());
        bookJson.put("isbn", _book.getIsbn());
        bookJson.put("year", _book.getYear());
        bookJson.put("user", userJson.toJSONString());


        System.out.println("\n\n\n\nJSON: \n" + bookJson.toJSONString() + "\n\n\n\n");

        Integer bookId = given().
                contentType(JSON).
                body(bookJson.toJSONString()).
        when().
                post("/createBook").
        then().
                contentType(JSON).
                statusCode(HttpStatus.SC_OK).
                body("title", equalTo(_book.getTitle())).
                body("author", equalTo(_book.getAuthor())).
                body("publisher", equalTo(_book.getPublisher())).
                body("isbn", equalTo(_book.getIsbn())).
                body("year", equalTo(_book.getYear())).
        log()
                .ifError().
        extract()
                .path("id");

        _book.setId(bookId);

        //System.out.println("\n\n\n **" + bookId + "**\n\n\n");

    }

    @Test
    public void test2CanReadBooks() {
        _book.setUsers(_user);
        bookDAO.create(_book);
        //System.out.println("\n\n\n\n" + get("/books?length=1&offset=0").asString() + "\n\n\n\n");

        when().
                get("/readBooks?length=1&offset=0").
        then().
                contentType(JSON).
                statusCode(HttpStatus.SC_OK).
                body("[0].title", equalTo(_book.getTitle())).
                body("[0].author", equalTo(_book.getAuthor())).
                body("[0].publisher", equalTo(_book.getPublisher())).
                body("[0].isbn", equalTo(_book.getIsbn())).
                body("[0].year", equalTo(_book.getYear())).
        log()
                .ifError();

    }

    @Test
    public void test3CanReadBook() {

        _book.setUsers(_user);
        bookDAO.create(_book);
        //System.out.println("\n\n\n\n" + get("/readBook?id=".concat(_book.getId().toString())).asString() + "\n\n\n\n");

        when().
                get("/readBook?id=".concat(_book.getId().toString())).
        then().
                contentType(JSON).
                statusCode(HttpStatus.SC_OK).
                body("title", equalTo(_book.getTitle())).
                body("author", equalTo(_book.getAuthor())).
                body("publisher", equalTo(_book.getPublisher())).
                body("isbn", equalTo(_book.getIsbn())).
                body("year", equalTo(_book.getYear())).
        log()
                .ifError();

    }

    @Test
    public void test4CanUpdateBook() {
        _book.setUsers(_user);
        bookDAO.create(_book);

        Book book2 = TestDataGenerator.createBook();
        book2.setUsers(_user);

        JSONObject bookJson = new JSONObject();
        bookJson.put("id", _book.getId());

        bookJson.put("title", book2.getTitle());
        bookJson.put("author", book2.getAuthor());
        bookJson.put("publisher", book2.getPublisher());
        bookJson.put("isbn", book2.getIsbn());
        bookJson.put("year", book2.getYear());

        given().
                contentType(JSON).
                body(bookJson.toJSONString()).
                when().
                post("/updateBook").
        then().
                statusCode(HttpStatus.SC_OK).
        log()
                .ifError();


        when().
                get("/readBook?id=".concat(_book.getId().toString())).
        then().
                contentType(JSON).
                statusCode(HttpStatus.SC_OK).
                body("title", equalTo(book2.getTitle())).
                body("author", equalTo(book2.getAuthor())).
                body("publisher", equalTo(book2.getPublisher())).
                body("isbn", equalTo(book2.getIsbn())).
                body("year", equalTo(book2.getYear())).
        log()
                .ifError();
    }

    @Test
    public void test5CanDeleteBook() {
        _book.setUsers(_user);
        bookDAO.create(_book);

        when().
                get("/deleteBook?id=".concat(_book.getId().toString())).
        then().
                statusCode(HttpStatus.SC_OK).
        log()
                .ifError();

        needsCleaning = false;
    }

    @After
    public void cleanUp() {
        if (needsCleaning) {
            bookDAO.delete(_book.getId());
        }
    }

}
