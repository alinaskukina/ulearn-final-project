import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        var connection = Db.getConnection("jdbc:sqlite:./catalog.sqlite");
        try {
            var data = Csv.ParseCsv("./catalog.csv");
            assert connection != null;
            Db.updateProducts(connection, data);
            var histogram = Histogram.createHistogram("Средняя цена",
                    connection,
                    "Названия товаров",
                    "Средняя цена, р.");
            Histogram.saveAsPng(histogram, "Task1", 800, 500);
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
