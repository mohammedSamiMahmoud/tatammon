package com.example.tatmon.Model;

public class Patient {

    String p_id, p_name,
            p_email, p_location;

    public Patient(String p_id, String p_name, String p_email, String p_location) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.p_email = p_email;
        this.p_location = p_location;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_email() {
        return p_email;
    }

    public void setP_email(String p_email) {
        this.p_email = p_email;
    }

    public String getP_location() {
        return p_location;
    }

    public void setP_location(String p_location) {
        this.p_location = p_location;
    }
}
