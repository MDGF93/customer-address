package com.example.customeraddress.entity;

import lombok.Data;
import net.bytebuddy.utility.nullability.MaybeNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String cpf;
    private String cnpj;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private List<Address> addressList;

    //Generate setter for addressList


    public Customer() {
    }

    public Customer(
            String firstName,
            String lastName,
            String email,
            String phone,
            @MaybeNull String cpf,
            @MaybeNull String cnpj) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.cnpj = cnpj;
    }

    public Customer(String valid_first_name, String valid_last_name, String valid_cpf, String valid_cnpj, String valid_phone, String valid_email, List<Address> valid_address_list) {
        this.firstName = valid_first_name;
        this.lastName = valid_last_name;
        this.cpf = valid_cpf;
        this.cnpj = valid_cnpj;
        this.phone = valid_phone;
        this.email = valid_email;
        this.addressList = valid_address_list;
    }


    @Override
    public String toString() {
        return """
        Customer {
        id=%s,
        firstName='%s',
        lastName='%s',
        email='%s',
        phone='%s',
        cpf='%s',
        cnpj='%s',
        addressList=%s
        }
        """.formatted(id, firstName, lastName, email, phone, cpf, cnpj, addressList);
    }
}
