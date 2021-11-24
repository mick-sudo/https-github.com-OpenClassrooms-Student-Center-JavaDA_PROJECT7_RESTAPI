package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Controller
public class RatingController {
    // TODO: Inject Rating service
	@Autowired
	private RatingRepository ratingRepository;
	
	private static final Logger logger = LogManager.getLogger(BidListController.class);
	
    @RequestMapping("/rating/list")
    public String home(Model model){
    	logger.info("methode home rating");
        // TODO: find all Rating, add to model
    	model.addAttribute("rating",ratingRepository.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
    	logger.info("methode add rating");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
    	logger.info("methode validate rating");
        // TODO: check data valid and save to db, after saving return Rating list
        if (!result.hasErrors()) {
        	ratingRepository.save(rating);
            model.addAttribute("rating", rating);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	logger.info("methode ShowUpdateForm rating");
        // TODO: get Rating by Id and to model then show to the form
				Rating rat = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
		model.addAttribute("rating", rat);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
    	logger.info("methode updateRating rating");
        // TODO: check required fields, if valid call service to update Rating and return Rating list
		if (result.hasErrors()) {
            return "rating/update";
        }
		rating.setId(id);
		ratingRepository.save(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
    	logger.info("methode delete rating");
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
		Rating rat = ratingRepository.findById(id)
		.orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
		ratingRepository.delete(rat);
        return "redirect:/rating/list";
    }
}
