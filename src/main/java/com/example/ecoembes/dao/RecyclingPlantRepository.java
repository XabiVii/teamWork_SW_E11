package com.example.ecoembes.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecoembes.entity.RecyclingPlant;

public interface RecyclingPlantRepository  extends JpaRepository<RecyclingPlant, Long> {

    
	Optional<RecyclingPlant> findById(Long id);
}