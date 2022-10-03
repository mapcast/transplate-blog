package com.transplate.project.util;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class TokenUtil {
	private final String secretKey = "hschoToken";
	
	public String getUserInfoFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userInfo").toString();
	}
}
