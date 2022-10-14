package com.example.customeraddress.dto;

public class CustomerAddressDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String cpf = null;
    private String cnpj = null;
    private String cep;
    private String city;
    private String state;



    private String street;
    private String number;
    private String extraInfo;
    private boolean mainAddress = false;

    public CustomerAddressDTO() {
    }

    public CustomerAddressDTO(String firstName, String lastName, String email, String phone, String cpf, String cnpj, String cep, String city, String state, String street, String number, String extraInfo, boolean mainAddress) {
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

    @Override
    public String toString() {
        return "CustomerAddressDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", cep='" + cep + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", number='" + number + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                ", mainAddress=" + mainAddress +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public boolean isMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(boolean mainAddress) {
        this.mainAddress = mainAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

}
