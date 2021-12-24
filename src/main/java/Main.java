import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        var connection = Db.getConnection("jdbc:sqlite:./catalog.sqlite");
        try {
            var data = Csv.ParseCsv("./catalog.csv");
            Db.updateProducts(connection, data);
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
