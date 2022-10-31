package com.example.customeraddress.repository;

import com.example.customeraddress.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCpf(String cpf);

    Customer findByCnpj(String cnpj);

    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE %?1% AND c.lastName LIKE %?2%")
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT c FROM Customer c JOIN c.addressList a WHERE a.city LIKE %?1%")
    List<Customer> findByAddressListCity(String city);

    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE %?1% AND c.lastName LIKE %?2% AND c.phone LIKE %?3%")
    List<Customer> findByFirstNameAndLastNameAndPhone(String firstName, String lastName, String phone);

    boolean existsByCpf(String cpf);
}
