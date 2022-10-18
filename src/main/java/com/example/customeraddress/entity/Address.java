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
@RequiredArgsConstructor
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
