package com.grudzinski.docugen.services;

import com.grudzinski.docugen.exceptions.NotFoundException;
import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.grudzinski.docugen.repository.WeddingCeremonyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<WeddingCeremony> weddingCeremonyOptional;
        weddingCeremonyOptional = weddingCeremonyRepository.findById(id);
        if (!weddingCeremonyOptional.isPresent()) {
            throw new NotFoundException("Document not found for Id: " + id);
        }
        return weddingCeremonyOptional.get();
    }
}
