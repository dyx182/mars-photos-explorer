import api.NasaApiClient;
import db.DataBase;
import db.PhotoDao;
import model.Photo;
import service.PhotoService;

import java.sql.Connection;
import java.util.Arrays;
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

            while (true) {
                String rover = scannerSetRover(scanner);
                scanner.nextLine();
                String camera = scannerSetCamera(scanner, rover);
                boolean check = false;

                int attempts = 3;
                while (attempts-- > 0) {
                    int sol = scannerSetSol(scanner);
                    scanner.nextLine();
                    List<Photo> photos = service.getPhoto(rover, sol, camera);
                    if (!photos.isEmpty()) {
                        for (Photo photo : photos) {
                            System.out.println(photo.getImg_src());
                        }
                        check = true;
                        break;
                    } else {
                        System.out.println("Фото не найдено");
                    }
                }
                if (!check) {
                    System.out.println("Фото по заданным параемтрам не найдены, выберите другие данные \n");

                } else {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка вывода" + e.getMessage());
        }
    }

    private static String scannerSetRover(Scanner scanner) {
        System.out.println("Выберите ровер " + Arrays.toString(Rovers.values()));
        return scanner.next();
    }

    private static int scannerSetSol(Scanner scanner) {
        System.out.println("Укажите сол");
        return scanner.nextInt();
    }

    private static String scannerSetCamera(Scanner scanner, String rover) {
        switch (rover) {
            case "CURIOSITY":
                System.out.println("Выберите Камеру" + Arrays.asList(CuriosityCams.values()));
                break;
            case "PERSEVERANCE", "SPIRIT":
                System.out.println("Выберите Камеру" + Arrays.asList(OpportunitySpiritCams.values()));
                break;
        }
        return scanner.nextLine();
    }

    private enum Rovers {
        CURIOSITY,
        OPPORTUNITY,
        SPIRIT
    }

    private enum CuriosityCams {
        FHAZ, RHAZ, MAST, CHEMCAM, MAHLI, MARDI, NAVCAM
    }

    private enum OpportunitySpiritCams {
        FHAZ, RHAZ, NAVCAM, PANCAM, MINITES
    }
}
