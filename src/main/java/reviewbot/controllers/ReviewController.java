package reviewbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reviewbot.dao.ReviewDAO;
import reviewbot.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewDAO _reviewDAO;

    @RequestMapping(method= RequestMethod.GET)
    public List<Review> listReview() {

        List<Review> reviews = new ArrayList<Review>();

        try {
            reviews = _reviewDAO.getAll();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }
}
