package ru.practikum.yandex.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practikum.yandex.model.OrderDataLombok;

import static io.restassured.RestAssured.given;


public class OrderApi extends RestApi {
    /**
     * URI для создания заказа
     */
    public static final String CREATE_ORDER_URI = "/api/v1/orders";
    /**
     * URI для отмены заказа
     */
    public static final String CANCEL_ORDER_URI = "/api/v1/orders/cancel";
    /**
     * URI для получения списка заказа
     */
    public static final String GET_LIST_ORDER_URI = "/api/v1/orders";

    @Step("Create order")
    public ValidatableResponse createOrderLombok(OrderDataLombok order) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_URI)
                .then();
    }

    @Step("Cancel order")
    public ValidatableResponse cancelOrder(int orderTrack) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(orderTrack)
                .when()
                .put(CANCEL_ORDER_URI, orderTrack)
                .then();
    }

    @Step("Get list of orders")
    public ValidatableResponse getListOrdersLombok() {
        return given()
                .spec(requestSpecification())
                .when()
                .get(GET_LIST_ORDER_URI)
                .then();
    }
}