/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import reviewbot.Application;
import reviewbot.dao.BookDAO;
import reviewbot.entity.Book;

import static com.jayway.restassured.RestAssured.when;

/**
 * Created by jtidwell on 4/10/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({
        "server.port:9001",
        "db.url=jdbc:mysql://localhost:3306/reviewbot_test?zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8"
})
public class ApplicationTest {

    @Autowired
    BookDAO bookDAO;

    private Book _book = new Book();

    @Value("${local.server.port}")   // 6
            int port;

    @Before
    public void setUp() {
        _book.setTitle((String) Double.toString((Math.random() * 20)));
        _book.setAuthor((String) Double.toString((Math.random() * 10)));
        bookDAO.create(_book);

        RestAssured.port = port;
    }

    @Test
    public void testPrintHelloWorld() {

        Assert.assertEquals(Application.getHelloWorld(), "Hello World");

    }

    @Test
    public void canGetBook() {
        Integer bookId = _book.getId();
        when().
                get("/books?length=1&offset=0").
        then().
                statusCode(HttpStatus.SC_OK);
    }
}
