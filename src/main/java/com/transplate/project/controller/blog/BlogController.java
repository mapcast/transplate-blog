package com.transplate.project.controller.blog;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {
	@GetMapping("/study")
	public ModelAndView study(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("blog/study");
		return mav;
	}
	
	@GetMapping("/dev")
	public ModelAndView dev(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("blog/dev");
		return mav;
	}
}
