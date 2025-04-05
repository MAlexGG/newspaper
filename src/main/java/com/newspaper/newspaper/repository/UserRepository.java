package com.newspaper.newspaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newspaper.newspaper.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
