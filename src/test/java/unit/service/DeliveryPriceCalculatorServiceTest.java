package unit.service;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.talykov.constants.DeliveryServiceLoad;
import ru.talykov.constants.PackageSize;
import ru.talykov.exception.DeliveryPriceCalculationException;
import ru.talykov.service.DeliveryPriceCalculatorService;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@Epic("unit")
@Feature("Тесты класса DeliveryPriceCalculatorService")
public class DeliveryPriceCalculatorServiceTest {
    private final DeliveryPriceCalculatorService deliveryPriceCalculatorService = new DeliveryPriceCalculatorService();

    @Test
    @DisplayName("Валидация расстояния: расстояние != null")
    void nullDeliveryDistance() {
        assertThatThrownBy(() -> deliveryPriceCalculatorService.calculate(
                null,
                PackageSize.SMALL,
                false,
                DeliveryServiceLoad.NORMAL))
                .isInstanceOf(DeliveryPriceCalculationException.class)
                .hasMessage("deliveryDistance must not be null");
    }

    @Test
    @DisplayName("Валидация размера посылки != null")
    void nullPackageSize() {
        assertThatThrownBy(() -> deliveryPriceCalculatorService.calculate(
                BigDecimal.ONE,
                null,
                false,
                DeliveryServiceLoad.NORMAL))
                .isInstanceOf(DeliveryPriceCalculationException.class)
                .hasMessage("packageSize must not be null");
    }

    @Test
    @DisplayName("Валидация состояние загрузки сервиса доставки != null")
    void nullDeliveryServiceLoad() {
        assertThatThrownBy(() -> deliveryPriceCalculatorService.calculate(
                BigDecimal.ONE,
                PackageSize.SMALL,
                false,
                null))
                .isInstanceOf(DeliveryPriceCalculationException.class)
                .hasMessage("deliveryServiceLoad must not be null");
    }


    @Test
    @DisplayName("Валидация расстояния: расстояние = 0")
    void zeroDeliveryDistance() {
        assertThatThrownBy(() -> deliveryPriceCalculatorService.calculate(
                BigDecimal.ZERO,
                PackageSize.SMALL,
                false,
                DeliveryServiceLoad.NORMAL))
                .isInstanceOf(DeliveryPriceCalculationException.class)
                .hasMessage("deliveryDistance must be greater than 0");
    }
}
