package com.vti.entity;

public class Student {
    private static int count = 0;
    private int id;
    private String name;
    private String hometown;
    private float point;

    public Student(String name, String hometown) {
        this.name = name;
        this.hometown = hometown;
        this.point = 0;
        this.id = count++;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public void plusPoint(float point) {
        this.point += point;
    }

    public void printInfo() {
        String rank;

        if (this.point < 4.0) {
            rank = "Yeu";
        } else if (this.point < 6.0) {
            rank = "Trung binh";
        } else if (this.point < 8.0) {
            rank = "Kha";
        } else {
            rank = "Gioi";
        }

        System.out.println(name + " - " + this.point + " - " + rank);
    }
}
