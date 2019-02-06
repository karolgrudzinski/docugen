package com.grudzinski.docugen.model.document;

import com.grudzinski.docugen.model.base.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class WeddingCeremony extends BaseDocument {

    @Column(name = "date_of_signing")
    private LocalDate dateOfSigning;

    @Column(name = "place_of_signing")
    private String placeOfSigning;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "performer")
    private String performer;

    @Column(name = "date_time_of_event")
    private LocalDateTime dateTimeOfEvent;

    @Column(name = "church")
    private String church;

    @Column(name = "place_of_event")
    private String placeOfEvent;

    @Column(name = "service_package")
    private String servicePackage;

    @Column(name = "charge")
    private BigDecimal charge;

    @Column(name = "advance")
    private BigDecimal advance;
}
