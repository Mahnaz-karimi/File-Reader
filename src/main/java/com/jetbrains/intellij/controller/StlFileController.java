package com.jetbrains.intellij.controller;


import com.jetbrains.intellij.entity.StlFile;
import com.jetbrains.intellij.service.StlFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
public class StlFileController {

    @Autowired
    private StlFileService stlFileService;

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";  // Thymeleaf-tema
    }

    @PostMapping("/upload")
    public String uploadStlFile(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".stl")) {
            model.addAttribute("message", "Please upload a valid STL file.");
            return "upload";
        }

        try {
            System.out.println("Uploading file: " + file.getOriginalFilename());
            stlFileService.saveStlFile(file);

            // Bekræft upload ved at hente den seneste STL-fil
            StlFile latestStlFile = stlFileService.getLatestStlFileFromDatabase();
            if (latestStlFile != null) {
                System.out.println("Uploaded file name: " + latestStlFile.getFileName());
            } else {
                System.out.println("No file found in the database.");
            }

            model.addAttribute("message", "STL file uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload STL file: " + e.getMessage());
        }
        return "upload";
    }

    @GetMapping("/getStlFile")
    public String  getStlFile(Model model) {
        // Hent STL-fil fra databasen
        StlFile latestStlFile  = stlFileService.getLatestStlFileFromDatabase();

        if (latestStlFile  != null) {
            // Hvis STL-filen findes, gem data i model for senere brug i HTML
            System.out.println("STL file data: " + (latestStlFile .getFileName()));
            System.out.println("STL file data length: " + latestStlFile.getData().length);
            // Hvis STL-filen findes, gem data i model for senere brug i HTML
            model.addAttribute("stlFile", latestStlFile);

            byte[] data = latestStlFile.getData();
            String base64Encoded = Base64.getEncoder().encodeToString(data);
            model.addAttribute("stlFileData", base64Encoded);


        } else {
            model.addAttribute("error", "Ingen STL-fil fundet i databasen.");
        }

        return "stl"; // Returner navnet på Thymeleaf-siden (stl.html)
    }

    @GetMapping("/showStlViewer")
    public String showStlViewer() {
        return "stlStatic.html";  // Returnerer stl.html (uden filtypenavnet) fra templates-mappen
    }

    @GetMapping("/testConnection")
    public ResponseEntity<String> testConnection() {
        return ResponseEntity.ok("Database connection is working!");
    }

}
