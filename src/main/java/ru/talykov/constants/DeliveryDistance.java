package ru.talykov.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.talykov.exception.DeliveryDistanceException;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum DeliveryDistance {
    VERY_LONG("более 30 км", BigDecimal.valueOf(300)),
    LONG("от 10 км до 30 км", BigDecimal.valueOf(200)),
    NORMAL("от 2 км до 10 км", BigDecimal.valueOf(100)),
    SHORT("до 2 км", BigDecimal.valueOf(50));

    private final String description;
    private final  BigDecimal price;

    public static DeliveryDistance getByDistance(BigDecimal deliveryDistance) {
        if (deliveryDistance == null) {
            throw new DeliveryDistanceException("Delivery distance must not be null.");
        }

        if (deliveryDistance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DeliveryDistanceException("Delivery distance must be grater than 0.");
        }

        if (deliveryDistance.compareTo(BigDecimal.valueOf(2)) < 0) {
            return SHORT;
        }

        if (deliveryDistance.compareTo(BigDecimal.valueOf(10)) < 0) {
            return NORMAL;
        }

        if (deliveryDistance.compareTo(BigDecimal.valueOf(30)) < 0) {
            return LONG;
        }

        return VERY_LONG;
    }
}
