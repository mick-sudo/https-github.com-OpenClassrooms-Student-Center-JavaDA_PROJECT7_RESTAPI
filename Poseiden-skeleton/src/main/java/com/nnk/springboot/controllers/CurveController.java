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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Controller
public class CurveController {
    // TODO: Inject Curve Point service
	private static final Logger logger = LogManager.getLogger(BidListController.class);
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
    @RequestMapping("/curvePoint/list")
    public String home(Model model){
    	logger.info("methode home curvePOint");
    	model.addAttribute("curvePoint",curvePointRepository.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
    	logger.info("methode addBidForm curvePOint");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
    	logger.info("methode validate curvePOint");
        // TODO: check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curvePointRepository.save(curvePoint);
            model.addAttribute("curvePoint", curvePoint);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	logger.info("methode sHowUpdateForm curvePOint");
        // TODO: get CurvePoint by Id and to model then show to the form
    	CurvePoint curve = curvePointRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curve Id:" + id));
		model.addAttribute("curvePoint", curve);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
    	logger.info("methode updateBid curvePOint");
    	if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePoint.setCurveId(id);
        curvePointRepository.save(curvePoint);
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	logger.info("methode deleteBid curvePOint");
    	CurvePoint curve = curvePointRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curve Id:" + id));
        curvePointRepository.delete(curve);
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }
}
