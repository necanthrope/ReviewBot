/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reviewbot.dto.metadata.AwardDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Award;
import reviewbot.repository.metadata.AwardRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
@Service
public class AwardService extends AbstractMetadataService <Award, AwardDTO> {

    @Autowired
    private AwardRepository _awardRepository;

    @Override
    public AwardDTO create(AwardDTO awardDTO) {
        return unwrap(_awardRepository.create(wrap(awardDTO)));
    }

    @Override
    public AwardDTO readOne(Long id) {
        return unwrap(_awardRepository.readOne(id.intValue()));
    }

    @Override
    public List<AwardDTO> readList(Long[] ids) {
        Integer[] idsInt = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idsInt[i] = ids[i].intValue();
        }
        return unwrapList(_awardRepository.readList(idsInt));
    }

    @Override
    public List<AwardDTO> readRange(Integer offset, Integer length) {
        return unwrapList(_awardRepository.readRange(offset, length));
    }

    @Override
    public List<AwardDTO> readAll() {
        return unwrapList(_awardRepository.readAll());
    }

    @Override
    public AwardDTO update(AwardDTO dto) {
        Award award = _awardRepository.readOne(dto.getId());
        award.setName(dto.getName());
        award.setDescription(dto.getDescription());
        return unwrap(_awardRepository.update(wrap(dto)));
    }

    @Override
    public void delete(Integer id) {
        _awardRepository.delete(id);
    }

    @Override
    public Award wrap(AwardDTO awardDTO) {
        Award award = new Award();

        award.setName(awardDTO.getName());
        award.setDescription(awardDTO.getDescription());

        return award;
    }

    @Override
    public AwardDTO unwrap(Award award) {
        AwardDTO awardDTO = new AwardDTO();

        awardDTO.setId(award.getAward());
        awardDTO.setName(award.getName());
        awardDTO.setDescription(award.getDescription());

        return awardDTO;
    }

    private List<AwardDTO> unwrapList(List<Award> awardEntities) {
        List<AwardDTO> awards = new ArrayList<AwardDTO>();
        for (Award award : awardEntities) {
            awards.add(unwrap(award));
        }
        return awards;
    }

    @Override
    public GenreMap wrapMapping (Book book, AwardDTO awardDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setAward(_awardRepository.readOne(awardDTO.getId()));
        return genreMap;
    }
}

