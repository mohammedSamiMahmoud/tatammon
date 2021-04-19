package com.example.tatmon.Model;

public class Consultation {
/*"id" => $row['id'],
                "doctor_id" => $doctor_id,
                "patient_id" => $row['patient_id'],
                "patient_name" => $row2['name'],
                "photo" => $row['photo'],
                "date" => $row['date'],
                "time" => $row['time'],
                "status" => $row['status']
* */
    String id,doctor_id,patient_id,patient_name,date,time,status;

    public Consultation(String id, String doctor_id, String patient_id, String patient_name, String date, String time, String status) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
