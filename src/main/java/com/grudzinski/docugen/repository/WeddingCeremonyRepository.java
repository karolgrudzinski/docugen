package com.grudzinski.docugen.repository;

import com.grudzinski.docugen.model.document.WeddingCeremony;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeddingCeremonyRepository extends PagingAndSortingRepository<WeddingCeremony, Long> {
//    Iterable<WeddingCeremony> findAll(Sort sort);
}
