package com.jetbrains.intellij.controller;


import com.jetbrains.intellij.service.StlFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class StlFileController {

    @Autowired
    private StlFileService stlFileService;

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";  // Thymeleaf-tema
    }

    @PostMapping("/upload")
    public String uploadStlFile(MultipartFile file, Model model) {
        try {
            stlFileService.saveStlFile(file);
            model.addAttribute("message", "STL file uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload STL file.");
        }
        return "upload";  // Gå tilbage til upload-siden
    }
}
