package com.example.customeraddress.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String city;
    private String state;
    private String street;
    private String number;
    private String extraInfo;
    private boolean mainAddress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer;

    public Address(String cep, String city, String state, String street, String number, String extraInfo, boolean mainAddress, Customer customer) {
        this.cep = cep;
        this.city = city;
        this.state = state;
        this.street = street;
        this.number = number;
        this.extraInfo = extraInfo;
        this.mainAddress = mainAddress;
        this.customer = customer;
    }

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
