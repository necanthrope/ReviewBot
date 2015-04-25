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
import reviewbot.dto.ReviewDTO;
import reviewbot.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@RestController
public class ReviewController {
    @Autowired
    private ReviewRepository _reviewRepo;

    @RequestMapping(value="/readAllReviews", method=RequestMethod.GET, produces="application/json")
    public List<ReviewDTO> readReviews() {

        List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();

        try {
            reviewDTOs = _reviewRepo.readAll();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return reviewDTOs;
    }

    @RequestMapping(value="/readReview", method=RequestMethod.GET, produces="application/json")
    public ReviewDTO readReview(
            @RequestParam(value="id") String idStr) {
        if(idStr == null) {
            return new ReviewDTO();
        }
        return _reviewRepo.readOne(new Integer(Integer.parseInt(idStr)));

    }
}
