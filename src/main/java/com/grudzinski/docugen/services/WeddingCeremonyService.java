package com.grudzinski.docugen.services;

import com.grudzinski.docugen.model.document.WeddingCeremony;

import java.util.List;

public interface WeddingCeremonyService {
    List<WeddingCeremony> getWeddings();
    WeddingCeremony findById(Long id);
}
