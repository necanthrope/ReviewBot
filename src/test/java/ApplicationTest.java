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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.transaction.Transactional;

import reviewbot.Application;
import reviewbot.dto.*;
import reviewbot.repository.BookRepository;
import reviewbot.repository.UserRepository;
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
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTest {


    @Autowired
    private BookRepository _bookRepository;

    @Autowired
    private UserRepository _userRepository;

    private BookDTO _bookDTO;
    private GenreDTO _genreDTO1;
    private SubgenreDTO _subgenreDTO1;
    private ThemeDTO _themeDTO1;

    private UserDTO _userDTO;

    private boolean needsCleaning = true;

    @Value("${local.server.port}")
            int port;


    @Before
    public void setUp() {

        RestAssured.port = port;
        _bookDTO = TestDataGenerator.createBook();
        _genreDTO1 = TestDataGenerator.createGenre();
        _subgenreDTO1 = TestDataGenerator.createSubgenre();
        _themeDTO1 = TestDataGenerator.createTheme();

        _userDTO = _userRepository.readOne(1);

    }

    @Test
    public void test1CanCreateBook() {

        //System.out.println("\n\n\nBOOK:" + _bookDTO.getId() + ":" + _bookDTO.getTitle() + "\n\n\n");

        //_userDTO = _userRepository.readOne(1);

        JSONObject userJson = new JSONObject();
        userJson.put("id", _userDTO.getId());
        userJson.put("username", _userDTO.getUsername());

        JSONObject bookJson = new JSONObject();
        bookJson.put("title", _bookDTO.getTitle());
        bookJson.put("author", _bookDTO.getAuthor());
        bookJson.put("publisher", _bookDTO.getPublisher());
        bookJson.put("isbn", _bookDTO.getIsbn());
        bookJson.put("year", _bookDTO.getYear());
        bookJson.put("user", userJson);


        //System.out.println("\n\n\n\nJSON: \n" + bookJson.toJSONString());
        //System.out.println("\nJSON: \n" + userJson.toJSONString() + "\n\n\n\n");

        Integer bookId = given().
                contentType(JSON).
                body(bookJson.toJSONString()).
        when().
                post("/createBook").
        then().
                contentType(JSON).
                statusCode(HttpStatus.SC_OK).
                body("title", equalTo(_bookDTO.getTitle())).
                body("author", equalTo(_bookDTO.getAuthor())).
                body("publisher", equalTo(_bookDTO.getPublisher())).
                body("isbn", equalTo(_bookDTO.getIsbn())).
                body("year", equalTo(_bookDTO.getYear())).
        log()
                .ifError().
        extract()
                .path("id");

        _bookDTO.setId(bookId);

        //System.out.println("\n\n\n **" + bookId + "**\n\n\n");

    }

    @Test
    public void test2CanReadBooks() {
        _bookDTO.setUser(_userDTO);
        _bookDTO = _bookRepository.create(_bookDTO);

        //System.out.println("\n\n\n\n" + get("/books?length=1&offset=0").asString() + "\n\n\n\n");

        when().
                get("/readBooks?length=1&offset=0").
        then().
                contentType(JSON).
                statusCode(HttpStatus.SC_OK).
                body("[0].title", equalTo(_bookDTO.getTitle())).
                body("[0].author", equalTo(_bookDTO.getAuthor())).
                body("[0].publisher", equalTo(_bookDTO.getPublisher())).
                body("[0].isbn", equalTo(_bookDTO.getIsbn())).
                body("[0].year", equalTo(_bookDTO.getYear())).
        log()
                .ifError();

    }

    @Test
    public void test3CanReadBook() {

        _bookDTO.setUser(_userDTO);
        _bookDTO = _bookRepository.create(_bookDTO);
        //System.out.println("\n\n\n\n" + get("/readBook?id=".concat(_bookDTO.getId().toString())).asString() + "\n\n\n\n");

        when().
                get("/readBook?id=".concat(_bookDTO.getId().toString())).
        then().
                contentType(JSON).
                statusCode(HttpStatus.SC_OK).
                body("title", equalTo(_bookDTO.getTitle())).
                body("author", equalTo(_bookDTO.getAuthor())).
                body("publisher", equalTo(_bookDTO.getPublisher())).
                body("isbn", equalTo(_bookDTO.getIsbn())).
                body("year", equalTo(_bookDTO.getYear())).
        log()
                .ifError();

    }

    @Test
    public void test4CanUpdateBook() {
        _bookDTO.setUser(_userDTO);
        _bookDTO = _bookRepository.create(_bookDTO);

        BookDTO book2 = TestDataGenerator.createBook();
        book2.setUser(_userDTO);

        JSONObject bookJson = new JSONObject();
        bookJson.put("id", _bookDTO.getId());

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
                get("/readBook?id=".concat(_bookDTO.getId().toString())).
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
        _bookDTO.setUser(_userDTO);
        _bookDTO = _bookRepository.create(_bookDTO);

        when().
                get("/deleteBook?id=".concat(_bookDTO.getId().toString())).
        then().
                statusCode(HttpStatus.SC_OK).
        log()
                .ifError();

        needsCleaning = false;
    }

    @After
    public void cleanUp() {
        if (needsCleaning) {
            _bookRepository.delete(_bookDTO.getId());
        }
    }

}
