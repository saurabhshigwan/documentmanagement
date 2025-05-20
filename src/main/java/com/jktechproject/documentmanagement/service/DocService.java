package com.jktechproject.documentmanagement.service;

import com.jktechproject.documentmanagement.entity.Document;
import com.jktechproject.documentmanagement.entity.User;
import com.jktechproject.documentmanagement.repository.DocumentRepo;
import com.jktechproject.documentmanagement.repository.UserRepoRole;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DocService {

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private UserRepoRole userRepoRole;

    @Autowired
    private DocumentContentParserService documentContentParserService;
    public Document saveDocument(MultipartFile file,String uploadedBy) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User uploader = userRepoRole.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        //Long fileSize = file.getSize();
        String content = documentContentParserService.extractText(file);
    String extractedContent = documentContentParserService.extractText(file);
        Document doc = new Document();

        doc.setFilename(fileName);
        doc.setFileSize(file.getSize());
        doc.setUploadedBy(uploadedBy);
        doc.setUploadedDate(LocalDateTime.now());
        doc.setFileType(contentType);
       // doc.setContent(content.getBytes());
        doc.setParsedText(extractedContent);
        return documentRepo.save(doc);
    }

    public List<Document> searchDocuments(String uploadedBy, String fileType, String parsedText, String filename) {
        return documentRepo.searchDocument(uploadedBy, fileType, parsedText, filename);
    }

    private String extractContentFromDoc(MultipartFile file) throws Exception {
        String type = Objects.requireNonNull(file.getContentType()).toLowerCase();

        if (type.equals("application/pdf")) {
            try (PDDocument document = PDDocument.load(file.getInputStream())) {
                return new PDFTextStripper().getText(document);
            }
        } else if (type.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            try (XWPFDocument doc = new XWPFDocument(file.getInputStream())) {
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                return extractor.getText();
            }
        } else if (type.equals("text/plain")) {
            return new String(file.getBytes());
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + type);
        }
    }

}
