package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet, String> {
  Optional<Planet> findFirstBySequence(Integer sequence);
  List<Planet> findAllByOrderBySequenceAsc();
}
