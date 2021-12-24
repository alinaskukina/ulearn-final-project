import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

    public static void updateProducts(Connection conn, List<Product> products) {
        Connection connection;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./catalog.sqlite");
            statement = connection.createStatement();
            createProductsIfNotExists(statement);
            int i = 0;

            while (i < products.size()){
                var product = products.get(i);
                String name = product.Name;
                String id = product.Id;
                double price = product.Price;
                int amount = product.Amount;

                insertInProducts(statement, name, id, price, amount);
                i++;
            }
            statement.executeBatch();
            statement.close();
            connection.close();
            System.out.printf("Successfully updated %s lines%n", i);
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private static void insertInProducts(Statement statement, String name, String id, double price, int amount) throws SQLException {
        String query = "INSERT INTO Products VALUES (" +
                "'" + name + "', " +
                "'" + id + "', " +
                "'" + price +  "', " +
                "'" + amount + "')";
        statement.addBatch(query);
    }

    private static void createProductsIfNotExists(Statement statement) throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS Products");
        statement.executeUpdate("CREATE TABLE Products " +
                "(NAME     CHAR(50) NOT NULL, " +
                "PRODUCT_ID CHAR(10) PRIMARY KEY NOT NULL," +
                "PRICE   FLOAT  NOT NULL, " +
                "AMOUNT      INT  NOT NULL )");
    }

    public static double getMedianPrice(Connection conn, String name) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("SELECT AVG(PRICE) FROM Products " +
                "WHERE NAME LIKE '%"+name+"%'");
        var result = statement.getResultSet();
        return Double.parseDouble(result.getString(1));
    }
}
