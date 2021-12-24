import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

public class Histogram {
    public static JFreeChart createHistogram(String title, Connection conn, String XAxis, String YAxis) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            addAveragePriceToDataset(conn, dataset, "Аквариум");
            addAveragePriceToDataset(conn, dataset, "Блок питания для ламп");
            addAveragePriceToDataset(conn, dataset, "Фильтр для аквариума");
            addAveragePriceToDataset(conn, dataset, "Верхний нож");
            addAveragePriceToDataset(conn, dataset, "Туалет Catidea");
            addAveragePriceToDataset(conn, dataset, "Гидропонная установка");
            addAveragePriceToDataset(conn, dataset, "Измельчитель зерна");
            addAveragePriceToDataset(conn, dataset, "Нагреватель для аквариума");
            addAveragePriceToDataset(conn, dataset, "Гнездо для кур несушек");
            addAveragePriceToDataset(conn, dataset, "Помпа для аквариума");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ChartFactory.createBarChart(
                title,
                XAxis,
                YAxis,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
    }

    private static void addAveragePriceToDataset(Connection conn, DefaultCategoryDataset dataset, String rowKey) throws SQLException {
        dataset.addValue(Db.getAveragePrice(conn, rowKey), rowKey, "");
    }

    public static void saveAsPng(JFreeChart chart, String name, int width, int height) {
        try {
            ChartUtilities.saveChartAsPNG(new File(name + ".png"), chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Гистограмма \"" + name + "\" была успешно сохранена!");
    }
}