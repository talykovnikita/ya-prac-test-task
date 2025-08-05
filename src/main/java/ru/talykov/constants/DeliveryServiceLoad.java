package ru.talykov.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum DeliveryServiceLoad {
    VERY_HIGH("очень высокий",  BigDecimal.valueOf(1.6)),
    HIGH("высокий",  BigDecimal.valueOf(1.4)),
    MEDIUM("средний", BigDecimal.valueOf(1.2)),
    NORMAL("нормальный", BigDecimal.ONE);

    private final String description;
    private final BigDecimal priceKoef;
}
