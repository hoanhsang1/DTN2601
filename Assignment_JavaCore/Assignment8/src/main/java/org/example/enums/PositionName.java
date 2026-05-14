package org.example.enums;

public enum PositionName {
    DEV, TEST, SCRUM_MASTER, PM;

    /**
     * Parse từ DB: chấp nhận cả "Dev", "DEV", "Scrum Master", "SCRUM_MASTER", "SCRUM MASTER"
     */
    public static PositionName fromDb(String dbValue) {
        if (dbValue == null) throw new IllegalArgumentException("position_name is null");
        // chuẩn hóa: uppercase + thay dấu cách bằng _
        String normalized = dbValue.trim().toUpperCase().replace(" ", "_");
        return PositionName.valueOf(normalized);
    }
}
