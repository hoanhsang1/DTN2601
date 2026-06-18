package com.vti.enums;

import org.aspectj.weaver.ast.Test;

import java.util.Arrays;

public enum PositionName {
//    Dev, Test,Scrum_Master, PM
    Dev("D"), Test("T"),Scrum_Master("S"), PM("PM");

    private String name;

    private PositionName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PositionName toEnum(String sqlName) {
//        for (PositionName positionName : PositionName.values()) {
//            if (positionName.getName().equals(sqlName)) {
//                return positionName;
//            }
//        }
//        return null;
        return Arrays.stream(PositionName.values())
                .filter(i -> i.getName().equals(sqlName)).findFirst().orElse(null);
    }
}
