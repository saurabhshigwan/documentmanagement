package com.jktechproject.documentmanagement.dto;



import java.io.Serializable;
import java.time.LocalDateTime;

public class DocumentResponse implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    private String filename;
    private String fileType;
    private Long fileSize;
    private String uploadedBy;
    private LocalDateTime uploadedDate;


    public DocumentResponse() {}

    public DocumentResponse(Long id, String filename, String fileType, Long fileSize, String uploadedBy, LocalDateTime uploadedDate) {
        this.id = id;
        this.filename = filename;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadedBy = uploadedBy;
        this.uploadedDate = uploadedDate;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }
}

