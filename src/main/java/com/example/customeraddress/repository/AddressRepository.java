package com.example.customeraddress.repository;

import com.example.customeraddress.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByCustomerId(Long id);

    boolean existsByCep(String valid_cep);

    Address findByCep(String valid_cep);

    Address findByCity(String valid_city);
}
