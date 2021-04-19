package com.example.tatmon.Model;

public class Article {

    String head,doc_name,date;


    public Article(String head, String doc_name, String date) {
        this.head = head;
        this.doc_name = doc_name;
        this.date = date;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
