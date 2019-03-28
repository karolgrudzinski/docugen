package com.grudzinski.docugen.wedding.repositories;

import com.grudzinski.docugen.wedding.model.WeddingCeremony;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WeddingCeremonyRepository extends PagingAndSortingRepository<WeddingCeremony, Long> {
}
