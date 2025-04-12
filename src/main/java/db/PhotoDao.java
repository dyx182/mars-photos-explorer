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
        String metadataSql = "MERGE INTO photos_metadata KEY(id) VALUES (?, ?, ?, ?)";
        String urlSql = "MERGE INTO photo_urls KEY(id) VALUES (?, ?)";

        try (PreparedStatement metadataStmt = connection.prepareStatement(metadataSql);
             PreparedStatement urlStmt = connection.prepareStatement(urlSql);) {

            for (Photo photo : photos) {
                metadataStmt.setInt(1, photo.getId());
                metadataStmt.setInt(2, photo.getSol());
                metadataStmt.setString(3, photo.getCameraName());
                metadataStmt.setString(4, photo.getRover());
                metadataStmt.addBatch();

                urlStmt.setInt(1, photo.getId());
                urlStmt.setString(2, photo.getImg_src());
                urlStmt.addBatch();
            }

            metadataStmt.executeBatch();
            urlStmt.executeBatch();
        }
}
        public List<Photo> getPhotoFromBase(String rover,int sol, String camera) throws SQLException {

            String sql = """
            SELECT m.id, m.sol, m.camera, u.img_src, m.rover 
            FROM photos_metadata m
            JOIN photo_urls u ON m.id = u.id
            WHERE m.rover = ? AND m.sol = ? AND m.camera = ?
            """;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, rover);
            statement.setInt(2, sol);
            statement.setString(3, camera);

            ResultSet result = statement.executeQuery();
            List<Photo> photos = new ArrayList<>();

            while (result.next()) {
                Photo photo = new Photo();
                photo.setId(result.getInt("id"));
                photo.setSol(result.getInt("sol"));
                photo.setCameraName(result.getString("camera"));
                photo.setImg_src(result.getString("img_src"));
                photo.setRover(result.getString("rover"));
                photos.add(photo);
            }

            result.close();
            statement.close();
            return photos;
        }
    }
