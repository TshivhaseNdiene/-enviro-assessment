package com.enviro.assessment.grad001.ndienetshivhase.service;

import com.enviro.assessment.grad001.ndienetshivhase.Entities.FileData;
import com.enviro.assessment.grad001.ndienetshivhase.Repositories.FileDataRepository;
import com.enviro.assessment.grad001.ndienetshivhase.Services.FileDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileDataServiceTest {

    @Mock
    private FileDataRepository fileDataRepository;

    @InjectMocks
    private FileDataService fileDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFile() throws IOException {
        // Arrange
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.csv");
        when(mockFile.getBytes()).thenReturn("2024-08-12 12:00, 23.5\n2024-08-12 12:05, 24.1\n2024-08-12 12:10, invalid_number\n2024-08-12 12:15, 22.8".getBytes());

        FileData mockSavedFile = new FileData();
        mockSavedFile.setId(1L);
        mockSavedFile.setProcessedData("Average Reading: 23.5");
        when(fileDataRepository.save(any(FileData.class))).thenReturn(mockSavedFile);

        // Act
        FileData result = fileDataService.saveFile(mockFile);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result.getProcessedData());
        assertTrue(result.getProcessedData().contains("Average Reading:"));
        verify(fileDataRepository, times(1)).save(any(FileData.class));
    }


    @Test
    void testGetFileData() {
        // Arrange
        FileData mockFileData = new FileData();
        mockFileData.setId(1L);
        when(fileDataRepository.findById(1L)).thenReturn(Optional.of(mockFileData));

        // Act
        Optional<FileData> result = fileDataService.getFileData(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(fileDataRepository, times(1)).findById(1L);
    }

    @Test
    void testProcessData() {
        // Arrange
        byte[] fileBytes = "2024-08-12 12:00, 23.5\n2024-08-12 12:05, 24.1\n2024-08-12 12:10, invalid_number\n2024-08-12 12:15, 22.8".getBytes();

        // Act
        String processedData = fileDataService.processData(fileBytes);

        // Assert
        assertTrue(processedData.contains("Timestamp: 2024-08-12 12:00, Reading: 23.5"));
        assertTrue(processedData.contains("Total Readings: 3"));
        assertTrue(processedData.contains("Average Reading: 23.4666"));
    }
}
