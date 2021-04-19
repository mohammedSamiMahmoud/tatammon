package com.example.tatmon.Model;


/**
 * "id" => $row['doctor_id'],
 * "Dname" => $row['name'],
 * "photo" => "https://smarttracks.org/test/tat/src/routes/Dimages/" . $row['photo'],
 * "Dspecialization" => $row['specialization']
 */
public class P_C {

    String id, Dname, photo, Dspecialization;

    public P_C(String id, String dname, String photo, String dspecialization) {
        this.id = id;
        Dname = dname;
        this.photo = photo;
        Dspecialization = dspecialization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDname() {
        return Dname;
    }

    public void setDname(String dname) {
        Dname = dname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDspecialization() {
        return Dspecialization;
    }

    public void setDspecialization(String dspecialization) {
        Dspecialization = dspecialization;
    }
}
