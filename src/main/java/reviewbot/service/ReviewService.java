/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reviewbot.dto.ReviewDTO;
import reviewbot.entity.Review;
import reviewbot.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
@Service
public class ReviewService extends AbstractService<Review, ReviewDTO> {

    @Autowired
    private ReviewRepository _reviewRepository;

    @Override
    public ReviewDTO create(ReviewDTO dto) {
        return null;
    }

    @Override
    public ReviewDTO readOne(Long id) {
        return unwrap(_reviewRepository.readOne(id.intValue()));
    }

    @Override
    public List<ReviewDTO> readList(Long[] ids) {
        return null;
    }

    @Override
    public List<ReviewDTO> readRange(Integer offset, Integer length) {
        return null;
    }

    @Override
    public List<ReviewDTO> readAll() {
        return null;
    }

    @Override
    public ReviewDTO update(ReviewDTO dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Review wrap(ReviewDTO dto) {
        return null;
    }

    private List<ReviewDTO> unwrapList(List<Review> reviewEntities) {
        List<ReviewDTO> reviews = new ArrayList<ReviewDTO>();
        for (Review review : reviewEntities) {
            reviews.add(unwrap(review));
        }
        return reviews;
    }

    @Override
    public ReviewDTO unwrap(Review entity) {
        return null;
    }
}
