package com.example.customeraddress.dto;

import net.bytebuddy.utility.nullability.MaybeNull;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddressDTO {
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

    @NotNull
    public boolean mainAddress;

    public AddressDTO() {
    }

    public AddressDTO(String cep, String city, String state, String street, String number, @MaybeNull String extraInfo, boolean mainAddress) {
        this.cep = cep;
        this.city = city;
        this.state = state;
        this.street = street;
        this.number = number;
        this.extraInfo = extraInfo;
        this.mainAddress = mainAddress;
    }
}
