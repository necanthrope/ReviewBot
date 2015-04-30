/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reviewbot.dto.metadata.GenreDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Genre;
import reviewbot.repository.metadata.GenreRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
@Service
public class GenreService extends AbstractMetadataService <Genre, GenreDTO> {

    @Autowired
    private GenreRepository _genreRepository;

    @Override
    public GenreDTO create(GenreDTO genreDTO) {
        return unwrap(_genreRepository.create(wrap(genreDTO)));
    }

    @Override
    public GenreDTO readOne(Long id) {
        return unwrap(_genreRepository.readOne(id.intValue()));
    }

    @Override
    public List<GenreDTO> readList(Long[] ids) {
        Integer[] idsInt = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idsInt[i] = ids[i].intValue();
        }
        return unwrapList(_genreRepository.readList(idsInt));
    }

    @Override
    public List<GenreDTO> readRange(Integer offset, Integer length) {
        return unwrapList(_genreRepository.readRange(offset, length));
    }

    @Override
    public List<GenreDTO> readAll() {
        return unwrapList(_genreRepository.readAll());
    }

    @Override
    public GenreDTO update(GenreDTO dto) {
        Genre genre = _genreRepository.readOne(dto.getId());
        genre.setName(dto.getName());
        genre.setDescription(dto.getDescription());
        return unwrap(_genreRepository.update(wrap(dto)));
    }

    @Override
    public void delete(Integer id) {
        _genreRepository.delete(id);
    }

    @Override
    public Genre wrap(GenreDTO genreDTO) {
        Genre genre = new Genre();

        genre.setName(genreDTO.getName());
        genre.setDescription(genreDTO.getDescription());

        return genre;
    }

    @Override
    public GenreDTO unwrap(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        genreDTO.setDescription(genre.getDescription());

        return genreDTO;
    }

    private List<GenreDTO> unwrapList(List<Genre> genreEntities) {
        List<GenreDTO> genres = new ArrayList<GenreDTO>();
        for (Genre genre : genreEntities) {
            genres.add(unwrap(genre));
        }
        return genres;
    }

    @Override
    public GenreMap wrapMapping (Book book, GenreDTO genreDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setGenre(_genreRepository.readOne(genreDTO.getId()));
        return genreMap;
    }
}

