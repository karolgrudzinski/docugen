package com.grudzinski.docugen.customer.repositories;

import com.grudzinski.docugen.customer.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
