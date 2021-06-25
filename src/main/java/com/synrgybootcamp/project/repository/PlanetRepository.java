package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.entity.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet,String> {
//    @Query(value = "SELECT * FROM pockets p WHERE p.user_id = :userId"
//            , nativeQuery = true)
//    Optional<Pocket> findPrimaryPocket(@Param("userId") String userId);
}
