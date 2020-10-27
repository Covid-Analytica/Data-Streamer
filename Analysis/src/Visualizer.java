import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Visualizer extends JFrame{


    List<CovidReport> actual;
    List<CovidReport> predicted;

    public static void display(List actual, List predicted) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Visualizer(actual, predicted).setVisible(true);
            }
        });
    }

    public Visualizer(List actual, List predicted) {
        super("KNN analysis");

        this.actual = actual;
        this.predicted = predicted;


        JPanel chartPanel = createPanel();
        add(chartPanel, BorderLayout.CENTER);

        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.white);
    }

    private JPanel createPanel() {
        String chartTitle = "Actual Cases vs Predicted Cases";
        String xAxisLabel = "Days";
        String yAxisLabel = "Log(Number of Cases)";

        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
                xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        customizeChart(chart);


        File imageFile = new File("C:\\kth\\s1p1\\ID2221-Data-Intensive-Computing\\lab\\DWTanalysis\\graphimages\\XYLineChart" + System.currentTimeMillis() +".png");
        int width = 640;
        int height = 480;

        try {
            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ChartPanel(chart);
    }

    private void customizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setSeriesPaint(1, Color.GREEN);

        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesStroke(1, new BasicStroke(1.0f));

        plot.setOutlinePaint(Color.WHITE);

        // sets renderer for lines
        plot.setRenderer(renderer);

        // sets plot background
        plot.setBackgroundPaint(Color.WHITE);

        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Actual Cases");
        XYSeries series2 = new XYSeries("Predicted Cases");

        for (int i = 0; i < actual.size(); i++) {
            series1.add(i, Math.log(actual.get(i).cases));
        }

        for (int i = 0; i < predicted.size(); i++) {
            series2.add(i, Math.log(predicted.get(i).cases));

        }

        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }
}