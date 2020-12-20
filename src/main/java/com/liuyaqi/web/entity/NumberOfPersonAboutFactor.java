package com.liuyaqi.web.entity;

import java.io.Serializable;

//number_people,date,day_of_week,is_weekend,is_holiday,temperature,is_start_of_semester,is_during_semester,month,hour
public class NumberOfPersonAboutFactor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String date;
    private int numberPeople;
    private int dayOfWeek;
    private int isWeekend;
    private int isHoliday;
    private float temperature;
    private int isStartOfSemester;
    private int isDuringSemester;

    public int getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getIsWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(int isWeekend) {
        this.isWeekend = isWeekend;
    }

    public int getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(int isHoliday) {
        this.isHoliday = isHoliday;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getIsStartOfSemester() {
        return isStartOfSemester;
    }

    public void setIsStartOfSemester(int isStartOfSemester) {
        this.isStartOfSemester = isStartOfSemester;
    }

    public int getIsDuringSemester() {
        return isDuringSemester;
    }

    public void setIsDuringSemester(int isDuringSemester) {
        this.isDuringSemester = isDuringSemester;
    }
}
