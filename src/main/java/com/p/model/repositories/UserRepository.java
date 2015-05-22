package com.p.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.p.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 
    public User findByEmail(String email);
}