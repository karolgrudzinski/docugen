package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.wedding.model.WeddingCeremony;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface WeddingCeremonyService {
    List<WeddingCeremony> getWeddings();
    List<WeddingCeremony> getWeddingsSorted(Sort sort);
    WeddingCeremony findById(Long id);
    WeddingCeremony save(WeddingCeremony weddingCeremony);
    void deleteById(Long id);
}
