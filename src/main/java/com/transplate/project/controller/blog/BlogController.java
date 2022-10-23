package com.transplate.project.controller.blog;

import javax.servlet.http.Cookie;
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
import com.transplate.project.util.TokenUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/blog")
public class BlogController {
	
	private final RestTemplate restTemplate;
	
	private final ObjectMapper mapper;
	
	private final TokenUtil tokenUtil;
		
	//최신 페이지로 리다이렉트 시켜야함
	@GetMapping("")
	public ModelAndView post(HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
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
		
		
		String postlistPath = "http://localhost:8082/post/posts?size=5";
		//String postPath = "http://localhost:8082/post/posts/top1?category=study";
		
		if(request.getParameter("category") != null) {
			postlistPath += "&category=" + request.getParameter("category");
		}
		
		HttpHeaders header = new HttpHeaders();
		
		HttpEntity<String> entity = new HttpEntity<>(header);
		
		
		if(request.getParameter("page") != null) {
			postlistPath += "&page=" + request.getParameter("page");
		} 
		
				
		ResponseEntity<String> listResponse = restTemplate.exchange(postlistPath, HttpMethod.GET, entity, String.class);
		
		JsonNode postlist = mapper.readTree(listResponse.getBody());
		
		
		if(postlist.get("content").size() > 0) {
			mav.addObject("postId", postlist.get("content").get(0).get("uuid").textValue());
			mav.addObject("post", postlist.get("content").get(0));
		} else {
			mav.addObject("postId", null);
			mav.addObject("post", null);
		}
		mav.addObject("userInfo", userInfo);
		mav.addObject("postlist", postlist.get("content"));
		mav.addObject("page", postlist.get("pageable").get("pageNumber").intValue());
		mav.addObject("category", request.getParameter("category"));
		mav.addObject("totalPages", postlist.get("totalPages").intValue());
		mav.setViewName("blog/posts");
		return mav;
	}
	
	@GetMapping("/{postId}")
	public ModelAndView getPostByPostId(HttpServletRequest request, @PathVariable String postId) throws JsonMappingException, JsonProcessingException {
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
		
		String postlistPath = "http://localhost:8082/post/posts?size=5";
		String postPath = "http://localhost:8082/post/posts/" + postId;
		
		if(request.getParameter("category") != null) {
			postlistPath += "&category=" + request.getParameter("category");
		}
		
		
		HttpHeaders header = new HttpHeaders();
		
		HttpEntity<String> entity = new HttpEntity<>(header);
		
		if(request.getParameter("uuid") != null) {
			postPath = "http://localhost:8082/post/posts/" + request.getParameter("uuid");
		}
				
		ResponseEntity<String> listResponse = restTemplate.exchange(postlistPath, HttpMethod.GET, entity, String.class);
		ResponseEntity<String> postResponse = restTemplate.exchange(postPath, HttpMethod.GET, entity, String.class);
		
		JsonNode postlist = mapper.readTree(listResponse.getBody());
		JsonNode post = mapper.readTree(postResponse.getBody());
		
		mav.addObject("userInfo", userInfo);
		mav.addObject("postId", post.get("uuid").textValue());
		mav.addObject("post", post);
		mav.addObject("postlist", postlist.get("content"));
		mav.addObject("page", postlist.get("pageable").get("pageNumber").intValue());
		mav.addObject("totalPages", postlist.get("totalPages").intValue());
		mav.setViewName("blog/posts");
		return mav;
	}
	
	@GetMapping("/write")
	public ModelAndView writePostPage(HttpServletRequest request) {
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
		mav.setViewName("blog/write");
		return mav;
	}
	
	@GetMapping("/modify/{postId}")
	public ModelAndView modifyPostPage(HttpServletRequest request, @PathVariable String postId) throws JsonMappingException, JsonProcessingException {
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
		
		HttpHeaders header = new HttpHeaders();
		
		HttpEntity<String> entity = new HttpEntity<>(header);
		
		String postPath = "http://localhost:8082/post/posts/" + postId;
				
		ResponseEntity<String> postResponse = restTemplate.exchange(postPath, HttpMethod.GET, entity, String.class);
		
		JsonNode post = mapper.readTree(postResponse.getBody());
		
		mav.addObject("userInfo", userInfo);
		mav.addObject("post", post);
		mav.setViewName("blog/modify");
		return mav;
	}
}
