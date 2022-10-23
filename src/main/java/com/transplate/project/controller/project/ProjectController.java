package com.transplate.project.controller.project;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {
	
	@GetMapping("/seekers")
	public ModelAndView seekersProjects(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("project/seekers");
		return mav;
	}
	
	@GetMapping("/side")
	public ModelAndView sideProjects(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("project/side");
		return mav;
	}
}
