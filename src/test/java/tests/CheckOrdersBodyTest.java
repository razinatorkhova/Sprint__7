package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.practikum.yandex.api.OrderApi;

import static org.hamcrest.Matchers.*;

@Feature("List of orders")
public class CheckOrdersBodyTest {
    @DisplayName("Check orders body contains list of orders")
    @Test
    public void checkOrdersBodyContainsListOfOrdersTest() {

        OrderApi orderApi = new OrderApi();

        ValidatableResponse response = orderApi.getListOrdersLombok();

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("orders", is(not(emptyArray()))); // Проверяем, что массив orders не пустой
    }
}
