package com.transplate.project.controller.blog;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/blog")
public class BlogController {
	
	private final RestTemplate restTemplate;
	
	private final ObjectMapper mapper;
		
	//최신 페이지로 리다이렉트 시켜야함
	@GetMapping("/study")
	public ModelAndView study(HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		ModelAndView mav = new ModelAndView();
		
		HttpHeaders header = new HttpHeaders();
		
		HttpEntity<String> entity = new HttpEntity<>(header);
		
		String postlistPath = "http://localhost:8082/post/posts?category=study";
		String postPath = "http://localhost:8082/post/posts/top1?category=study";
		if(request.getParameter("uuid") != null) {
			postPath = "http://localhost:8082/post/posts/" + request.getParameter("uuid");
		}
				
		ResponseEntity<String> listResponse = restTemplate.exchange(postlistPath, HttpMethod.GET, entity, String.class);
		ResponseEntity<String> postResponse = restTemplate.exchange(postPath, HttpMethod.GET, entity, String.class);
		
		JsonNode postlist = mapper.readTree(listResponse.getBody());
		JsonNode post = mapper.readTree(listResponse.getBody());
		
		System.out.println(post.isEmpty());
		
		mav.addObject("post", post);
		mav.addObject("postlist", postlist);
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
