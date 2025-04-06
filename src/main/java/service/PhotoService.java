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

    public List<Photo> getPhoto(Rover rover, int sol, String camera) {

        try {
            List<Photo> cashedPhoto = photoDao.getPhotoFromBase(rover, sol, camera);
            if(!cashedPhoto.isEmpty()) {
                return cashedPhoto;
            }
            else {
                List<Photo> newPhoto = nasaApiClient.getPhotos(rover, sol, camera);
                photoDao.savePhoto(newPhoto);
                return newPhoto;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Ошибка работы с БД", e);
        }
    }
}
