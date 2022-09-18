package com.transplate.project.controller.rest;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transplate.project.dto.PostDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/blog")
@RequiredArgsConstructor
public class BlogRestController {
		
	@PostMapping(value = "/write")
	public String writePost(HttpServletRequest request, @RequestBody PostDto dto) throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		
		Cookie[] cookies = request.getCookies();
		String accessToken = "Bearer ";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("accessToken")) {
					accessToken += cookie.getValue();
				}
			}
		}
		
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", accessToken);
		header.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<PostDto> entity = new HttpEntity<PostDto>(dto, header);
		
		String path = "http://localhost:8082/post/posts";
		ResponseEntity<String> response = restTemplate.postForEntity(path, entity, String.class);
		return response.getBody();
	}
	
	@PostMapping(value = "/modify")
	public String modifyPost(HttpServletRequest request, @RequestBody PostDto dto) throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		
		Cookie[] cookies = request.getCookies();
		String accessToken = "Bearer ";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("accessToken")) {
					accessToken += cookie.getValue();
				}
			}
		}
		
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", accessToken);
		header.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<PostDto> entity = new HttpEntity<PostDto>(dto, header);
		
		String path = "http://localhost:8082/post/posts";
		ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.PUT, entity, String.class);
		return response.getBody();
	}
	
	@PostMapping(value = "/delete/{postId}")
	public String deletePost(HttpServletRequest request, @PathVariable String postId) throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		
		Cookie[] cookies = request.getCookies();
		String accessToken = "Bearer ";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("accessToken")) {
					accessToken += cookie.getValue();
				}
			}
		}
		
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", accessToken);
		header.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(header);
		
		String path = "http://localhost:8082/post/posts/" + postId;
		ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.DELETE, entity, String.class);
		return response.getBody();
	}
}
