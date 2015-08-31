package DataVisuals;

/**
 *
 * @author Mike
 */

import Calendar.CustomDate;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LineChart extends JFrame {
    private static final int HEIGHT = 640;
    private static final int WIDTH = 480;

    private JFreeChart lineChart;

    // Create dataset
    private final XYSeriesCollection dataset = new XYSeriesCollection();
    private final XYSeries series1 = new XYSeries("SS-Rotterdam");
    private final XYSeries series2 = new XYSeries("Hotel New York");
    private final XYSeries series3 = new XYSeries("Rotterdam Centraal Station");
    private final XYSeries series4 = new XYSeries("Markthal");
    private final XYSeries series5 = new XYSeries("Euromast");

    public LineChart(final String chartTitle, final String category, List<DatasetObject> obj, String unitOfMeasaure) {
        super(chartTitle);
        lineChart = ChartFactory.createXYLineChart(
                chartTitle, // Chart title
                "Time", // Domain axis label
                category, // Range axis label
                createDataset(obj, unitOfMeasaure), // Data
                PlotOrientation.VERTICAL, // Orientation
                true, // Include Legend
                true, // Tooltips
                false // URLS
        );
        lineChart.setBackgroundPaint(Color.white);
//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);

        // get a reference to the plot for further customisation...
        final XYPlot plot = lineChart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
        this.add(chartPanel);
        this.setVisible(false);
    }

    private XYSeriesCollection createDataset(List<DatasetObject> obj, String unitOfMeasure) {
        XYSeriesCollection dataset = null;
//        if (unitOfMeasure.equals("Year"))
//            dataset = createDatasetYear(obj);
//        if (unitOfMeasure.equals("Month"))
//            dataset = createDatasetMonth(obj);
//        if (unitOfMeasure.equals("Day"))
//            dataset = createDatasetDay(obj);
        if (unitOfMeasure.equals("Hour"))
            dataset = createDatasetHour(obj);
        if (unitOfMeasure.equals("Minute"))
            dataset = createDatasetMinute(obj);
        if (unitOfMeasure.equals("Second"))
            dataset = createDatasetSecond(obj);
        return dataset;
    }

    private XYSeriesCollection createDatasetHour(List<DatasetObject> obj) {

        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).getName().equals("SS-Rotterdam"))
                series1.add(Double.parseDouble(CustomDate.filterHour(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Hotel New York"))
                series2.add(Double.parseDouble(CustomDate.filterHour(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Rotterdam Centraal Station"))
                series3.add(Double.parseDouble(CustomDate.filterHour(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Markthal"))
                series4.add(Double.parseDouble(CustomDate.filterHour(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Euromast"))
                series5.add(Double.parseDouble(CustomDate.filterHour(obj.get(i).getTime())), obj.get(i).getValue());
        }
        addSeries(series1);
        addSeries(series2);
        addSeries(series3);
        addSeries(series4);
        addSeries(series5);
        return dataset;
    }

    private XYSeriesCollection createDatasetMinute(List<DatasetObject> obj) {
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).getName().equals("SS-Rotterdam"))
                series1.add(Double.parseDouble(CustomDate.filterMinute(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Hotel New York"))
                series2.add(Double.parseDouble(CustomDate.filterMinute(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Rotterdam Centraal Station"))
                series3.add(Double.parseDouble(CustomDate.filterMinute(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Markthal"))
                series4.add(Double.parseDouble(CustomDate.filterMinute(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Euromast"))
                series5.add(Double.parseDouble(CustomDate.filterMinute(obj.get(i).getTime())), obj.get(i).getValue());
        }
        addSeries(series1);
        addSeries(series2);
        addSeries(series3);
        addSeries(series4);
        addSeries(series5);
        return dataset;
    }

    private XYSeriesCollection createDatasetSecond(List<DatasetObject> obj) {
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).getName().equals("SS-Rotterdam"))
                series1.add(Double.parseDouble(CustomDate.filterSecond(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Hotel New York"))
                series2.add(Double.parseDouble(CustomDate.filterSecond(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Rotterdam Centraal Station"))
                series3.add(Double.parseDouble(CustomDate.filterSecond(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Markthal"))
                series4.add(Double.parseDouble(CustomDate.filterSecond(obj.get(i).getTime())), obj.get(i).getValue());
            if (obj.get(i).getName().equals("Euromast"))
                series5.add(Double.parseDouble(CustomDate.filterSecond(obj.get(i).getTime())), obj.get(i).getValue());
        }
        addSeries(series1);
        addSeries(series2);
        addSeries(series3);
        addSeries(series4);
        addSeries(series5);
        return dataset;
    }

    private void addSeries(XYSeries series) {
        dataset.addSeries(series);
    }
}
