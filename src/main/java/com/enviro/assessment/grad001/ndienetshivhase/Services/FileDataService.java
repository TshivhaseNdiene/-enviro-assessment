package com.enviro.assessment.grad001.ndienetshivhase.Services;

import com.enviro.assessment.grad001.ndienetshivhase.Entities.FileData;
import com.enviro.assessment.grad001.ndienetshivhase.Repositories.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileDataService {

    @Autowired
    private FileDataRepository fileDataRepository;

    public FileData saveFile(MultipartFile file) throws IOException {
        FileData fileData = new FileData();
        fileData.setFileName(file.getOriginalFilename());
        fileData.setContent(new String(file.getBytes()));
        fileData.setProcessedData(processData(file.getBytes()));
        fileData.setUploadDate(LocalDateTime.now());

        return fileDataRepository.save(fileData);
    }

    public Optional<FileData> getFileData(Long id) {
        return fileDataRepository.findById(id);
    }

    public List<FileData> getAllFiles() {
        return fileDataRepository.findAll();
    }

    public String processData(byte[] fileBytes) {
        String content = new String(fileBytes);
        String[] lines = content.split("\n");

        double totalReading = 0;
        int count = 0;

        StringBuilder processedData = new StringBuilder("Processed Data:\n");

        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields.length == 2) {
                String timestamp = fields[0].trim();
                double reading;

                try {
                    reading = Double.parseDouble(fields[1].trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                totalReading += reading;
                count++;

                // Append the processed data
                processedData.append("Timestamp: ").append(timestamp)
                        .append(", Reading: ").append(reading).append("\n");
            }
        }

        if (count > 0) {
            double averageReading = totalReading / count;
            processedData.append("\nTotal Readings: ").append(count)
                    .append(", Average Reading: ").append(averageReading);
        } else {
            processedData.append("\nNo valid readings found.");
        }

        return processedData.toString();
    }
}
