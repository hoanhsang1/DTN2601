package org.example.entity;

public class Major {
    private int majorId;
    private String majorName;


    public int getMajorId() {
        return majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Major(int majorId, String majorName) {
        this.majorId = majorId;
        this.majorName = majorName;
    }
}
