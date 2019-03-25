package com.grudzinski.docugen.services;

import com.grudzinski.docugen.exceptions.NotFoundException;
import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.grudzinski.docugen.repository.CustomerRepository;
import com.grudzinski.docugen.repository.WeddingCeremonyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<WeddingCeremony> getWeddingsSorted(Sort sort) {
        List<WeddingCeremony> weddings = new ArrayList<>();
        weddingCeremonyRepository.findAll(sort).forEach(weddings::add);


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

    public String getProposedShortName(WeddingCeremony weddingCeremony) {
        DateTimeFormatter format = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd").toFormatter();
        if (weddingCeremony.getDateOfEvent() == null) {
            log.error("DateOfEvent can't be null");
            return "";
        }
        String datePart = weddingCeremony.getDateOfEvent().format(format);
        String customerPart = Arrays.stream(weddingCeremony.getCustomer().getName().split(" "))
                .map(s -> s.substring(0, Math.min(s.length(), 5))).collect(Collectors.joining(""));
        String place = weddingCeremony.getPlaceOfEvent();
        String placePart = place.substring(0, Math.min(place.length(), 6));
//        log.debug("datePart = {}", datePart);
//        log.debug("custPart = {}", customerPart);
//        log.debug("placPart = {}", placePart);

        String shortName = "".join("-", datePart, customerPart, placePart);

        log.debug("Generated proposed short name = {}", shortName);
        return shortName;
    }

    @Override
    public WeddingCeremony save(WeddingCeremony weddingCeremony) {
        // TODO: Create DTO for create/update form and move this to proper DTO->Entity converter
        if (weddingCeremony.getId() != null) {
            WeddingCeremony weddingFromDB = this.findById(weddingCeremony.getId());
            if (weddingFromDB != null) {
                weddingCeremony.setCreationTime(weddingFromDB.getCreationTime());
            }
        }

        if (weddingCeremony.getDocumentShortName().isEmpty()) {
            weddingCeremony.setDocumentShortName(getProposedShortName(weddingCeremony));
            log.debug("Set DocumentShortName to generated value = {}", weddingCeremony.getDocumentShortName());
        }

        WeddingCeremony savedWeddingCeremony = weddingCeremonyRepository.save(weddingCeremony);
        log.debug("Saved WeddingCeremony:" + savedWeddingCeremony.getId());

        return savedWeddingCeremony;
    }

    @Override
    public void deleteById(Long id) {
        weddingCeremonyRepository.deleteById(id);
    }
}
