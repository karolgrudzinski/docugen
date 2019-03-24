package com.grudzinski.docugen.repository;

import com.grudzinski.docugen.model.document.WeddingCeremony;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface WeddingCeremonyRepository extends CrudRepository<WeddingCeremony, Long> {
    Iterable<WeddingCeremony> findAll(Sort sort);
}
