/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service;

import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;

import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
public abstract class AbstractService <E, T>{

    public abstract T create(T dto);

    public abstract T readOne(Long id);

    public abstract List<T> readList(Long[] ids);

    public abstract List<T> readRange(Integer offset, Integer length);

    public abstract List<T> readAll();

    public abstract T update(T dto);

    public abstract void delete(Integer id);

    public abstract E wrap (T dto);

    public abstract T unwrap (E entity);

}
