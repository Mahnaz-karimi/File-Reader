package com.jetbrains.intellij.controller;


import com.jetbrains.intellij.service.StlFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

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
            System.out.println("Uploading file: " + file.getOriginalFilename()); // Debug
            stlFileService.saveStlFile(file);
            model.addAttribute("message", "STL file uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload STL file: " + e.getMessage());
        }
        return "upload";  // Gå tilbage til upload-siden
    }
    @GetMapping("/getStlFile")
    public ResponseEntity<byte[]> getStlFile() {
        byte[] stlFileData = stlFileService.getLatestStlFileFromDatabase();
        if (stlFileData != null) {
            System.out.println("STL file found, size: " + stlFileData.length);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"latest.stl\"")
                    .body(stlFileData);
        } else {
            System.out.println("No STL file found in the database.");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        byte[] latestStlFile = stlFileService.getLatestStlFileFromDatabase();
        if (latestStlFile != null) {
            System.out.println("STL file found, size: " + latestStlFile.length);
            // Konverter byte[] til base64-streng
            String base64Image = Base64.getEncoder().encodeToString(latestStlFile);
            model.addAttribute("latestStlFile", "data:image/png;base64," + base64Image);  // Sørg for, at det er en gyldig MIME-type
        } else {
            System.out.println("No STL file found in the controller.");
            model.addAttribute("latestStlFile", null);
        }
        return "upload"; // Din HTML-fil hedder upload.html
    }
}
