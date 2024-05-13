package com.zerobase.haito.security;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.zerobase.haito.exception.TokenExpiredException;
import com.zerobase.haito.service.MemberService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
	private static final String KEY_ROLES = "roles";
	
	private final MemberService memberService;
	
	@Value("{spring.jwt.secret}")
	private String secretKey;
	
	public String generateToken(String username, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put(KEY_ROLES, roles);
		
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);
		
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(SignatureAlgorithm.HS512, secretKey)
			.compact();
	}
	
	public String getUsername(String token) {
		return parseClaims(token).getSubject();
	}
	
	public boolean validateToken(String token) {
		if(!StringUtils.hasText(token)) return false;
		
		parseClaims(token);
		return true;
	}
	
	public Authentication getAuthentication(String jwt) {
		UserDetails userDetails = memberService.loadUserByUsername(getUsername(jwt));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	private Claims parseClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		}
	}
}
