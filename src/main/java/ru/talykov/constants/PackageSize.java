package ru.talykov.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum PackageSize {
    SMALL("маленький", BigDecimal.valueOf(100)),
    BIG("большой", BigDecimal.valueOf(200));

    private final  String description;
    private final  BigDecimal price;
}
