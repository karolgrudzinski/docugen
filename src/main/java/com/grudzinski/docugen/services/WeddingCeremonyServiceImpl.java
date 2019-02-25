package com.grudzinski.docugen.services;

import com.grudzinski.docugen.exceptions.NotFoundException;
import com.grudzinski.docugen.model.base.Customer;
import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.grudzinski.docugen.repository.CustomerRepository;
import com.grudzinski.docugen.repository.WeddingCeremonyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WeddingCeremonyServiceImpl implements WeddingCeremonyService {

    private final WeddingCeremonyRepository weddingCeremonyRepository;
    private final CustomerRepository customerRepository;

    public WeddingCeremonyServiceImpl(WeddingCeremonyRepository weddingCeremonyRepository, CustomerRepository customerRepository) {
        this.weddingCeremonyRepository = weddingCeremonyRepository;
        this.customerRepository = customerRepository;
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

    @Override
    public WeddingCeremony save(WeddingCeremony weddingCeremony) {
        if (weddingCeremony.getCustomer().getId() == null) {
            Customer savedCustomer = customerRepository.save(weddingCeremony.getCustomer());
            if (savedCustomer.getId() != null) {
                weddingCeremony.setCustomer(savedCustomer);
            }
        }

        WeddingCeremony savedWeddingCeremony = weddingCeremonyRepository.save(weddingCeremony);
        log.debug("Saved WeddingCeremony:" + savedWeddingCeremony.getId());

        return savedWeddingCeremony;
    }
}
