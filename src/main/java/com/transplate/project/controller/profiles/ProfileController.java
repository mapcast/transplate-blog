package com.transplate.project.controller.profiles;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {
	@GetMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portfolio/main");
		return mav;
	}
	
	@GetMapping("/career")
	public ModelAndView career(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portfolio/career");
		return mav;
	}
	
	@GetMapping("/skill")
	public ModelAndView skills(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("portfolio/skills");
		return mav;
	}
}
