package com.transplate.project.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transplate.project.dto.JoinDto;
import com.transplate.project.dto.LoginDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DefaultController {
	
	private final RestTemplate restTemplate;
	
	private final ObjectMapper mapper;
		
	@PostMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginDto dto) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LoginDto> entity = new HttpEntity<LoginDto>(dto, header);
		
		ResponseEntity<String> tokenResponse = restTemplate.postForEntity("http://localhost:8081/auth/login", entity, String.class);
		System.out.println(tokenResponse.getBody());
		
		Cookie cookie = new Cookie("accessToken", tokenResponse.getBody());
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return tokenResponse.getBody();
	}
	
	@PostMapping("/signout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("accessToken", null);
	    cookie.setMaxAge(0); 
	    response.addCookie(cookie); 
	}
	
	@PostMapping("/join")
	public String join(HttpServletRequest request, @RequestBody JoinDto dto) throws JsonProcessingException {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(dto), header);
		
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/auth/join", entity, String.class);
		System.out.println(response.getBody());
		
		return response.getBody();
	}
	
}
