package com.grudzinski.docugen.services;

import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.grudzinski.docugen.repository.WeddingCeremonyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeddingCeremonyServiceImpl implements WeddingCeremonyService {

    private WeddingCeremonyRepository weddingCeremonyRepository;

    public WeddingCeremonyServiceImpl(WeddingCeremonyRepository weddingCeremonyRepository) {
        this.weddingCeremonyRepository = weddingCeremonyRepository;
    }

    @Override
    public List<WeddingCeremony> getWeddings() {
        List<WeddingCeremony> weddings = new ArrayList<>();
        weddingCeremonyRepository.findAll().forEach(weddings::add);
        return weddings;
    }

    @Override
    public WeddingCeremony findById(Long id) {
        return null;
    }
}
