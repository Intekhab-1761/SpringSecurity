package com.cg.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.jwt.entity.User;

public interface UserRepository extends JpaRepository<User,String>{

	public User findByEmail(String email);
}
