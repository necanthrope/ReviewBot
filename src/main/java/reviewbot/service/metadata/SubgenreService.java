/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reviewbot.dto.metadata.SubgenreDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Subgenre;
import reviewbot.repository.metadata.SubgenreRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
@Service
public class SubgenreService extends AbstractMetadataService <Subgenre, SubgenreDTO> {

    @Autowired
    private SubgenreRepository _subgenreRepository;

    @Override
    public SubgenreDTO create(SubgenreDTO subgenreDTO) {
        return unwrap(_subgenreRepository.create(wrap(subgenreDTO)));
    }

    @Override
    public SubgenreDTO readOne(Long id) {
        return unwrap(_subgenreRepository.readOne(id.intValue()));
    }

    @Override
    public List<SubgenreDTO> readList(Long[] ids) {
        Integer[] idsInt = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idsInt[i] = ids[i].intValue();
        }
        return unwrapList(_subgenreRepository.readList(idsInt));
    }

    @Override
    public List<SubgenreDTO> readRange(Integer offset, Integer length) {
        return unwrapList(_subgenreRepository.readRange(offset, length));
    }

    @Override
    public List<SubgenreDTO> readAll() {
        return unwrapList(_subgenreRepository.readAll());
    }

    @Override
    public SubgenreDTO update(SubgenreDTO dto) {
        Subgenre subgenre = _subgenreRepository.readOne(dto.getId());
        subgenre.setName(dto.getName());
        subgenre.setDescription(dto.getDescription());
        return unwrap(_subgenreRepository.update(wrap(dto)));
    }

    @Override
    public void delete(Integer id) {
        _subgenreRepository.delete(id);
    }

    @Override
    public Subgenre wrap(SubgenreDTO subgenreDTO) {
        Subgenre subgenre = new Subgenre();

        subgenre.setName(subgenreDTO.getName());
        subgenre.setDescription(subgenreDTO.getDescription());

        return subgenre;
    }

    @Override
    public SubgenreDTO unwrap(Subgenre subgenre) {
        SubgenreDTO subgenreDTO = new SubgenreDTO();

        subgenreDTO.setId(subgenre.getSubgenre());
        subgenreDTO.setName(subgenre.getName());
        subgenreDTO.setDescription(subgenre.getDescription());

        return subgenreDTO;
    }

    private List<SubgenreDTO> unwrapList(List<Subgenre> subgenreEntities) {
        List<SubgenreDTO> subgenres = new ArrayList<SubgenreDTO>();
        for (Subgenre subgenre : subgenreEntities) {
            subgenres.add(unwrap(subgenre));
        }
        return subgenres;
    }

    @Override
    public GenreMap wrapMapping (Book book, SubgenreDTO subgenreDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setSubgenre(_subgenreRepository.readOne(subgenreDTO.getId()));
        return genreMap;
    }
}

