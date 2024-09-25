package com.jetbrains.intellij.repository;

import com.jetbrains.intellij.entity.StlFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StlFileRepository extends JpaRepository<StlFile, String> {
}
