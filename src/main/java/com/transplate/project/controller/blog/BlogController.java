package com.transplate.project.controller.blog;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		
		String postlistPath = "http://localhost:8082/post/posts?category=study&size=5";
		//String postPath = "http://localhost:8082/post/posts/top1?category=study";
		
		if(request.getParameter("page") != null) {
			postlistPath += "&page=" + request.getParameter("page");
		} 
		
				
		ResponseEntity<String> listResponse = restTemplate.exchange(postlistPath, HttpMethod.GET, entity, String.class);
		
		JsonNode postlist = mapper.readTree(listResponse.getBody());
		
		mav.addObject("postId", postlist.get("content").get(0).get("uuid").textValue());
		mav.addObject("post", postlist.get("content").get(0));
		mav.addObject("postlist", postlist.get("content"));
		mav.addObject("page", postlist.get("pageable").get("pageNumber").intValue());
		mav.addObject("totalPages", postlist.get("totalPages").intValue());
		mav.setViewName("blog/study");
		return mav;
	}
	
	@GetMapping("/study/{postId}")
	public ModelAndView getPostByPostId(HttpServletRequest request, @PathVariable String postId) throws JsonMappingException, JsonProcessingException {
		ModelAndView mav = new ModelAndView();
		
		HttpHeaders header = new HttpHeaders();
		
		HttpEntity<String> entity = new HttpEntity<>(header);
		
		String postlistPath = "http://localhost:8082/post/posts?category=study&size=5";
		String postPath = "http://localhost:8082/post/posts/" + postId;
		if(request.getParameter("uuid") != null) {
			postPath = "http://localhost:8082/post/posts/" + request.getParameter("uuid");
		}
				
		ResponseEntity<String> listResponse = restTemplate.exchange(postlistPath, HttpMethod.GET, entity, String.class);
		ResponseEntity<String> postResponse = restTemplate.exchange(postPath, HttpMethod.GET, entity, String.class);
		
		JsonNode postlist = mapper.readTree(listResponse.getBody());
		JsonNode post = mapper.readTree(postResponse.getBody());
		
		System.out.println(post);
		
		mav.addObject("postId", post.get("uuid").textValue());
		mav.addObject("post", post);
		mav.addObject("postlist", postlist.get("content"));
		mav.addObject("page", postlist.get("pageable").get("pageNumber").intValue());
		mav.addObject("totalPages", postlist.get("totalPages").intValue());
		mav.setViewName("blog/study");
		return mav;
	}
	
	@GetMapping("/dev")
	public ModelAndView dev(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("blog/dev");
		return mav;
	}
	
	@GetMapping("/write")
	public ModelAndView writePostPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("blog/write");
		return mav;
	}
	
	@GetMapping("/modify/{postId}")
	public ModelAndView modifyPostPage(HttpServletRequest request, @PathVariable String postId) throws JsonMappingException, JsonProcessingException {
		ModelAndView mav = new ModelAndView();
		
		HttpHeaders header = new HttpHeaders();
		
		HttpEntity<String> entity = new HttpEntity<>(header);
		
		String postPath = "http://localhost:8082/post/posts/" + postId;
				
		ResponseEntity<String> postResponse = restTemplate.exchange(postPath, HttpMethod.GET, entity, String.class);
		
		JsonNode post = mapper.readTree(postResponse.getBody());
		
		mav.addObject("post", post);
		mav.setViewName("blog/modify");
		return mav;
	}
}
