import java.sql.Connection;
import java.sql.DriverManager;

public class Db {
    public static Connection getConnection(String path){
        try {
            Class.forName("org.sqlite.JDBC");
            var conn = DriverManager.getConnection(path);
            System.out.println("Подключение к базе данных выполнено успешно!");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
