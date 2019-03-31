package com.grudzinski.docugen.wedding.model;

import com.grudzinski.docugen.base.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "package_items")
public class PackageItem extends BaseEntity {
    public PackageItem(String name) {
        this.name = name;
    }

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "servicePackageItems")
    private List<WeddingCeremony> weddings;
}
