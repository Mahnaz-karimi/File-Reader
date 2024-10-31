package com.jetbrains.intellij.service;

import com.jetbrains.intellij.entity.StlFile;
import com.jetbrains.intellij.repository.StlFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
@Service
public class StlFileService {
    @Autowired
    private StlFileRepository stlFileRepository;

    public void saveStlFile(MultipartFile file) throws IOException {
        StlFile stlFile = new StlFile();
        stlFile.setFileName(file.getOriginalFilename());
        stlFile.setData(file.getBytes());
        stlFile.setCreatedAt(LocalDateTime.now()); // Sæt createdAt til nuværende tid
        stlFileRepository.save(stlFile);
    }
    // Metode til at hente sidste STL-fil fra databasen
    @Transactional // Sørg for, at der er en transaktion
    public StlFile getLatestStlFileFromDatabase() {
        try {
            Optional<StlFile> optionalStlFile = stlFileRepository.findTopByOrderByCreatedAtDesc();
            if (optionalStlFile.isPresent()) {
                System.out.println("STL file found: " + optionalStlFile.get().getFileName());
                return optionalStlFile.get(); // Returner hele StlFile-objektet
            } else {
                System.out.println("No STL file found in the database.");
                throw new RuntimeException("STL-fil ikke fundet");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving STL file: " + e.getMessage());
            throw new RuntimeException("Fejl ved hentning af STL-fil");
        }
    }

    @Transactional(readOnly = true)
    public StlFile getStlFileById(Long id) {
        return stlFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("STL-fil ikke fundet med ID: " + id));
    }
}
