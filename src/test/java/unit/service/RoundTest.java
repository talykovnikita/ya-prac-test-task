package unit.service;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.talykov.exception.RoundException;
import ru.talykov.service.RoundService;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Epic("unit")
@Feature("Тесты класса RoundService")
public class RoundTest {

    @Test
    @DisplayName("Проверка округления в большую сторону")
    void roundUpTest() {
        assertThat(RoundService.round(BigDecimal.valueOf(3.14)))
                .as("Неверное округление")
                .isEqualTo(BigDecimal.valueOf(4));
    }

    @Test
    @DisplayName("Проверка выброса исключения при попытке округлить `null`")
    void roundNullValueTest() {
        assertThatThrownBy(() -> RoundService.round(null))
                .isInstanceOf(RoundException.class)
                .hasMessage("value for rounding must not be null");
    }
}
