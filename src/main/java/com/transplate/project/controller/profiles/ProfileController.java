package com.transplate.project.controller.profiles;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.transplate.project.util.TokenUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProfileController {
	
	private final TokenUtil tokenUtil;
	
	@GetMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		Cookie[] cookies = request.getCookies();
		String accessToken = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("accessToken")) {
					accessToken = cookie.getValue();
				}
			}
		}
		String username = tokenUtil.getUserNameFromToken(accessToken);
		mav.addObject("username", username);
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
