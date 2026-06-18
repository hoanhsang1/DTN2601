package com.vti.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ArticlePositionNameConverter implements AttributeConverter<PositionName, String> {
    @Override
    public String convertToDatabaseColumn(PositionName positionName) {
        if(positionName == null) {return null;}
        return positionName.getName();
    }

    @Override
    public PositionName convertToEntityAttribute(String s) {
        if(s == null) {return null;}
        return PositionName.toEnum(s);
    }
}
