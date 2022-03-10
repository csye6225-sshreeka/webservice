package com.springboot.springbootapp.repository;

import com.springboot.springbootapp.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByUserId(int userId);
}