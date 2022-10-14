package com.example.customeraddress.entity;

import lombok.Data;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;


    @Pattern(regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$")// (99) 99999-9999
    private String phone;

    @CPF(message = "CPF inválido")
    @Nullable
    private String cpf;

    @CNPJ
    @Nullable
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
