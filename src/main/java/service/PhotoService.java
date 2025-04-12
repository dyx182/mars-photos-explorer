package service;

import api.NasaApiClient;
import db.PhotoDao;
import lombok.AllArgsConstructor;
import model.Photo;
import model.Rover;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class PhotoService {

    private final NasaApiClient nasaApiClient;
    private final PhotoDao photoDao;

    public List<Photo> getPhoto(String rover, int sol, String camera) {

        try {
            List<Photo> cashedPhotos = photoDao.getPhotoFromBase(rover, sol, camera);
            if(!cashedPhotos.isEmpty()) {
                return cashedPhotos;
            }
            else {
                List<Photo> newPhotos = nasaApiClient.getPhotos(rover, sol, camera);
                if (newPhotos != null && !newPhotos.isEmpty()) {
                    photoDao.savePhoto(newPhotos);
                }
                return newPhotos;
            }
        }
        catch (SQLException e) {
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Ошибка работы с БД", e);
        }
    }
}
