/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service.metadata;

import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.service.AbstractService;

/**
 * Created by jtidwell on 4/30/2015.
 */
public abstract class AbstractMetadataService<E, T> extends AbstractService<E, T>{

    public abstract GenreMap wrapMapping (Book book, T dto);

}
