package com.example.administrator.hkbookcyh;

public class Item {
    private Integer id;
    private Integer year;
    private Integer month;
    private Integer day;
    private int won;
    private int kind;  //0 : 수입, 1 : 지출

    public Item(int won, int kind) {
        this.won = won;
        this.kind = kind;
    }

    public Item(Integer id, Integer year, Integer month, Integer day, int won, int kind) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.won = won;
        this.kind = kind;
    }


    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
