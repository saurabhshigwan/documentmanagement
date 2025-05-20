package com.jktechproject.documentmanagement.repository;

import com.jktechproject.documentmanagement.entity.Document;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface DocumentRepo extends JpaRepository<Document, Long> {

    List<Document> findByUploadedBy(String uploadedBy);

/*  @Query("SELECT d FROM Documents d WHERE " +
            "(:uploadedBy IS NULL OR d.uploadedBy = :uploadedBy) AND " +
            "(:fileType IS NULL OR d.fileType = :fileType) AND " +
            "(:parsedText IS NULL OR LOWER(CAST(d.parsedText AS string)) LIKE LOWER(CONCAT('%', :parsedText, '%'))) AND " +
            "(:filename IS NULL OR LOWER(CAST(d.filename AS string)) LIKE LOWER(CONCAT('%', :filename, '%')))")
    List<Document> searchDocument(
            @Param("uploadedBy") String uploadedBy,
            @Param("fileType") String fileType,
            @Param("parsedText") String parsedText,
            @Param("filename") String filename
    );*/

    @Query(value = """
    SELECT * FROM documents d
    WHERE (:uploadedBy IS NULL OR d.uploaded_by = :uploadedBy)
      AND (:fileType IS NULL OR d.file_type = :fileType)
      AND (:parsedText IS NULL OR LOWER(CAST(d.parsed_text AS TEXT)) LIKE LOWER(CONCAT('%', :parsedText, '%')))
      AND (:filename IS NULL OR LOWER(d.filename) LIKE LOWER(CONCAT('%', :filename, '%')))
    """, nativeQuery = true)
    List<Document> searchDocumentNativeSQL(
            @Param("uploadedBy") String uploadedBy,
            @Param("fileType") String fileType,
            @Param("parsedText") String parsedText,
            @Param("filename") String filename
    );

}
