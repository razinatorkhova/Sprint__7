package tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.practikum.yandex.api.OrderApi;

import static org.hamcrest.Matchers.notNullValue;

public class CheckOrdersBody {
    @DisplayName("Check orders body contains list of orders")
    @Test
    public void checkOrdersBodyContainsListOfOrdersTest() {

        OrderApi orderApi = new OrderApi();
        //вызываем метод
        ValidatableResponse response = orderApi.getListOrdersLombok();

        //проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(notNullValue());
    }
}

//Список заказов
//+Проверь, что в тело ответа возвращается список заказов.