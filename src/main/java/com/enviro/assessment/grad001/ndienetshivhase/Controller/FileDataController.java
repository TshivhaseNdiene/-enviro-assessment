package com.enviro.assessment.grad001.ndienetshivhase.Controller;

import com.enviro.assessment.grad001.ndienetshivhase.Entities.FileData;
import com.enviro.assessment.grad001.ndienetshivhase.Services.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileDataController {

    @Autowired
    private FileDataService fileDataService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileData savedFile = fileDataService.saveFile(file);
            return new ResponseEntity<>("File uploaded successfully with ID: " + savedFile.getId(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileData> getFileData(@PathVariable Long id) {
        Optional<FileData> fileData = fileDataService.getFileData(id);
        return fileData.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<FileData> getAllFiles() {
        return fileDataService.getAllFiles();
    }
}
