package com.jetbrains.intellij.service;

import com.jetbrains.intellij.entity.StlFile;
import com.jetbrains.intellij.repository.StlFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class StlFileService {
    @Autowired
    private StlFileRepository stlFileRepository;

    public void saveStlFile(MultipartFile file) throws IOException {
        StlFile stlFile = new StlFile();
        stlFile.setFileName(file.getOriginalFilename());
        stlFile.setData(file.getBytes());
        stlFileRepository.save(stlFile);
    }
}
