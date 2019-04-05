package com.grudzinski.docugen.wedding.model;

import lombok.Getter;
import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Id;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Immutable
@Subselect(value = "SELECT wedding_ceremony.id, " +
        "CONCAT_WS(', ', STRING_AGG(package_items.name, ', '), wedding_ceremony.service_package) AS service_Package, " +
        "customers.name as customer_Name, " +
        "wedding_ceremony.date_of_event AS date_Of_Event, " +
        "wedding_ceremony.time_of_event AS time_Of_Event " +
        "FROM wedding_ceremony " +
        "LEFT JOIN wedding_ceremony_package_items ON wedding_ceremony.id=wedding_ceremony_package_items.wedding_ceremony_id " +
        "LEFT JOIN package_items ON wedding_ceremony_package_items.package_item_id=package_items.id " +
        "LEFT JOIN customers ON wedding_ceremony.customer_id=customers.id " +
        "GROUP BY wedding_ceremony.id, customers.name")
public class WeddingCeremonySummary {
    @Id
    private Long Id;

    private String servicePackage;
    private String customerName;
    private LocalDate dateOfEvent;
    private LocalTime timeOfEvent;
}
