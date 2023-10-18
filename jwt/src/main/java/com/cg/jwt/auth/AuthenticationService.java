package com.cg.jwt.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.jwt.config.JwtService;
import com.cg.jwt.entity.User;
import com.cg.jwt.repository.UserRepository;
import com.cg.jwt.roles.ROLE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

	private final UserRepository repository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	public AuthenticationResponse register(RegisterRequest request) {
		log.info("AuthenticationService: register: Start");
		var user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(ROLE.USER)
				.build();
		repository.save(user);

		var jwtToken = jwtService.generateToken(user);
		log.info("AuthenticationService: register: End");
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		log.info("AuthenticationService: authenticate: Start");
//		authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(
//						request.getEmail(),
//						request.getPassword()
//						)
//					);
		if(request.getEmail()== null) {
			throw new UsernameNotFoundException("Enter username and password!!");
		}
		log.info("AuthenticationService: authenticate: fetching user by email");
		var user = repository.findByEmail(request.getEmail());
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password!!");
		}
		log.info("AuthenticationService: authenticate: generatingToken");
		var jwtToken = jwtService.generateToken(user);
		log.info("AuthenticationService: authenticate: End");
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

}
