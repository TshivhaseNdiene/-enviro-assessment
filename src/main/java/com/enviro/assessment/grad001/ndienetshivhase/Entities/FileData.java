package com.enviro.assessment.grad001.ndienetshivhase.Entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Lob
    private String content;

    @Lob
    private String processedData;

    private LocalDateTime uploadDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProcessedData() {
        return processedData;
    }

    public void setProcessedData(String processedData) {
        this.processedData = processedData;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }
}
