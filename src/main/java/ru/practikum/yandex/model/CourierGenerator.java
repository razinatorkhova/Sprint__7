package ru.practikum.yandex.model;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    @Step("Generate random courier")
    public static CourierDataLombok getRandomCourier() {
        String login = RandomStringUtils.randomAlphabetic(8);
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(8);

        return new CourierDataLombok(login, password, firstName);
    }

    @Step("Generate random courier")
    public static CourierDataLombok getRandomCourier(String loginParam, String passwordParam,
                                                     String firstNameParam) {
        String login = (loginParam != null) ? loginParam + RandomStringUtils.randomAlphabetic(5) : "";
        String password = (passwordParam != null) ? passwordParam + RandomStringUtils.randomAlphabetic(5) : "";
        String firstName = (firstNameParam != null) ? firstNameParam + RandomStringUtils.randomAlphabetic(5) : "";

        return new CourierDataLombok(login, password, firstName);
    }

}
