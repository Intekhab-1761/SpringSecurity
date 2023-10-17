package com.cg.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.jwt.entity.User;

public interface UserRepository extends JpaRepository<User,String>{

	Optional<User> findByEmail(String email);
}
