package com.grudzinski.docugen.wedding.repositories;

import com.grudzinski.docugen.wedding.model.PdfFile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PdfDbStorageRepository extends CrudRepository<PdfFile, Long > {
    Optional<PdfFile> findByUuid(UUID uuid);
}
