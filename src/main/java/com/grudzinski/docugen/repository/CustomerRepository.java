package com.grudzinski.docugen.repository;

import com.grudzinski.docugen.model.base.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
