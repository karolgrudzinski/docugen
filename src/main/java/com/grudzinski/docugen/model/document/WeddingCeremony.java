package com.grudzinski.docugen.model.document;

import com.grudzinski.docugen.model.base.Customer;
import com.grudzinski.docugen.model.base.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class WeddingCeremony extends BaseDocument {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_signing")
    private LocalDate dateOfSigning;

    @Column(name = "place_of_signing")
    private String placeOfSigning;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "performer")
    private String performer;

    @Column(name = "date_of_event")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfEvent;

    @Column(name = "time_of_event")
    private LocalTime timeOfEvent;

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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "document_short_name")
    private String documentShortName;
}
