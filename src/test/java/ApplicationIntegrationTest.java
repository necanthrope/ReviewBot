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
import reviewbot.dto.metadata.*;

import org.json.simple.JSONObject;
import reviewbot.service.BookService;
import reviewbot.service.UserService;
import reviewbot.service.metadata.*;

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
    private UserService _userService;

    @Autowired
    private BookService _bookService;

    @Autowired
    private GenreService _genreService;

    @Autowired
    private SubgenreService _subgenreService;

    @Autowired
    private ThemeService _themeService;

    @Autowired
    private AwardService _awardService;

    @Autowired
    private FormatService _formatService;

    @Autowired
    private MiscService _miscService;

    private BookDTO _bookDTO;
    private GenreDTO _genreDTO1;
    private SubgenreDTO _subgenreDTO1;
    private ThemeDTO _themeDTO1;
    private AwardDTO _awardDTO1;
    private FormatDTO _formatDTO1;
    private MiscDTO _miscDTO1;

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
        _awardDTO1 = TestDataGenerator.createAward();
        _formatDTO1 = TestDataGenerator.createFormat();
        _miscDTO1 = TestDataGenerator.createMisc();

        _userDTO = _userService.readOne(new Long(1));

    }

    @Test
    public void test1CanCreateBook() {

        //System.out.println("\n\n\nBOOK:" + _bookDTO.getId() + ":" + _bookDTO.getTitle() + "\n\n\n");
        //_userDTO = _userService.readOne(1);

        _genreDTO1 = _genreService.create(_genreDTO1);
        _subgenreDTO1 = _subgenreService.create(_subgenreDTO1);
        _themeDTO1 = _themeService.create(_themeDTO1);
        _awardDTO1 = _awardService.create(_awardDTO1);
        _formatDTO1 = _formatService.create(_formatDTO1);
        _miscDTO1 = _miscService.create(_miscDTO1);


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

        JSONObject awardJson = new JSONObject();
        awardJson.put("id", _awardDTO1.getId());
        JSONArray awardDTOs = new JSONArray();
        awardDTOs.add(awardJson);

        JSONObject formatJson = new JSONObject();
        formatJson.put("id", _formatDTO1.getId());
        JSONArray formatDTOs = new JSONArray();
        formatDTOs.add(formatJson);

        JSONObject miscJson = new JSONObject();
        miscJson.put("id", _miscDTO1.getId());
        JSONArray miscDTOs = new JSONArray();
        miscDTOs.add(miscJson);

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
        bookJson.put("awards", awardDTOs);
        bookJson.put("formats", formatDTOs);
        bookJson.put("misc", miscDTOs);


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

                body("awards[0].name", equalTo(_awardDTO1.getName())).
                body("awards[0].description", equalTo(_awardDTO1.getDescription())).

                body("formats[0].name", equalTo(_formatDTO1.getName())).
                body("formats[0].description", equalTo(_formatDTO1.getDescription())).

                body("misc[0].name", equalTo(_miscDTO1.getName())).
                body("misc[0].description", equalTo(_miscDTO1.getDescription())).

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

        //System.out.println("\n\n\n\n CREATED BOOK: \n" + get("/readBooks?length=1&offset=0").asString() + "\n\n\n\n");

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

                body("[0].genres[0].name", equalTo(_genreDTO1.getName())).
                body("[0].genres[0].description", equalTo(_genreDTO1.getDescription())).

                body("[0].subgenres[0].name", equalTo(_subgenreDTO1.getName())).
                body("[0].subgenres[0].description", equalTo(_subgenreDTO1.getDescription())).

                body("[0].themes[0].name", equalTo(_themeDTO1.getName())).
                body("[0].themes[0].description", equalTo(_themeDTO1.getDescription())).

                body("[0].awards[0].name", equalTo(_awardDTO1.getName())).
                body("[0].awards[0].description", equalTo(_awardDTO1.getDescription())).

                body("[0].formats[0].name", equalTo(_formatDTO1.getName())).
                body("[0].formats[0].description", equalTo(_formatDTO1.getDescription())).

                body("[0].misc[0].name", equalTo(_miscDTO1.getName())).
                body("[0].misc[0].description", equalTo(_miscDTO1.getDescription())).

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

                body("genres[0].name", equalTo(_genreDTO1.getName())).
                body("genres[0].description", equalTo(_genreDTO1.getDescription())).

                body("subgenres[0].name", equalTo(_subgenreDTO1.getName())).
                body("subgenres[0].description", equalTo(_subgenreDTO1.getDescription())).

                body("themes[0].name", equalTo(_themeDTO1.getName())).
                body("themes[0].description", equalTo(_themeDTO1.getDescription())).

                body("awards[0].name", equalTo(_awardDTO1.getName())).
                body("awards[0].description", equalTo(_awardDTO1.getDescription())).

                body("formats[0].name", equalTo(_formatDTO1.getName())).
                body("formats[0].description", equalTo(_formatDTO1.getDescription())).

                body("misc[0].name", equalTo(_miscDTO1.getName())).
                body("misc[0].description", equalTo(_miscDTO1.getDescription())).

        log()
                .ifError();

    }

    @Test
    public void test4CanUpdateBook() {
        writeDummyData();

        BookDTO book2 = TestDataGenerator.createBook();
        book2.setUser(_userDTO);

        JSONObject userJson = new JSONObject();
        userJson.put("id", _userDTO.getId());
        userJson.put("username", _userDTO.getUsername());

        JSONObject bookJson = new JSONObject();
        bookJson.put("id", _bookDTO.getId());

        bookJson.put("title", book2.getTitle());
        bookJson.put("author", book2.getAuthor());
        bookJson.put("publisher", book2.getPublisher());
        bookJson.put("isbn", book2.getIsbn());
        bookJson.put("year", book2.getYear());

        bookJson.put("user", userJson);

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
            _bookService.delete(_bookDTO.getId());
        }
        if (_genreDTO1.getId() != null)
            _genreService.delete(_genreDTO1.getId());

        if (_subgenreDTO1.getId() != null)
            _subgenreService.delete(_subgenreDTO1.getId());

        if(_themeDTO1.getId() != null)
            _themeService.delete(_themeDTO1.getId());

        if(_awardDTO1.getId() != null)
            _awardService.delete(_awardDTO1.getId());

        if(_formatDTO1.getId() != null)
            _formatService.delete(_formatDTO1.getId());

        if(_miscDTO1.getId() != null)
            _miscService.delete(_miscDTO1.getId());

    }

    private void writeDummyData(){

        // Create the metadata objects in the db, so the DTOs have valid IDs.
        _genreDTO1 = _genreService.create(_genreDTO1);
        _subgenreDTO1 = _subgenreService.create(_subgenreDTO1);
        _themeDTO1 = _themeService.create(_themeDTO1);
        _awardDTO1 = _awardService.create(_awardDTO1);
        _formatDTO1 = _formatService.create(_formatDTO1);
        _miscDTO1 = _miscService.create(_miscDTO1);


        List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
        genreDTOs.add(_genreDTO1);

        List<SubgenreDTO> subgenreDTOs = new ArrayList<SubgenreDTO>();
        subgenreDTOs.add(_subgenreDTO1);

        List<ThemeDTO> themeDTOs = new ArrayList<ThemeDTO>();
        themeDTOs.add(_themeDTO1);

        List<AwardDTO> awardDTOs = new ArrayList<AwardDTO>();
        awardDTOs.add(_awardDTO1);

        List<FormatDTO> formatDTOs = new ArrayList<FormatDTO>();
        formatDTOs.add(_formatDTO1);

        List<MiscDTO> miscDTOs = new ArrayList<MiscDTO>();
        miscDTOs.add(_miscDTO1);


        _bookDTO.setUser(_userDTO);
        _bookDTO.setGenres(genreDTOs);
        _bookDTO.setSubgenres(subgenreDTOs);
        _bookDTO.setThemes(themeDTOs);
        _bookDTO.setAwards(awardDTOs);
        _bookDTO.setFormats(formatDTOs);
        _bookDTO.setMisc(miscDTOs);

        _bookDTO = _bookService.create(_bookDTO);

    }

}
