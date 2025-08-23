package com.demoqa.api;

import com.demoqa.utils.AllureListener;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class BaseApi {

    protected static RequestSpecification spec() {
        return given()
                .filter(AllureListener.withCustomTemplates())
                .contentType(JSON)
                .log().all();
    }

    protected static RequestSpecification spec(String token) {
        return spec()
                .auth().oauth2(token);
    }
}
