package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import ru.practikum.yandex.api.CourierApi;
import ru.practikum.yandex.model.CourierDataLombok;

import static org.hamcrest.CoreMatchers.is;
import static ru.practikum.yandex.model.CourierGenerator.getRandomCourier;

@Feature("Create courier")
public class CreateCourierTest {

    protected String courierId;
    protected CourierDataLombok courierDataLombok;

    @After
    public void cleanUp() {
        if (courierId != null) {
            CourierApi courierApi = new CourierApi();
            // Удаляем курьера по его ID
            courierApi.deleteCourier(courierId);
        }
    }

    @DisplayName("Check courier can be created")
    @Test
    public void courierCanBeCreatedTest() {

        courierDataLombok = getRandomCourier("Vlad54321", "password54321", "Vlad");

        CourierApi courierApi = new CourierApi();

        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);
        // Получаем ID курьера
        courierId = response.extract().path("id");

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @DisplayName("Check courier can be created with required fields only")
    @Test
    public void courierCanBeCreatedWithRequiredFieldsOnlyTest() {

        courierDataLombok = getRandomCourier("Vlad54321", "password54321", null);

        CourierApi courierApi = new CourierApi();

        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);

        courierId = response.extract().path("id");

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }


    @Test
    @DisplayName("Check cannot create two identical couriers")
    @Description("Этот тест упадет, тк в документации версии 1.0.0 https://qa-scooter.praktikum-services.ru/docs/#api-Courier-CreateCourier ОР message: Этот логин уже используется ФР message: Этот логин уже используется. Попробуйте другой.")
    // описание теста

    public void cannotCreateTwoIdenticalCouriersTest() {

        // Создаем первого курьера
        courierDataLombok = getRandomCourier("Vlad54321", "password54321", "Vlad");

        CourierApi courierApi = new CourierApi();

        ValidatableResponse responseCreateFirst = courierApi.createCourierLombok(courierDataLombok);
        courierId = responseCreateFirst.extract().path("id");

        // Теперь пытаемся создать курьера с тем же логином
        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется"));
    }

    @DisplayName("Check cannot create courier without Login")
    @Test
    public void cannotCreateCourierWithoutLoginTest() {
        // Создаем курьера
        courierDataLombok = getRandomCourier(null, "password54321", null);

        CourierApi courierApi = new CourierApi();

        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Check cannot create courier without Password")
    @Test
    public void cannotCreateCourierWithoutPasswordTest() {
        // Создаем курьера
        courierDataLombok = getRandomCourier("Vlad54321", null, null);

        CourierApi courierApi = new CourierApi();

        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);


        response.log().all() //вывод лога
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}


