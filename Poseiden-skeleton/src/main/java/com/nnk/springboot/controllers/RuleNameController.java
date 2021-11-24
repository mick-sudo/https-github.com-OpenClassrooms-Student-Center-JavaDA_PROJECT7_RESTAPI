package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Controller
public class RuleNameController {
    // TODO: Inject RuleName service
	@Autowired
	private RuleNameRepository ruleNameRepository;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // TODO: find all RuleName, add to model
    	model.addAttribute("rulenames", ruleNameRepository.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        if (!result.hasErrors()) {
        	ruleNameRepository.save(ruleName);
            model.addAttribute("rating", ruleName);
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
    	RuleName ruleName = ruleNameRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curve Id:" + id));
		model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "curvePoint/update";
        }
    	ruleName.setId(id);
        ruleNameRepository.save(ruleName);
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
    	RuleName ruleName = ruleNameRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
    	ruleNameRepository.delete(ruleName);
        return "redirect:/ruleName/list";
    }
}
