package com.grudzinski.docugen.services;

import com.grudzinski.docugen.model.document.WeddingCeremony;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface WeddingCeremonyService {
    List<WeddingCeremony> getWeddings();
    List<WeddingCeremony> getWeddingsSorted(Sort sort);
    WeddingCeremony findById(Long id);
    WeddingCeremony save(WeddingCeremony weddingCeremony);
    void deleteById(Long id);
}
