package com.jetbrains.intellij.repository;

import com.jetbrains.intellij.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
