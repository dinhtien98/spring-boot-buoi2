package com.example.buoi2bt4.models;

public enum Rating {
    Gioi("Giỏi"),Kha("Khá"),Tb("Trung Bình"),Yeu("Yếu");
    private String value;
    Rating(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
