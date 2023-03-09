package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.entity.Arrangement;
import zh.arss.entity.Request;
import zh.arss.entity.User;
import zh.arss.utilities.PasswordHasher;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainService {
    private static final MainService MAIN_SERVICE = new MainService();

    private MainService() {
    }

    public static MainService getInstance() {
        return MAIN_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private User user;

    public String authorization(String login, String password) {
        try {
            if (login.equals(""))
                return "Введите логин";
            if (password.equals(""))
                return "Введите пароль";

            password = PasswordHasher.hashingPassword(password);
            String response = databaseHandler.authorization(login, password);
            if (response.equals("login not found")) {
                return "Пользователь не найден";
            }
            if (response.equals("incorrect password")) {
                return "Неверный пароль";
            }

            user = databaseHandler.getUser(Long.parseLong(response));

            return "success";
        } catch (SQLException e) {
            return "Неизвестная ошибка";
        }
    }

    public String registration(String login, String password) {
        if (login.equals(""))
            return "Введите логин";
        if (login.length() < 5)
            return "Логин короче 5 символов";
        if (password.equals(""))
            return "Введите пароль";
        if (password.length() < 5)
            return "Пароль короче 5 символов";

        password = PasswordHasher.hashingPassword(password);

        String response = databaseHandler.registration(login, password);
        if (response.equals("error")) {
            return "Ошибка регистрации";
        }
        return response;
    }

    public String createRequest(String date, String email, String phone, String description) {
        if (email.equals("")) {
            return "Ошибка";
        }
        if (phone.equals("")) {
            return "Ошибка";
        }

        date = date.split(": ")[1];
        String service;
        if (date.split(" ")[1].equals("ночь")) {
            service = "Аренда";
        } else {
            service = "Запись";
        }
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(new Random().nextInt(9));
        }

        try {
            databaseHandler.insertRequest(user.getIdUser(), date, service, code.toString(), email, phone, description);
            return code.toString();
        } catch (Exception exception) {
            return "Ошибка";
        }
    }

    public User getUser() {
        return user;
    }

    public boolean buyArrangement(Arrangement arrangement, String license){
        try {
            license = license.substring(license.indexOf('\'') + 1, license.length() - 3);
            databaseHandler.buyArrangement(arrangement.getIdArrangement());
            String fileName = (user.getLogin() + "_" + arrangement.getName() + "_" + license + ".txt")
                    .replace(" ", "_").replace(":", "_")
                    .replace(",", "_").replace("\"", "_")
                    .replace("?", "_").replace("|", "_");
            PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8);
            writer.println("Файл лицензии, который Валентин так и не отправил\n\n\n\n");
            writer.println("Покупка аранжировки " + arrangement.getName());
            writer.println("по лицензии " + license.substring(0, license.indexOf('-')) + "\n\n");
            writer.println("Покупатель " + user.getLogin() + "\n\n");
            writer.println("Общая стоимость: " + license.substring(license.indexOf('-') + 2) + " рублей");
            writer.close();
            Files.move(Paths.get(fileName), Paths.get( "licenses\\" + fileName));
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public List<Request> getAllRequest() {
        return databaseHandler.getAllRequest();
    }
    public List<Arrangement> getAllArrangement() {
        return databaseHandler.getAllArrangement();
    }
}
