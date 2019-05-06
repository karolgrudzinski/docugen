package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.wedding.model.PdfFile;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public interface PdfStorageService {
    PdfFile save(ByteArrayOutputStream document, String fileName);
    PdfFile save(PdfFile pdfFile);
    PdfFile findById(Long id);
    PdfFile findByUuid(UUID uuid);
}
