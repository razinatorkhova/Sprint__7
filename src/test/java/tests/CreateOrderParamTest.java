package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practikum.yandex.api.OrderApi;
import ru.practikum.yandex.model.OrderDataLombok;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Feature("Create order")
@RunWith(Parameterized.class)
public class CreateOrderParamTest {

    private final List<String> colors;

    public CreateOrderParamTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {Arrays.asList()} // отсутствие цветов
        });
    }

    @DisplayName("Create order with different colors")
    @Test
    public void createOrderTest() {
        OrderDataLombok order = new OrderDataLombok(
                "Nabut",
                "Uchiba",
                "Konoba, 142",
                "3",
                "+7 998 355 35 55",
                5,
                "2025-02-17",
                "Write me please before",
                colors
        );

        ValidatableResponse response = new OrderApi().createOrderLombok(order);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", is(notNullValue()));
    }
}
