package com.transplate.project.controller.profiles;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transplate.project.util.TokenUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProfileController {
	
	private final TokenUtil tokenUtil;
	
	private final ObjectMapper mapper;
	
	@GetMapping("/")
	public String redirect(HttpServletRequest request) {
		return "redirect:/profile";
	}
	
	@GetMapping("/profile")
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
		
		JsonNode userInfo = null;
		if(!accessToken.equals("")) {
			try {
				userInfo = mapper.readTree(tokenUtil.getUserInfoFromToken(accessToken));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		mav.addObject("userInfo", userInfo);
		mav.setViewName("portfolio/main");
		return mav;
	}
	
	@GetMapping("/career")
	public ModelAndView career(HttpServletRequest request) {
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
		
		JsonNode userInfo = null;
		if(!accessToken.equals("")) {
			try {
				userInfo = mapper.readTree(tokenUtil.getUserInfoFromToken(accessToken));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		mav.addObject("userInfo", userInfo);
		mav.setViewName("portfolio/career");
		return mav;
	}
	
	@GetMapping("/skill")
	public ModelAndView skills(HttpServletRequest request) {
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
		
		JsonNode userInfo = null;
		if(!accessToken.equals("")) {
			try {
				userInfo = mapper.readTree(tokenUtil.getUserInfoFromToken(accessToken));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		mav.addObject("userInfo", userInfo);
		mav.setViewName("portfolio/skills");
		return mav;
	}
}
