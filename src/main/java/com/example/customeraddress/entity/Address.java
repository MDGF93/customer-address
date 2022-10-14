package com.example.customeraddress.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Address {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

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

    private String extraInfo;
    private boolean mainAddress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;
        return id != null && Objects.equals(id, address.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
