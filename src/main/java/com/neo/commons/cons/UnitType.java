package com.neo.commons.cons;

import lombok.Getter;

public enum UnitType {
	Month("月"),
    Day("日"),
    Year("年"),
    Hour("时"),
    Minute("分"),
    Second("秒");

    @Getter
    private String type;

    UnitType(String type) {
        this.type = type;
    }

    public static UnitType getUnit(String t) {
        for (UnitType unitType : UnitType.values()) {
            if (t.equalsIgnoreCase(unitType.name()) || t.equalsIgnoreCase(unitType.getType())) {
                return unitType;
            }
        }
        return null;
    }

}
