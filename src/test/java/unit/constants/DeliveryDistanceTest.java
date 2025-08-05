package unit.constants;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.talykov.constants.DeliveryDistance;
import ru.talykov.exception.DeliveryDistanceException;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static ru.talykov.constants.DeliveryDistance.*;

@Epic("unit")
@Feature("Тесты класса DeliveryDistance")
public class DeliveryDistanceTest {

    @Test
    @DisplayName("Маппинг цена для расстояния > 30км")
    void getVeryLongPrice() {
        final var distance = DeliveryDistance.getByDistance(BigDecimal.valueOf(30));

        Assertions.assertThat(distance)
                .as("Неверный маппинг цены для VERY_LONG дистанций")
                .isEqualTo(VERY_LONG);
    }

    @Test
    @DisplayName("Маппинг цена для расстояния между 10км и 30км")
    void getLongPrice() {
        final var distance = DeliveryDistance.getByDistance(BigDecimal.valueOf(29.9));

        Assertions.assertThat(distance)
                .as("Неверный маппинг цены для LONG дистанций")
                .isEqualTo(LONG);
    }

    @Test
    @DisplayName("Маппинг цена для расстояния между 2 и 10 км")
    void getNormalPrice() {
        final var distance = DeliveryDistance.getByDistance(BigDecimal.valueOf(2));

        Assertions.assertThat(distance)
                .as("Неверный маппинг цены для NORMAL дистанций")
                .isEqualTo(NORMAL);
    }

    @Test
    @DisplayName("Маппинг цена для расстояния < 2км")
    void getShortPrice() {
        final var distance = DeliveryDistance.getByDistance(BigDecimal.valueOf(1.99));

        Assertions.assertThat(distance)
                .as("Неверный маппинг цены для SHORT дистанций")
                .isEqualTo(SHORT);
    }

    @Test
    @DisplayName("Получение исключения при маппинге расстояния null")
    void nullDeliveryDistanceTest() {
        assertThatThrownBy(() ->DeliveryDistance.getByDistance(null))
                .isInstanceOf(DeliveryDistanceException.class)
                .hasMessage("Delivery distance must not be null.");
    }

    @Test
    @DisplayName("Получение исключения при маппинге расстояния 0")
    void zeroDeliveryDistanceTest() {
        assertThatThrownBy(() ->DeliveryDistance.getByDistance(BigDecimal.ZERO))
                .isInstanceOf(DeliveryDistanceException.class)
                .hasMessage("Delivery distance must be grater than 0.");
    }
}
