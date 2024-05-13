package com.zerobase.haito.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.haito.model.Auth;
import com.zerobase.haito.persistence.entity.MemberEntity;
import com.zerobase.haito.security.TokenProvider;
import com.zerobase.haito.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Transactional
public class AuthController {
	private final MemberService memberService;
	
	private final TokenProvider tokenProvider;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody Auth.SignUp request) {
		MemberEntity user = memberService.register(request);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signUp(@RequestBody Auth.SignIn request) {
		MemberEntity user = memberService.authenticate(request);
		String token = tokenProvider.generateToken(user.getUsername(), user.getRoles());
		return ResponseEntity.ok(token);
	}
}
