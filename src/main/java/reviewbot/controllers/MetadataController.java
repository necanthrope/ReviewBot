/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewbot.dao.GenreDAO;
import reviewbot.dao.SubgenreDAO;
import reviewbot.dao.ThemeDAO;
import reviewbot.entity.Genre;
import reviewbot.entity.Subgenre;
import reviewbot.entity.Theme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@RestController
public class MetadataController {
    @Autowired
    private GenreDAO _genreDAO;
    @Autowired
    private SubgenreDAO _subgenreDAO;
    @Autowired
    private ThemeDAO _themeDAO;

    /**
     * Forward the request for genre metadata to the GenreDAO.
     *
     * @param idsIn
     * @return List of genre objects.
     */

    @RequestMapping(value="/genres", method= RequestMethod.GET, produces="application/json")
    public List<Genre> getGenresByIds(
            @RequestParam(value="ids") String idsIn) {
        if(idsIn == null)
            return new ArrayList<>();

        return _genreDAO.readList(parseIds(idsIn));
    }

    /**
     * Forward the request for subgenre metadata to the SubgenreDAO.
     *
     * @param idsIn a comma separated string of id values
     * @return List of subgenre objects
     */
    @RequestMapping(value="/subgenres", method= RequestMethod.GET, produces="application/json")
    public List<Subgenre> getSubgenresByIds(
            @RequestParam(value="ids") String idsIn) {
        if(idsIn == null)
            return new ArrayList<>();

        return _subgenreDAO.readList(parseIds(idsIn));
    }

    /**
     * Forward the request for theme metadata to the ThemeDAO.
     *
     * @param idsIn a comma separated string of id values
     * @return List of theme objects
     */
    @RequestMapping(value="/themes", method= RequestMethod.GET, produces="application/json")
    public List<Theme> getThemesByIds(
            @RequestParam(value="ids") String idsIn) {
        if(idsIn == null)
            return new ArrayList<>();

        return _themeDAO.readList(parseIds(idsIn));
    }

    /**
     * All the metadata tables have a similar structure. This parses a commma
     * separated string of ID values into a Long array, to be used by the DAO
     * when querying the metadata tables.
     *
     * @param idsIn a comma separated string of id values
     * @return Long array of requested ID values
     */
    private Long[] parseIds (String idsIn) {
        List<Long> idLongList = new ArrayList<>();
        String[] idStrings = idsIn.split(",");
        for (String idStr : idStrings) {
            idLongList.add(Long.parseLong(idStr));
        }
        return idLongList.toArray(new Long[0]);
    }
}
