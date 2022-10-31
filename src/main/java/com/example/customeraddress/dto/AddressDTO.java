package com.example.customeraddress.dto;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class AddressDTO {
    @NotBlank
    @Pattern(regexp = "^\\d{5}-\\d{3}$")    // 99999-999
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
    private String name;

    @NotNull
    private boolean mainAddress;

    public AddressDTO() {}

    public AddressDTO(String cep, String city, String state, String street, String number, @MaybeNull String extraInfo,
                      boolean mainAddress, @MaybeNull String name) {
        this.cep         = cep;
        this.city        = city;
        this.state       = state;
        this.street      = street;
        this.number      = number;
        this.extraInfo   = extraInfo;
        this.mainAddress = mainAddress;
        this.name        = name;
    }
}