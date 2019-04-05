package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.wedding.model.WeddingCeremony;
import com.grudzinski.docugen.wedding.model.WeddingCeremonySummary;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface WeddingCeremonyService {
    List<WeddingCeremony> getWeddings();
    List<WeddingCeremony> getWeddingsSorted(Sort sort);
    List<WeddingCeremonySummary> getWeddingSummariesSorted(Sort sort);
    WeddingCeremony findById(Long id);
    WeddingCeremony save(WeddingCeremony weddingCeremony);
    void deleteById(Long id);
}
