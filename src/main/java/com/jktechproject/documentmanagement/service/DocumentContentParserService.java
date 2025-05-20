package com.jktechproject.documentmanagement.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class DocumentContentParserService {

    public String extractText(MultipartFile file) {
        String fileType = file.getContentType();

        try {
            if (fileType == null) {
                throw new IllegalArgumentException("Unknown file type");
            }

            if (fileType.equals("application/pdf")) {
                return extractFromPDF(file.getInputStream());

            } else if (fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                return extractFromDOCX(file.getInputStream());

            } else if (fileType.equals("text/plain")) {
                return extractFromTXT(file.getInputStream());

            } else {
                throw new UnsupportedOperationException("Unsupported file type: " + fileType);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error extracting text from file", e);
        }
    }

    private String extractFromPDF(InputStream inputStream) throws Exception {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractFromDOCX(InputStream inputStream) throws Exception {
        try (XWPFDocument doc = new XWPFDocument(inputStream)) {
            StringBuilder sb = new StringBuilder();
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
                sb.append(para.getText()).append("\n");
            }
            return sb.toString();
        }
    }

    private String extractFromTXT(InputStream inputStream) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
}

