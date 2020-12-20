package com.liuyaqi.web.entity;

import java.io.Serializable;

public class PersonNumber implements Serializable {
    private static final long serialVersionUID = 1L;

    private String date;
    private int number;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
