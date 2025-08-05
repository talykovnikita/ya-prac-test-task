package ru.talykov.service;

import ru.talykov.exception.RoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundService {

    public static BigDecimal round(BigDecimal value) {
        if (value == null) {
            throw new RoundException("value for rounding must not be null");
        }
        return value.setScale(0, RoundingMode.CEILING);
    }
}
