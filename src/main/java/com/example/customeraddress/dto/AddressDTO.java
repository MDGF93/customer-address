package com.example.customeraddress.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String cep;
    private String extraInfo;
    private String number;
    private boolean mainAddress;

    public AddressDTO(String cep, String extraInfo, String number, boolean mainAddress) {
        this.cep = cep;
        this.extraInfo = extraInfo;
        this.number = number;
        this.mainAddress = mainAddress;
    }

    public AddressDTO() {
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
}
