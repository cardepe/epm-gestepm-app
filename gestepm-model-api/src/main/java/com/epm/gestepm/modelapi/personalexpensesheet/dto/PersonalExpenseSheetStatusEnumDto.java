package com.epm.gestepm.modelapi.personalexpensesheet.dto;

public enum PersonalExpenseSheetStatusEnumDto {
    PENDING,
    APPROVED,
    PAID,
    REJECTED;

    public boolean isBefore(PersonalExpenseSheetStatusEnumDto other) {
        return this.ordinal() < other.ordinal();
    }

    public boolean isAfter(PersonalExpenseSheetStatusEnumDto other) {
        return this.ordinal() > other.ordinal();
    }
}
