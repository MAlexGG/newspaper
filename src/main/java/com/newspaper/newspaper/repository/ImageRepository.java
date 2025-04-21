package com.newspaper.newspaper.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.newspaper.newspaper.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {}
