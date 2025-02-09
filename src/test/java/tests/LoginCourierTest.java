package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import ru.practikum.yandex.api.CourierApi;
import ru.practikum.yandex.model.CourierDataLombok;
import ru.practikum.yandex.model.LoginDataLombok;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.practikum.yandex.model.CourierGenerator.getRandomCourier;

public class LoginCourierTest {

        protected String courierId;
        protected LoginDataLombok LoginDataLombok;

        @After
        public void cleanUp() {
            if (courierId != null) {
                CourierApi courierApi = new CourierApi();
                // Удаляем курьера по его ID
                courierApi.deleteCourier(courierId);
            }
        }

    @DisplayName("Check courier can be authorized")
    @Test
    public void courierCanBeAuthorizedTest() {
        // Генерация случайного курьера
        CourierDataLombok courierDataLombok = getRandomCourier("Vlad54321", "password54321", "Vlad");
        CourierApi courierApi = new CourierApi();

        // Создание курьера
        ValidatableResponse createResponse = courierApi.createCourierLombok(courierDataLombok);
        createResponse.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));

        // Попытка авторизации с использованием логина и пароля из созданного курьера
        LoginDataLombok loginDataLombok = new LoginDataLombok(courierDataLombok.getLogin(), courierDataLombok.getPassword());
        ValidatableResponse response = courierApi.loginCourier(loginDataLombok);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(notNullValue()));
    }


    @DisplayName("Check cannot Authorized courier without Login")
    @Test
    public void cannotAuthorizedCourierWithoutLoginTest() {

        LoginDataLombok loginDataLombok = new LoginDataLombok(null, "password54321");

        CourierApi courierApi = new CourierApi();
        //вызываем метод
        ValidatableResponse response = courierApi.loginCourier(loginDataLombok);

        // Проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check cannot Authorized courier without Password")
    @Description("этот тест упадет, тк ОР HTTP1.1 404 Not Found ФР HTTP 1.1 504 Gateway time out")

    public void cannotAuthorizedCourierWithoutPasswordTest() {

        LoginDataLombok loginDataLombok = new LoginDataLombok("Vlad54321", null);


        CourierApi courierApi = new CourierApi();
        //вызываем метод
        ValidatableResponse response = courierApi.loginCourier(loginDataLombok);

        // Проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check cannot Authorized courier with incorrect Login")
    public void cannotAuthorizedCourierWithIncorrectLoginTest() {

        LoginDataLombok loginDataLombok = new LoginDataLombok("Vlad55554321","password54321");

        CourierApi courierApi = new CourierApi();
        //вызываем метод
        ValidatableResponse response = courierApi.loginCourier(loginDataLombok);

        // Проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check cannot Authorized courier with incorrect Password")
    public void cannotAuthorizedCourierWithIncorrectPasswordTest() {

        LoginDataLombok loginDataLombok = new LoginDataLombok("Vlad54321","password55554321");

        CourierApi courierApi = new CourierApi();
        //вызываем метод
        ValidatableResponse response = courierApi.loginCourier(loginDataLombok);

        // Проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }
    }


//Логин курьера
//Проверь:
//+курьер может авторизоваться;
//+для авторизации нужно передать все обязательные поля;
//+система вернёт ошибку, если неправильно указать логин или пароль;
//+если какого-то поля нет, запрос возвращает ошибку;
//+если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
//+успешный запрос возвращает id.