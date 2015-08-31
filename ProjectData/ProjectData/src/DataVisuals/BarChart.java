package DataVisuals;

/**
 * Created by jls on 3/9/15.
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.List;

public class BarChart extends JFrame {

    private static final int HEIGHT = 270;
    private static final int WIDTH = 800;

    private JFreeChart barChart;

    // Create dataset
    private final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    public BarChart(final String chartTitle, final String category, List<DatasetObject> obj, boolean percentage) {
        super(chartTitle);
        if (!percentage) {
            barChart = ChartFactory.createBarChart(
                    chartTitle, // Chart title
                    category, // Domain axis label
                    "Integer", // Range axis label
                    createDataset(obj, percentage), // Data
                    PlotOrientation.VERTICAL, // Orientation
                    true, // Include Legend
                    true, // Tooltips
                    false // URLS
            );
            CategoryPlot plot = (CategoryPlot) barChart.getPlot();
            final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
            this.add(chartPanel);
            this.setVisible(false);
        }
        if (percentage) {
            barChart = ChartFactory.createBarChart(
                    chartTitle, // Chart title
                    category, // Domain axis label
                    "%", // Range axis label
                    createDataset(obj, percentage), // Data
                    PlotOrientation.VERTICAL, // Orientation
                    true, // Include Legend
                    true, // Tooltips
                    false // URLS
            );
            CategoryPlot plot = (CategoryPlot) barChart.getPlot();
            final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setRange(0, 100);
//            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//            rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
            rangeAxis.setNumberFormatOverride(new DecimalFormat("0"));
            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
            this.add(chartPanel);
            this.setVisible(false);
        }
    }

    private double calculatePercentage(List<DatasetObject> obj, int value) {
        double total = 0;
        for (DatasetObject o : obj) {
            total += o.getValue();
        }
        return value / total * 100;
    }

    private CategoryDataset createDataset(List<DatasetObject> obj, boolean percentage) {
        if (percentage) {
            for (int i = 0; i < obj.size(); i++) {
                addValue(calculatePercentage(obj, obj.get(i).getValue()), obj.get(i).getName(), obj.get(i).getCategory());
            }
        } else if (!percentage) {
            for (int i = 0; i < obj.size(); i++) {
                addValue(obj.get(i).getValue(), obj.get(i).getName(), obj.get(i).getCategory());
            }
        }
        return dataset;
    }

    private void addValue(double data, String medium, String category) {
        dataset.addValue(data, medium, category);
    }
}
