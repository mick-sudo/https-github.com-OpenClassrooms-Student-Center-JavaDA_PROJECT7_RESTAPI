package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

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

import java.sql.Timestamp;

import javax.validation.Valid;

@Controller
public class BidListController {

	@Autowired
	private BidListRepository bidListRepository;

	private static final Logger logger = LogManager.getLogger(BidListController.class);

	@RequestMapping("/bidList/list")
	public String home(Model model) {
		logger.info("methode home bidList");
		// TODO: call service find all bids to show to the view
		model.addAttribute("bidList", bidListRepository.findAll());
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid) {
		logger.info("methode add bidList");
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		logger.info("methode validate bidList");
		// TODO: check data valid and save to db, after saving return bid list
		if (!result.hasErrors()) {
			bid.setCreationDate(new Timestamp(System.currentTimeMillis()));
			bidListRepository.save(bid);
			model.addAttribute("bidList", bidListRepository.findAll());
			return "redirect:/bidList/list";
		}
		return "bidList/add";

	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("methode showUpdateForm bidList");
		// TODO: get Bid by Id and to model then show to the form
		BidList bid = bidListRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
		model.addAttribute("bidList", bid);
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		logger.info("methode updateBid bidList");
		// TODO: check required fields, if valid call service to update Bid and return
		// validation front; on ne fait jamais confiance a lutilisateur
		if (result.hasErrors()) {
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListRepository.save(bidList);
        //model.addAttribute("bidList", bidListRepository.findAll());
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		logger.info("methode deleteBid bidList");
		// TODO: Find Bid by Id and delete the bid, return to Bid list
		BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid ID" + id));
        bidListRepository.delete(bidList);
        
		return "redirect:/bidList/list";
	}
}
