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
}
