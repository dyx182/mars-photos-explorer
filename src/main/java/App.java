import api.NasaApiClient;
import db.DataBase;
import db.PhotoDao;
import model.Photo;
import model.Rover;
import service.PhotoService;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {

        //TODO https://api.nasa.gov/

        try(Connection connection = DataBase.getConnection()) {
            NasaApiClient nasaApiClient = new NasaApiClient();
            PhotoDao photoDao = new PhotoDao(connection);
            PhotoService service = new PhotoService(nasaApiClient, photoDao);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Выберите  CURIOSITY, PERSEVERANCE, OPPORTUNITY, SPIRIT");
            String rover = scanner.next();

            System.out.println("Укажите сол");
            int sol = scanner.nextInt();
            scanner.nextLine();


            //TODO в зависимости от марсохода сделать возможность ввода камер
            System.out.println("Укажите Камеру");
            String camera = scanner.nextLine();

            List<Photo> photos = service.getPhoto(rover, sol, camera);
            for (Photo  photo : photos) {
                System.out.println(photo.getImg_src());
            }
        }
        catch (Exception e) {
            //TODO Добавить ошибку
            System.err.println("Ошибка вывода" + e.getMessage());
        }
    }
}
