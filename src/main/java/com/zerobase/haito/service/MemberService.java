package com.zerobase.haito.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zerobase.haito.exception.PasswordNotMatchingException;
import com.zerobase.haito.exception.UsernameAlreadyInUseException;
import com.zerobase.haito.model.Auth;
import com.zerobase.haito.persistence.entity.MemberEntity;
import com.zerobase.haito.persistence.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService{
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		MemberEntity user = memberRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(String.format("User name (%s) doesn't exist", username)));
		return user;
	}
	
	public MemberEntity register(Auth.SignUp signUp) {
		boolean exists = memberRepository.existsByUsername(signUp.getUsername());
		if (exists) {
			throw new UsernameAlreadyInUseException();
		}
		
		signUp.setPassword(passwordEncoder.encode(signUp.getPassword()));
		MemberEntity user = memberRepository.save(signUp.toEntity());
		return user;
	}
	
	public MemberEntity authenticate(Auth.SignIn signIn) {
		MemberEntity user = memberRepository.findByUsername(signIn.getUsername())
			.orElseThrow(
				() -> new UsernameNotFoundException(String.format(
						"User name (%s) doesn't exist", signIn.getUsername())));
		
		if(!passwordEncoder.matches(signIn.getPassword(), user.getPassword())) {
			throw new PasswordNotMatchingException();
		}
		
		return user;
	}
	
}
