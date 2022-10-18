package com.example.customeraddress.repository;

import com.example.customeraddress.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCpf(String cpf);

    Customer findByCnpj(String cnpj);
}
