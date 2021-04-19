package com.example.tatmon.Model;

public class Patient {

    String id, name,
            email, password,
            phone, credit_card_num,
            location, disease,
            surgery;

    public Patient(String id, String name, String email, String password, String phone, String credit_card_num, String location, String disease, String surgery) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.credit_card_num = credit_card_num;
        this.location = location;
        this.disease = disease;
        this.surgery = surgery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCredit_card_num() {
        return credit_card_num;
    }

    public void setCredit_card_num(String credit_card_num) {
        this.credit_card_num = credit_card_num;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getSurgery() {
        return surgery;
    }

    public void setSurgery(String surgery) {
        this.surgery = surgery;
    }
}
