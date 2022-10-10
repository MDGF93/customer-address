package com.example.customeraddress.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^\\d{5}-\\d{3}$") // 99999-999
    private String cep;
//ViaCep API
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
//ViaCep API

    @NotBlank
    private String extraInfo;

    @NotBlank
    private String number;
    private boolean mainAddress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer;

    private Address callViaCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        String result = restTemplate.getForObject(url, String.class);
        Gson gson = new Gson();
        return gson.fromJson(result, Address.class);
    }

    public Address(String cep, String extraInfo, String number, boolean mainAddress, Customer customer) {
        Address address = callViaCep(cep);
        this.cep = cep;
        this.extraInfo = extraInfo;
        this.number = number;
        this.mainAddress = mainAddress;
        this.customer = customer;
        this.logradouro = address.getLogradouro();
        this.complemento = address.getComplemento();
        this.bairro = address.getBairro();
        this.localidade = address.getLocalidade();
        this.uf = address.getUf();
        this.ibge = address.getIbge();
        this.gia = address.getGia();
        this.ddd = address.getDdd();
        this.siafi = address.getSiafi();
    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                ", ibge='" + ibge + '\'' +
                ", gia='" + gia + '\'' +
                ", ddd='" + ddd + '\'' +
                ", siafi='" + siafi + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                ", number='" + number + '\'' +
                ", mainAddress=" + mainAddress +
                '}';
    }
}
