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
import org.json.simple.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.transaction.Transactional;

import reviewbot.Application;
import reviewbot.dto.*;
import reviewbot.dto.metadata.GenreDTO;
import reviewbot.dto.metadata.SubgenreDTO;
import reviewbot.dto.metadata.ThemeDTO;
import reviewbot.repository.*;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/10/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, TestDatabaseConfig.class})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
@org.springframework.boot.test.IntegrationTest("server.port:9001")
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationIntegrationTest {

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private BookRepository _bookRepository;

    @Autowired
    private GenreRepository _genreRepository;

    @Autowired
    private SubgenreRepository _subgenreRepository;

    @Autowired
    private ThemeRepository _themeRepository;

    @Autowired
    private AwardRepository _awardRepository;

    @Autowired
    private FormatRepository _formatRepository;

    @Autowired
    private MiscRepository _miscRepository;

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

        _genreDTO1 = _genreRepository.create(_genreDTO1);
        _subgenreDTO1 = _subgenreRepository.create(_subgenreDTO1);
        _themeDTO1 = _themeRepository.create(_themeDTO1);


        JSONObject userJson = new JSONObject();
        userJson.put("id", _userDTO.getId());
        userJson.put("username", _userDTO.getUsername());

        JSONObject genreJson = new JSONObject();
        genreJson.put("id", _genreDTO1.getId());
        JSONArray genreDTOs = new JSONArray();
        genreDTOs.add(genreJson);

        JSONObject subgenreJson = new JSONObject();
        subgenreJson.put("id", _subgenreDTO1.getId());
        JSONArray subgenreDTOs = new JSONArray();
        subgenreDTOs.add(subgenreJson);

        JSONObject themeJson = new JSONObject();
        themeJson.put("id", _themeDTO1.getId());
        JSONArray themeDTOs = new JSONArray();
        themeDTOs.add(themeJson);


        JSONObject bookJson = new JSONObject();
        bookJson.put("title", _bookDTO.getTitle());
        bookJson.put("author", _bookDTO.getAuthor());
        bookJson.put("publisher", _bookDTO.getPublisher());
        bookJson.put("isbn", _bookDTO.getIsbn());
        bookJson.put("year", _bookDTO.getYear());
        bookJson.put("user", userJson);
        bookJson.put("genres", genreDTOs);
        bookJson.put("subgenres", subgenreDTOs);
        bookJson.put("themes", themeDTOs);


        System.out.println("\n\n\n\nJSON: \n" + bookJson.toJSONString());
        System.out.println("\nJSON: \n" + genreDTOs.toJSONString() + "\n\n\n\n");

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

                body("genres[0].name", equalTo(_genreDTO1.getName())).
                body("genres[0].description", equalTo(_genreDTO1.getDescription())).

                body("subgenres[0].name", equalTo(_subgenreDTO1.getName())).
                body("subgenres[0].description", equalTo(_subgenreDTO1.getDescription())).

                body("themes[0].name", equalTo(_themeDTO1.getName())).
                body("themes[0].description", equalTo(_themeDTO1.getDescription())).

                log()
                .ifError().
        extract()
                .path("id");

        _bookDTO.setId(bookId);

        //System.out.println("\n\n\n **" + bookId + "**\n\n\n");
        System.out.println("\n\n\n\n CREATED BOOK: \n" + get("/readBook?id=".concat(_bookDTO.getId().toString())).asString() + "\n\n\n\n");

    }

    @Test
    public void test2CanReadBooks() {
        writeDummyData();

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

        writeDummyData();
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
        writeDummyData();

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
        writeDummyData();

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
        if (_genreDTO1.getId() != null)
            _genreRepository.delete(_genreDTO1.getId());

        if (_subgenreDTO1.getId() != null)
            _subgenreRepository.delete(_subgenreDTO1.getId());

        if(_themeDTO1.getId() != null)
            _themeRepository.delete(_themeDTO1.getId());
    }

    private void writeDummyData(){

        // Create the metadata objects in the db, so the DTOs have valid IDs.
        _genreDTO1 = _genreRepository.create(_genreDTO1);
        _subgenreDTO1 = _subgenreRepository.create(_subgenreDTO1);
        _themeDTO1 = _themeRepository.create(_themeDTO1);

        List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
        genreDTOs.add(_genreDTO1);

        List<SubgenreDTO> subgenreDTOs = new ArrayList<SubgenreDTO>();
        subgenreDTOs.add(_subgenreDTO1);

        List<ThemeDTO> themeDTOs = new ArrayList<ThemeDTO>();
        themeDTOs.add(_themeDTO1);

        _bookDTO.setUser(_userDTO);
        _bookDTO.setGenres(genreDTOs);
        _bookDTO.setSubgenres(subgenreDTOs);
        _bookDTO.setThemes(themeDTOs);

        _bookDTO = _bookRepository.create(_bookDTO);

    }

}
