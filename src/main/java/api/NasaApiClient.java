package api;

import com.google.gson.Gson;
import io.restassured.response.Response;
import model.Photo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class NasaApiClient {

    public static final String URI = "https://api.nasa.gov/mars-photos/api/v1";
    public static final String apiKey = "DEMO_KEY";

    private final Gson gson = new Gson();


    public Response getResponse(String rover, int inSol, String cameraName) {
                return given()
                .baseUri(URI)
                .queryParam("sol", inSol)
                .queryParam("camera", cameraName)
                .queryParam("api_key", apiKey)
                .when()
                .get("/rovers/" + rover + "/photos");
    }

    public List<Photo> getPhotos(String rover, int inSol, String cameraName) {
        Response response = getResponse(rover, inSol, cameraName);
        List<Map<String, Object>> photosData = response
                .jsonPath()
                .getList("photos");

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("API Error" + response.getBody().asString());
        }

        return photosData.stream()
                .map(photo -> {
                    Map<String, Object> camera = (Map <String, Object>) photo.get("camera");
                    Map<String, Object> roverData = (Map<String, Object>) photo.get("rover");
                    return new Photo(
                            (Integer) photo.get("id"),
                            (Integer) photo.get("sol"),
                            (String) camera.get("name"),
                            (String) photo.get("img_src"),
                            (String) roverData.get("name")
                    );
                        }
                        )
                .collect(Collectors.toList());
    }
}
