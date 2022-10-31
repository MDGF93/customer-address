package com.example.customeraddress.dto;

import lombok.Data;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CustomerAddressDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}-[0-9]{4}$")// (99) 99999-9999
    private String phone;

    @CPF(message = "CPF inválido")
    @Nullable
    private String cpf = null;

    @CNPJ
    @Nullable
    private String cnpj = null;

    @NotBlank
    @Pattern(regexp = "^\\d{5}-\\d{3}$") // 99999-999
    private String cep;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    @Nullable
    private String extraInfo;

    @Nullable
    private String addressName;

    private boolean mainAddress = false;

    public CustomerAddressDTO() {
    }

    public CustomerAddressDTO(String firstName, String lastName, String email, String phone, @MaybeNull String cpf,
                              @MaybeNull String cnpj, String cep, String city, String state, String street,
                              String number, @MaybeNull String extraInfo, boolean mainAddress, @MaybeNull String addressName) {
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
        this.addressName = addressName;
    }
}
