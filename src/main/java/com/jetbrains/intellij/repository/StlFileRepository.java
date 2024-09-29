package com.jetbrains.intellij.repository;

import com.jetbrains.intellij.entity.AppUser;
import com.jetbrains.intellij.entity.StlFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StlFileRepository extends JpaRepository<StlFile, Long> {
    // Hent den nyeste STL-fil baseret på oprettelsesdato
    Optional<StlFile> findTopByOrderByCreatedAtDesc();

}
