package com.example.customeraddress.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String cep;
    private String city;
    private String state;
    private String street;
    private String number;
    private String extraInfo;
    private boolean mainAddress;

    public AddressDTO(String cep, String city, String state, String street, String number, String extraInfo, boolean mainAddress) {
        this.cep = cep;
        this.city = city;
        this.state = state;
        this.street = street;
        this.number = number;
        this.extraInfo = extraInfo;
        this.mainAddress = mainAddress;
    }

    public AddressDTO() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public boolean isMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(boolean mainAddress) {
        this.mainAddress = mainAddress;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCep() {
        return cep;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
