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
import reviewbot.dao.ReviewDAO;
import reviewbot.entity.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@RestController
public class ReviewController {
    @Autowired
    private ReviewDAO _reviewDAO;

    @RequestMapping(value="/reviews", method=RequestMethod.GET, produces="application/json")
    public List<Review> getReviews() {

        List<Review> reviews = new ArrayList<Review>();

        try {
            reviews = _reviewDAO.readAll();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    @RequestMapping(value="/review", method=RequestMethod.GET, produces="application/json")
    public Review getReviewById(
            @RequestParam(value="id") String idStr) {

        Review review;

        if(idStr == null) {
            review =  new Review();
            return review;
        }

        review = _reviewDAO.readOne(new Long(Integer.parseInt(idStr)));
        return review;

    }
}
