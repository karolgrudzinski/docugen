package com.grudzinski.docugen.wedding.model;

import com.grudzinski.docugen.base.models.BaseAuditableEntity;
import com.grudzinski.docugen.base.models.PaymentMethod;
import com.grudzinski.docugen.customer.models.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class WeddingCeremony extends BaseAuditableEntity {

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

    @ManyToMany
    @JoinTable(name = "wedding_ceremony_package_items",
            joinColumns = @JoinColumn(name = "wedding_ceremony_id"),
            inverseJoinColumns = @JoinColumn(name = "package_item_id"))
    private Set<PackageItem> servicePackageItems = new HashSet<>();

    @Column(name = "charge")
    private BigDecimal charge;

    @Column(name = "advance")
    private BigDecimal advance;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "document_short_name")
    private String documentShortName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private PdfFile pdfFile;
}
