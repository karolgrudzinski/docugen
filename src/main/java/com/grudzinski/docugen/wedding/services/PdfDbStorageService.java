package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.base.exceptions.NotFoundException;
import com.grudzinski.docugen.wedding.model.PdfFile;
import com.grudzinski.docugen.wedding.repositories.PdfDbStorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PdfDbStorageService implements PdfStorageService {

    private final PdfDbStorageRepository storageRepository;

    public PdfDbStorageService(PdfDbStorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Override
    public PdfFile save(ByteArrayOutputStream document, String fileName) {
        PdfFile pdfFile = new PdfFile(document.toByteArray(), fileName);

        return this.save(pdfFile);
    }

    @Override
    public PdfFile save(PdfFile pdfFile) {
        PdfFile savedPdfFile = storageRepository.save(pdfFile);

        return savedPdfFile;
    }

    @Override
    public PdfFile findById(Long id) {
        Optional<PdfFile> pdfFileOptional = storageRepository.findById(id);
        if(!pdfFileOptional.isPresent()) {
            throw new NotFoundException("Pdf file object not found for Id: " + id);
        }

        return pdfFileOptional.get();
    }

    @Override
    public PdfFile findByUuid(UUID uuid) {
        Optional<PdfFile> pdfFileOptional = storageRepository.findByUuid(uuid);
        if (!pdfFileOptional.isPresent()) {
            throw new NotFoundException("Pdf file object not found for UUID: " + uuid.toString());
        }

        return pdfFileOptional.get();
    }
}
