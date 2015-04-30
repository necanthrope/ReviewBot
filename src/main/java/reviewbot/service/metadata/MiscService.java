/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reviewbot.dto.metadata.MiscDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Misc;
import reviewbot.repository.metadata.MiscRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
@Service
public class MiscService extends AbstractMetadataService <Misc, MiscDTO> {

    @Autowired
    private MiscRepository _miscRepository;

    @Override
    public MiscDTO create(MiscDTO miscDTO) {
        return unwrap(_miscRepository.create(wrap(miscDTO)));
    }

    @Override
    public MiscDTO readOne(Long id) {
        return unwrap(_miscRepository.readOne(id.intValue()));
    }

    @Override
    public List<MiscDTO> readList(Long[] ids) {
        Integer[] idsInt = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idsInt[i] = ids[i].intValue();
        }
        return unwrapList(_miscRepository.readList(idsInt));
    }

    @Override
    public List<MiscDTO> readRange(Integer offset, Integer length) {
        return unwrapList(_miscRepository.readRange(offset, length));
    }

    @Override
    public List<MiscDTO> readAll() {
        return unwrapList(_miscRepository.readAll());
    }

    @Override
    public MiscDTO update(MiscDTO dto) {
        Misc misc = _miscRepository.readOne(dto.getId());
        misc.setName(dto.getName());
        misc.setDescription(dto.getDescription());
        return unwrap(_miscRepository.update(wrap(dto)));
    }

    @Override
    public void delete(Integer id) {
        _miscRepository.delete(id);
    }

    @Override
    public Misc wrap(MiscDTO miscDTO) {
        Misc misc = new Misc();

        misc.setName(miscDTO.getName());
        misc.setDescription(miscDTO.getDescription());

        return misc;
    }

    @Override
    public MiscDTO unwrap(Misc misc) {
        MiscDTO miscDTO = new MiscDTO();

        miscDTO.setId(misc.getMisc());
        miscDTO.setName(misc.getName());
        miscDTO.setDescription(misc.getDescription());

        return miscDTO;
    }

    private List<MiscDTO> unwrapList(List<Misc> miscEntities) {
        List<MiscDTO> miscs = new ArrayList<MiscDTO>();
        for (Misc misc : miscEntities) {
            miscs.add(unwrap(misc));
        }
        return miscs;
    }

    @Override
    public GenreMap wrapMapping (Book book, MiscDTO miscDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setMisc(_miscRepository.readOne(miscDTO.getId()));
        return genreMap;
    }
}

