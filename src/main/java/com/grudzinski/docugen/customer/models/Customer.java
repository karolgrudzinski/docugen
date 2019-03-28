package com.grudzinski.docugen.customer.models;

import com.grudzinski.docugen.base.models.BusinessEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BusinessEntity {

}
