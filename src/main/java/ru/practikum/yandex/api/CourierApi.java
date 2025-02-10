package ru.practikum.yandex.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practikum.yandex.model.CourierDataLombok;
import ru.practikum.yandex.model.LoginDataLombok;

import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi {
    /**
     * URI для создания курьера
     */
    public static final String CREATE_COURIER_URI = "/api/v1/courier";
    /**
     * URI для авторизации курьера
     */
    public static final String LOGIN_COURIER_URI = "/api/v1/courier/login";
    /**
     * URI для удаления курьера
     */
    public static final String DELETE_COURIER_URI = "/api/v1/courier/:id";

    @Step("Create courier")
    public ValidatableResponse createCourierLombok(CourierDataLombok courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(String courierId) {
        return given()
                .spec(requestSpecification())
                .when()
                .delete(DELETE_COURIER_URI, courierId)
                .then();
    }

    @Step("Authorized courier")
    public ValidatableResponse loginCourier(LoginDataLombok courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_URI)
                .then();
    }

}
