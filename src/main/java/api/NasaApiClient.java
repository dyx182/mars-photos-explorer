package api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Photo;
import model.Rover;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NasaApiClient {

    public static final String URI = "https://api.nasa.gov/mars-photos/api/v1";
    public static final String apiKey = "DEMO_KEY";

    private final Gson gson = new Gson();

    public List<Photo> getPhotos(Rover rover, int sol, String camera) {
        Response response = RestAssured.given()
                .baseUri(URI)
                .queryParam("sol", sol)
                .queryParam("camera", camera)
                .queryParam("api_key", apiKey)
                .when()
                .get("/rovers/" + rover.name().toLowerCase() + "/photos");

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("API Error" + response.getBody().asString());
        }

        JsonPath jsonPath = response.jsonPath();

        return response.jsonPath()
                .getList("photos", Photo.class)
                .stream()
                .map(photo -> {
                    // Дополнительно устанавливаем rover, так как он вложен в JSON
                    photo.setRover(rover);
                    return photo;
                })
                .collect(Collectors.toList());
    }
}
