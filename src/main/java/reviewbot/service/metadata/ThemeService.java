/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reviewbot.dto.metadata.ThemeDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Theme;
import reviewbot.repository.metadata.ThemeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
@Service
public class ThemeService extends AbstractMetadataService <Theme, ThemeDTO> {

    @Autowired
    private ThemeRepository _themeRepository;

    @Override
    public ThemeDTO create(ThemeDTO themeDTO) {
        return unwrap(_themeRepository.create(wrap(themeDTO)));
    }

    @Override
    public ThemeDTO readOne(Long id) {
        return unwrap(_themeRepository.readOne(id.intValue()));
    }

    @Override
    public List<ThemeDTO> readList(Long[] ids) {
        Integer[] idsInt = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idsInt[i] = ids[i].intValue();
        }
        return unwrapList(_themeRepository.readList(idsInt));
    }

    @Override
    public List<ThemeDTO> readRange(Integer offset, Integer length) {
        return unwrapList(_themeRepository.readRange(offset, length));
    }

    @Override
    public List<ThemeDTO> readAll() {
        return unwrapList(_themeRepository.readAll());
    }

    @Override
    public ThemeDTO update(ThemeDTO dto) {
        Theme theme = _themeRepository.readOne(dto.getId());
        theme.setName(dto.getName());
        theme.setDescription(dto.getDescription());
        return unwrap(_themeRepository.update(wrap(dto)));
    }

    @Override
    public void delete(Integer id) {
        _themeRepository.delete(id);
    }

    @Override
    public Theme wrap(ThemeDTO themeDTO) {
        Theme theme = new Theme();

        theme.setName(themeDTO.getName());
        theme.setDescription(themeDTO.getDescription());

        return theme;
    }

    @Override
    public ThemeDTO unwrap(Theme theme) {
        ThemeDTO themeDTO = new ThemeDTO();

        themeDTO.setId(theme.getTheme());
        themeDTO.setName(theme.getName());
        themeDTO.setDescription(theme.getDescription());

        return themeDTO;
    }

    private List<ThemeDTO> unwrapList(List<Theme> themeEntities) {
        List<ThemeDTO> themes = new ArrayList<ThemeDTO>();
        for (Theme theme : themeEntities) {
            themes.add(unwrap(theme));
        }
        return themes;
    }

    @Override
    public GenreMap wrapMapping (Book book, ThemeDTO themeDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setTheme(_themeRepository.readOne(themeDTO.getId()));
        return genreMap;
    }
}