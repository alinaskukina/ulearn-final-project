import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Подключаемся к БД
        var connection = Db.getConnection("jdbc:sqlite:./catalog.sqlite");
        try {
            // Получаем лист Product с помощью нашего парсера
            var data = Csv.ParseCsv("./catalog.csv");
            assert connection != null;
            // Вносим изменения в БД (обновляем БД данными из catalog.csv)
            Db.updateProducts(connection, data);
            // Создаем гистограмму средних цен товаров из БД
            var histogram = Histogram.createHistogram("Средняя цена",
                    connection,
                    "Названия товаров",
                    "Средняя цена, р.");
            // Сохраняем гистограмму в папку проекта гистограмму
            Histogram.saveAsPng(histogram, "Task1", 800, 500);
            // Закрываем соединение с БД
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
