package com.grudzinski.docugen.wedding.repositories;

import com.grudzinski.docugen.wedding.model.WeddingCeremonySummary;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WeddingCeremonySummaryRepository extends PagingAndSortingRepository<WeddingCeremonySummary, Long> {
}
