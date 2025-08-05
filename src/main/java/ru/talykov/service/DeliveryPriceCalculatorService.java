package ru.talykov.service;

import ru.talykov.configuration.Configuration;
import ru.talykov.constants.DeliveryDistance;
import ru.talykov.constants.DeliveryServiceLoad;
import ru.talykov.constants.PackageSize;
import ru.talykov.exception.DeliveryPriceCalculationException;

import java.math.BigDecimal;


public class DeliveryPriceCalculatorService {

    public BigDecimal calculate(
            BigDecimal deliveryDistance,
            PackageSize packageSize,
            boolean isFragile,
            DeliveryServiceLoad deliveryServiceLoad
            ) {
        if (deliveryDistance == null) {
            throw new DeliveryPriceCalculationException("deliveryDistance must not be null");
        }

        if (deliveryDistance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DeliveryPriceCalculationException("deliveryDistance must be greater than 0");
        }

        if (packageSize == null) {
            throw new DeliveryPriceCalculationException("packageSize must not be null");
        }

        if (deliveryServiceLoad == null) {
            throw new DeliveryPriceCalculationException("deliveryServiceLoad must not be null");
        }

        BigDecimal distancePrice = DeliveryDistance.getByDistance(deliveryDistance).getPrice();
        BigDecimal packageSizePrice = packageSize.getPrice();
        BigDecimal fragilePrice = isFragile ? Configuration.FRAGILE_ADDITIONAL_PRICE : BigDecimal.ZERO;
        BigDecimal deliveryServiceLoadKoef = deliveryServiceLoad.getPriceKoef();

        BigDecimal deliverPrice = distancePrice
                .add(packageSizePrice)
                .add(fragilePrice)
                .multiply(deliveryServiceLoadKoef);

        BigDecimal finalPrice = deliverPrice.compareTo(Configuration.MIN_PRICE) < 0 ? Configuration.MIN_PRICE : deliverPrice;
        return RoundService.round(finalPrice);
    }
}
