import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        var connection = Db.getConnection("jdbc:sqlite:./catalog.sqlite");
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
