package com.jktechproject.documentmanagement.controller;

import com.jktechproject.documentmanagement.dto.DocumentResponse;
import com.jktechproject.documentmanagement.entity.Document;
import com.jktechproject.documentmanagement.service.DocService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/jktech/docman/doc")
public class DocumentController {

    @Autowired
    private DocService docService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
        try {

            Document saved = docService.saveDocument(file, userDetails.getUsername());
            return ResponseEntity.ok("Document uploaded successfully: " + saved.getFilename());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<DocumentResponse>> searchDocuments(
            @RequestParam(required = false) String uploadedBy,
            @RequestParam(required = false) String fileType,
            @RequestParam(required = false) String parsedText,
            @RequestParam(required = false) String filename
    ) {
        uploadedBy = (uploadedBy != null && !uploadedBy.isBlank()) ? uploadedBy : null;
        fileType = (fileType != null && !fileType.isBlank()) ? fileType : null;
        parsedText = (parsedText != null && !parsedText.isBlank()) ? parsedText : null;
        filename = (filename != null && !filename.isBlank()) ? filename : null;
        System.out.println("==> Controller Called");
        System.out.printf("Searching with: uploadedBy=%s, fileType=%s, parsedText=%s, filename=%s%n",
                uploadedBy, fileType, parsedText, filename);
        List<DocumentResponse> results = docService.searchDocuments(uploadedBy, fileType, parsedText, filename);
        return ResponseEntity.ok(results);
    }

    @PostConstruct
    public void init() {
        System.out.println(" DocumentController initialized");
    }
}