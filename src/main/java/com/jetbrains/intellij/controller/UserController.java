package com.jetbrains.intellij.controller;

import com.jetbrains.intellij.entity.AppUser;
import com.jetbrains.intellij.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // restcontroller burges når sataene sendes som en JSON-struktur (eller XML) i request-bodyen.
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/users")
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public AppUser createUser(@RequestBody AppUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @GetMapping("/users/{id}") public AppUser getStudent(@PathVariable long id){
        AppUser appUser = userRepository.findById(id).get(); return appUser;
    }
    @DeleteMapping("/users/delete/{id}") public void removeStudent(@PathVariable long id){
        AppUser appUser = userRepository.findById(id).get(); userRepository.delete(appUser);
    }
}
