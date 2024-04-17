package com.gsil.gradleptos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserDataTreat
{
	@GetMapping("/privacy")
	public ModelAndView person_data_treat() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("PersonalDataTreat.html");
		
		return mv;
	}

}
