package com.grudzinski.docugen.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BusinessEntity extends BaseEntity {

    public BusinessEntity(Long id, String name, String address, String phone, String email) {
        super(id);
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    @Column(name = "name")
    private String name;

//    @Column(name = "tax_id")
//    private String tax_id;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

}
