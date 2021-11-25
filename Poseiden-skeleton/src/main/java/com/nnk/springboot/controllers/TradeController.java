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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Controller
public class TradeController {
    // TODO: Inject Trade service
	@Autowired
	private TradeRepository tradeRepository;
	
	private static final Logger logger = LogManager.getLogger(BidListController.class);

    @RequestMapping("/trade/list")
    public String home(Model model){
    	logger.info("methode home trade");
        // TODO: find all Trade, add to model
    	model.addAttribute("trades",tradeRepository.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
    	logger.info("methode add trade");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
    	logger.info("methode validate trade");
        // TODO: check data valid and save to db, after saving return Trade list
        if (!result.hasErrors()) {
        	tradeRepository.save(trade);
            model.addAttribute("curvePoint", trade);
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	logger.info("methode showUpdateForm trade");
        // TODO: get Trade by Id and to model then show to the form
    			Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
		model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
    	logger.info("methode updateTrade trade");
    	if (result.hasErrors()) {
            return "trade/update";
        }
    	trade.setTradeId(id);
        tradeRepository.save(trade);
        model.addAttribute("trade", trade);
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
    	logger.info("methode delete trade");
    	Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    	tradeRepository.delete(trade);
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        return "redirect:/trade/list";
    }
}
