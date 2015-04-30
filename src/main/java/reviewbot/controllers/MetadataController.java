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
import reviewbot.dto.metadata.GenreDTO;
import reviewbot.dto.metadata.SubgenreDTO;
import reviewbot.dto.metadata.ThemeDTO;
import reviewbot.service.metadata.GenreService;
import reviewbot.service.metadata.SubgenreService;
import reviewbot.service.metadata.ThemeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@RestController
public class MetadataController {
    @Autowired
    private GenreService _genreService;
    @Autowired
    private SubgenreService _subgenreService;
    @Autowired
    private ThemeService _themeService;

    /**
     * Forward the request for genre metadata to the GenreDAO.
     *
     * @param idsIn
     * @return List of genre objects.
     */

    @RequestMapping(value="/genres", method= RequestMethod.GET, produces="application/json")
    public List<GenreDTO> getGenresByIds(
            @RequestParam(value="ids") String idsIn) {
        if(idsIn == null)
            return new ArrayList<GenreDTO>();

        return _genreService.readList(parseIds(idsIn));
    }

    /**
     * Forward the request for subgenre metadata to the SubgenreDAO.
     *
     * @param idsIn a comma separated string of id values
     * @return List of subgenre objects
     */
    @RequestMapping(value="/subgenres", method= RequestMethod.GET, produces="application/json")
    public List<SubgenreDTO> getSubgenresByIds(
            @RequestParam(value="ids") String idsIn) {
        if(idsIn == null)
            return new ArrayList<SubgenreDTO>();

        return _subgenreService.readList(parseIds(idsIn));
    }

    /**
     * Forward the request for theme metadata to the ThemeDAO.
     *
     * @param idsIn a comma separated string of id values
     * @return List of theme objects
     */
    @RequestMapping(value="/themes", method= RequestMethod.GET, produces="application/json")
    public List<ThemeDTO> getThemesByIds(
            @RequestParam(value="ids") String idsIn) {
        if(idsIn == null)
            return new ArrayList<ThemeDTO>();

        return _themeService.readList(parseIds(idsIn));
    }

    /**
     * All the metadata tables have a similar structure. This parses a commma
     * separated string of ID values into a Integer array, to be used by the repository
     * when querying the metadata tables.
     *
     * @param idsIn a comma separated string of id values
     * @return Integer array of requested ID values
     */
    private Long[] parseIds (String idsIn) {
        List<Long> idLong = new ArrayList<>();
        String[] idStrings = idsIn.split(",");
        for (String idStr : idStrings) {
            idLong.add(Long.parseLong(idStr));
        }
        return idLong.toArray(new Long[0]);
    }
}
