package com.juaracoding.moviedb;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestMovieDB {
    String baseMovieURL = "https://api.themoviedb.org/3/movie";
    String apiKey = "api_key=12a968b37631b9e9209083070dd89612";
    String authorizationKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMmE5NjhiMzc2MzFiOWU5MjA5MDgzMDcwZGQ4OTYxMiIsInN1YiI6IjY0MDcyZDgxN2E0ZWU3MDA3YmJhZGVlMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ItEkyg23Kg11f14G7lgFe_gsG_AET_7yQYiKbHSv9Xw";
    @Test
    public void testGetMovieNowPlaying() {
        String nowPlayingURL = baseMovieURL+"/now_playing?"+apiKey+"&language=en-US&page=1";
        Response res = RestAssured.get(nowPlayingURL);
        System.out.println(res.getBody().asString());
        System.out.println(res.getStatusCode());

        given().get(nowPlayingURL)
                .then()
                .statusCode(200)
                .body("results.id[0]",equalTo(631842))
                .body("results.original_title[0]",equalTo("Knock at the Cabin"));
        System.out.println();
    }

    @Test
    public void testGetMoviePopular() {
        String popularMovieURL = baseMovieURL+"/popular?"+apiKey+"&language=en-US&page=1";
        Response res = RestAssured.get(popularMovieURL);
        System.out.println(res.getBody().asString());
        System.out.println(res.getStatusCode());

        given().get(popularMovieURL)
                .then()
                .statusCode(200)
                .body("results.id[2]",equalTo(1011679))
                .body("results.original_title[2]",equalTo("Shark Side of the Moon"));
        System.out.println();
    }

    @Test
    public void testPostRating() {
        String movieRatingURL = baseMovieURL+"/750929/rating?"+apiKey;
        JSONObject req = new JSONObject();
        req.put("value","7.5");


        given()
                .header("Authorization",authorizationKey)
                .header("Content-Type","application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(req.toJSONString())
                .when()
                .post(movieRatingURL)
                .then()
                .statusCode(201)
                .log().all();


    }
}
