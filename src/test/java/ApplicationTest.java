/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

import com.jayway.restassured.RestAssured;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

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
import reviewbot.configuration.DatabaseConfig;
import reviewbot.dao.BookDAO;
import reviewbot.entity.Book;


/**
 * Created by jtidwell on 4/10/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, DatabaseConfig.class})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
@IntegrationTest("server.port:9001")
@PropertySource("classpath:test.properties")
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTest {

    @Autowired
    BookDAO bookDAO;

    private Book _book = new Book();
    private static boolean setupDone = false;

    @Value("${local.server.port}")
            int port;

    @Before
    public void setUp() {

        if(setupDone)
            return;

        RestAssured.port = port;

        _book.setTitle(Double.toString((Math.random() * 20)));
        _book.setAuthor(Double.toString((Math.random() * 10)));
        _book.setPublisher(Double.toString((Math.random() * 10)));
        _book.setIsbn(Double.toString(Math.floor((Math.random() * 100000000))));
        _book.setYear(Double.toString(Math.floor((Math.random() * 100 ))));

        bookDAO.create(_book);

        setupDone = true;

    }

    @Test
    public void canGetBook() {
        when().
                get("/books?length=1&offset=0").
        then().
                statusCode(HttpStatus.SC_OK).
                body("title", hasItems(_book.getTitle())).
                body("author", hasItems(_book.getAuthor())).
                body("publisher", hasItems(_book.getPublisher())).
                body("isbn", hasItems(_book.getIsbn())).
                body("year", hasItems(_book.getYear())); //.
                //body("title", equalTo(_book.getTitle())).
                //body(containsString(_book.getTitle()));
    }

    @After
    public void cleanUp() {
        if (!setupDone)
            return;

        bookDAO.delete(_book);
    }
}
