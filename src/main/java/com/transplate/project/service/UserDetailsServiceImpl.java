package com.transplate.project.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transplate.project.dto.LoginDto;
import com.transplate.project.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		RestTemplate restTemplate = new RestTemplate();
		
		ObjectMapper mapper = new ObjectMapper();
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
	
		HttpEntity<LoginDto> entity = new HttpEntity<LoginDto>(header);
		
		ResponseEntity<String> userResponse = restTemplate.postForEntity("http://localhost:8081/auth/user", entity, String.class);
		
		try {
			JsonNode userNode = mapper.readTree(userResponse.getBody());
			User user = new User();
			user.setRole(userNode.get("role").textValue());
			user.setUserId(userNode.get("userId").textValue());
			user.setPassword(userNode.get("userPassword").textValue());
			return user;
		} catch (Exception e) {
			return new User();
		}
	}

}
