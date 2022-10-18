package com.example.customeraddress.dto;

import net.bytebuddy.utility.nullability.MaybeNull;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.lang.Nullable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerAddressDTO {

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;

    @Email
    public String email;

    @Pattern(regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}-[0-9]{4}$")// (99) 99999-9999
    public String phone;

    @CPF(message = "CPF inválido")
    @Nullable
    public String cpf = null;

    @CNPJ
    @Nullable
    public String cnpj = null;

    @NotBlank
    @Pattern(regexp = "^\\d{5}-\\d{3}$") // 99999-999
    public String cep;

    @NotBlank
    public String city;

    @NotBlank
    public String state;

    @NotBlank
    public String street;

    @NotBlank
    public String number;

    @Nullable
    public String extraInfo;

    public boolean mainAddress = false;

    public CustomerAddressDTO() {
    }

    public CustomerAddressDTO(String firstName, String lastName, String email, String phone, @MaybeNull String cpf, @MaybeNull String cnpj, String cep, String city, String state, String street, String number, @MaybeNull String extraInfo, boolean mainAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.cep = cep;
        this.city = city;
        this.state = state;
        this.street = street;
        this.number = number;
        this.extraInfo = extraInfo;
        this.mainAddress = mainAddress;
    }
}
