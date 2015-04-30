/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reviewbot.dto.metadata.FormatDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Format;
import reviewbot.repository.metadata.FormatRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
@Service
public class FormatService extends AbstractMetadataService <Format, FormatDTO> {

    @Autowired
    private FormatRepository _formatRepository;

    @Override
    public FormatDTO create(FormatDTO formatDTO) {
        return unwrap(_formatRepository.create(wrap(formatDTO)));
    }

    @Override
    public FormatDTO readOne(Long id) {
        return unwrap(_formatRepository.readOne(id.intValue()));
    }

    @Override
    public List<FormatDTO> readList(Long[] ids) {
        Integer[] idsInt = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idsInt[i] = ids[i].intValue();
        }
        return unwrapList(_formatRepository.readList(idsInt));
    }

    @Override
    public List<FormatDTO> readRange(Integer offset, Integer length) {
        return unwrapList(_formatRepository.readRange(offset, length));
    }

    @Override
    public List<FormatDTO> readAll() {
        return unwrapList(_formatRepository.readAll());
    }

    @Override
    public FormatDTO update(FormatDTO dto) {
        Format format = _formatRepository.readOne(dto.getId());
        format.setName(dto.getName());
        format.setDescription(dto.getDescription());
        return unwrap(_formatRepository.update(wrap(dto)));
    }

    @Override
    public void delete(Integer id) {
        _formatRepository.delete(id);
    }

    @Override
    public Format wrap(FormatDTO formatDTO) {
        Format format = new Format();

        format.setName(formatDTO.getName());
        format.setDescription(formatDTO.getDescription());

        return format;
    }

    @Override
    public FormatDTO unwrap(Format format) {
        FormatDTO formatDTO = new FormatDTO();

        formatDTO.setId(format.getFormat());
        formatDTO.setName(format.getName());
        formatDTO.setDescription(format.getDescription());

        return formatDTO;
    }

    private List<FormatDTO> unwrapList(List<Format> formatEntities) {
        List<FormatDTO> formats = new ArrayList<FormatDTO>();
        for (Format format : formatEntities) {
            formats.add(unwrap(format));
        }
        return formats;
    }

    @Override
    public GenreMap wrapMapping (Book book, FormatDTO formatDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setFormat(_formatRepository.readOne(formatDTO.getId()));
        return genreMap;
    }
}

