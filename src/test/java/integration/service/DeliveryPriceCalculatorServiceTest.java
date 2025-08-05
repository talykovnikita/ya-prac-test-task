package integration.service;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.talykov.constants.DeliveryServiceLoad;
import ru.talykov.constants.PackageSize;
import ru.talykov.exception.DeliveryPriceCalculationException;
import ru.talykov.service.DeliveryPriceCalculatorService;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Epic("integration")
@Feature("Тесты класса DeliveryPriceCalculatorService")
public class DeliveryPriceCalculatorServiceTest {

    private final DeliveryPriceCalculatorService deliveryPriceCalculatorService = new DeliveryPriceCalculatorService();

    @Test
    @DisplayName("Доставка большого хрупкого груза при очень высокой загрузке на 30+ километров")
    void bigFragilePackageToVeryLongDistanceAndVeryHighDeliveryServiceLoad() {

        var resultPrice = deliveryPriceCalculatorService.calculate(
                BigDecimal.valueOf(30.1),
                PackageSize.BIG,
                true,
                DeliveryServiceLoad.VERY_HIGH
        );

        assertThat(resultPrice)
                .as("Рассчётная цена не совпадает с ожидаемой")
                .isEqualTo(BigDecimal.valueOf(1280));
    }

    @Test
    @DisplayName("Доставка маленького НЕхрупкого груза при высокой загрузке на 10 километров")
    void smallNotFragilePackageToLongDistanceAndHighDeliveryServiceLoad() {

        var resultPrice = deliveryPriceCalculatorService.calculate(
                BigDecimal.valueOf(10),
                PackageSize.SMALL,
                false,
                DeliveryServiceLoad.HIGH
        );

        assertThat(resultPrice)
                .as("Рассчётная цена не совпадает с ожидаемой")
                .isEqualTo(BigDecimal.valueOf(420));
    }

    @Test
    @DisplayName("Доставка большого хрупкого груза при средней загрузке на 2 километра")
    void bigFragilePackageToNormalDistanceAndMediumDeliveryServiceLoad() {

        var resultPrice = deliveryPriceCalculatorService.calculate(
                BigDecimal.valueOf(2),
                PackageSize.BIG,
                true,
                DeliveryServiceLoad.MEDIUM
        );

        assertThat(resultPrice)
                .as("Рассчётная цена не совпадает с ожидаемой")
                .isEqualTo(BigDecimal.valueOf(720));
    }

    @Test
    @DisplayName("Доставка большого хрупкого груза при средней загрузке на 2 километра. Минимальный порог цены в 400 не превышен.")
    void smallNotFragilePackageToShortDistanceAndNormalDeliveryServiceLoad() {

        var resultPrice = deliveryPriceCalculatorService.calculate(
                BigDecimal.valueOf(1.9),
                PackageSize.SMALL,
                false,
                DeliveryServiceLoad.NORMAL
        );

        assertThat(resultPrice)
                .as("Рассчётная цена не совпадает с ожидаемой")
                .isEqualTo(BigDecimal.valueOf(400));
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
    @DisplayName("Валидация размера посылки != null")
    void nullDeliveryServiceLoad() {
        assertThatThrownBy(() -> deliveryPriceCalculatorService.calculate(
                BigDecimal.ONE,
                PackageSize.SMALL,
                false,
                null))
                .isInstanceOf(DeliveryPriceCalculationException.class)
                .hasMessage("deliveryServiceLoad must not be null");
    }
}
