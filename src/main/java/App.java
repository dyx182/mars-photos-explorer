import api.NasaApiClient;
import db.DataBase;
import db.PhotoDao;
import model.Photo;
import service.PhotoService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {

        //TODO https://api.nasa.gov/

        try (Connection connection = DataBase.getConnection()) {
            NasaApiClient nasaApiClient = new NasaApiClient();
            PhotoDao photoDao = new PhotoDao(connection);
            PhotoService service = new PhotoService(nasaApiClient, photoDao);

            Scanner scanner = new Scanner(System.in);

            String rover = scannerSetRover(scanner);
            scanner.nextLine();
            String camera = scannerSetCamera(scanner);

            //TODO в зависимости от марсохода сделать возможность ввода камер

            while (true) {
                int sol = scannerSetSol(scanner);
                scanner.nextLine();
                List<Photo> photos = service.getPhoto(rover, sol, camera);

                if (!photos.isEmpty()) {
                    for (Photo photo : photos) {
                        System.out.println(photo.getImg_src());
                    }
                } else {
                    System.out.println("Фото не найдено, выберите другой sol");
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка вывода" + e.getMessage());
        }
    }

    private static String scannerSetRover(Scanner scanner) {
        System.out.println("Выберите  CURIOSITY, PERSEVERANCE, OPPORTUNITY, SPIRIT");
        return scanner.next();
    }

    private static int scannerSetSol(Scanner scanner) {
        System.out.println("Укажите сол");
        return scanner.nextInt();
    }

    private static String scannerSetCamera(Scanner scanner) {
        System.out.println("Укажите Камеру");
        return scanner.nextLine();
    }
}
