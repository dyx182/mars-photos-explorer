package db;
import lombok.AllArgsConstructor;
import model.Photo;
import model.Rover;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PhotoDao {

    private final Connection connection;

    public void savePhoto(List<Photo> photos) throws SQLException {
        String sql = "INSERT INTO photos (id, sol, camera, img_src, rover) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);

        for (Photo photo : photos) {
            statement.setInt(1, photo.getId());
            statement.setInt(2, photo.getSol());
            statement.setString(3, photo.getCamera());
            statement.setString(4, photo.getImg_src());
            statement.setString(5, photo.getRover().getName());
            statement.addBatch();
        }

        statement.executeBatch();
        statement.close();
    }

    public List<Photo> getPhotoFromBase(Rover rover, int sol, String camera) throws SQLException {
        String sql = "SELECT * FROM photos WHERE rover = ? AND sol = ? AND camera = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, rover.getName());
        statement.setInt(2, sol);
        statement.setString(3, camera);

        ResultSet result = statement.executeQuery();
        List<Photo> photos = new ArrayList<>();

        while (result.next()) {
            Photo photo = new Photo();
            photo.setId(result.getInt("id"));
            photo.setSol(result.getInt("sol"));
            photo.setCamera(result.getString("camera"));
            photo.setImg_src(result.getString("img_src"));
            photo.setRover(Rover.valueOf(result.getString("rover")));
            photos.add(photo);
        }

        result.close();
        statement.close();
        return photos;
    }
}
