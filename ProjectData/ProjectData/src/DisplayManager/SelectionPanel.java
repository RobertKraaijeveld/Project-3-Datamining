package DisplayManager;

import Calendar.CustomDate;
import Calendar.DatePicker;
import ConnectionManager.ConnectionManager;
import DataVisuals.BarChart;
import DataVisuals.DatasetObject;
import DataVisuals.LineChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by jls on 2/15/15.
 */
public class SelectionPanel extends JPanel {

    private GridBagConstraints gbc;

    private JComboBox mediaBox;
    private JComboBox categoryBoxTwitter;
    private JComboBox categoryBoxFacebook;
    private JComboBox unitOfMeasureBox;

    private java.util.List<DatasetObject> datasetObject = new ArrayList<DatasetObject>();

    private JLabel endDate;
    private JLabel startDate;
    private JLabel selectMedia;
    private JLabel reminderOne;
    private JLabel reminderTwo;
    private JLabel reminderThree;
    private JLabel selectCategory;
    private JLabel unitOfMeasure;

    private JButton findButton;

    private DatePicker startDateBox;
    private DatePicker endDateBox;

    protected BarChart barChart;
    protected BarChart barChartTwo;
    protected LineChart lineChart;

    private String[] media = {"", "Twitter", "Facebook"};
    private String[] valuesTwitter = {"", "Amount of Followers", "Amount of Tweets", "Amount of Foreigners", "Amount of Friends", "Amount of Favorites"};
    private String[] valuesFacebook = {"", "Amount of Likes", "Amount of Checkins", "Talking about Count", /*"Amount of Feeds"*/};
    private String[] unit = {"", /*"Year", "Month", "Day", */"Hour", "Minute", "Second"};

    protected SelectionPanel() {
        super(new GridBagLayout());
        // Initialize
        initComponents();
        // Create
        createComponents();
        createPanel();
        // Listen
        buttonListener();
        // State
        setInactiveState();
    }

    private void initComponents() {
        // Labels
        startDate = new JLabel("Start Date:", JLabel.LEFT);
        endDate = new JLabel("End Date:", JLabel.LEFT);
        selectMedia = new JLabel("Select your media:", JLabel.LEFT);
        selectCategory = new JLabel("Select your category:", JLabel.LEFT);
        unitOfMeasure = new JLabel(" Select your unit of measurement", JLabel.LEFT);
        reminderOne = new JLabel("This field can't be empty!", JLabel.LEFT);
        reminderTwo = new JLabel("This field can't be empty!", JLabel.LEFT);
        reminderThree = new JLabel("This field can't be empty!", JLabel.LEFT);
        // Buttons
        findButton = new JButton(" Find >> ");
        // ComboBoxes
        mediaBox = new JComboBox(media);
        categoryBoxTwitter = new JComboBox(valuesTwitter);
        categoryBoxFacebook = new JComboBox(valuesFacebook);
        unitOfMeasureBox = new JComboBox(unit);
        // DatePickers
        startDateBox = new DatePicker();
        endDateBox = new DatePicker();
        // GridBagConstraints + Insets
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 30, 5);
    }

    private void createComponents() {
        // Labels
        reminderOne.setVisible(false);
        reminderTwo.setVisible(false);
        reminderThree.setVisible(false);
        // Buttons
        findButton.setPreferredSize(new Dimension(100, 25));
        // ComboBoxes
        mediaBox.setPreferredSize(new Dimension(200, 25));
        categoryBoxTwitter.setPreferredSize(new Dimension(200, 25));
        categoryBoxFacebook.setPreferredSize(new Dimension(200, 25));
        categoryBoxTwitter.setVisible(false);
        categoryBoxFacebook.setVisible(false);
        unitOfMeasureBox.setPreferredSize(new Dimension(200, 25));
        unitOfMeasureBox.setVisible(false);
    }

    protected void setActiveState() {
        if (ConnectionManager.loggedIn) {
            findButton.setEnabled(true);
            mediaBox.setEnabled(true);
            categoryBoxFacebook.setEnabled(true);
            categoryBoxTwitter.setEnabled(true);
            unitOfMeasureBox.setEnabled(true);
        }
    }

    protected void setInactiveState() {
        if (!ConnectionManager.loggedIn) {
            findButton.setEnabled(false);
            selectCategory.setVisible(false);
            mediaBox.setEnabled(false);
            selectCategory.setEnabled(false);
            categoryBoxFacebook.setEnabled(false);
            categoryBoxTwitter.setEnabled(false);
            unitOfMeasure.setVisible(false);
            unitOfMeasure.setEnabled(false);
            unitOfMeasureBox.setEnabled(false);
        }
    }

    protected void setBarChart(BarChart chart) {
        this.barChart = chart;
    }

    protected void setBarChartTwo(BarChart chart) {
        this.barChartTwo = chart;
    }

    protected void setLineChart(LineChart chart) {
        this.lineChart = chart;
    }

    private void createPanel() {
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        this.add(reminderOne, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(startDate, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(startDateBox.getDatePicker(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(new JLabel(""), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        this.add(reminderTwo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(endDate, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        this.add(endDateBox.getDatePicker(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(new JLabel(""), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        this.add(reminderThree, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        this.add(selectMedia, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(mediaBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(selectCategory, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(categoryBoxFacebook, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(categoryBoxTwitter, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(unitOfMeasure, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(unitOfMeasureBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 0; // no growing fat
        gbc.weighty = 0; // no growing tall
        this.add(findButton, gbc);

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), "Select panel"));
    }

    protected void buttonListener() {
        findButton.addActionListener(e -> {
            if (Display.bar.isSelected())
                toggleBars();
            if (Display.graph.isSelected())
                toggleGraph();
        });
        mediaBox.addActionListener(e -> showCategory());
        categoryBoxTwitter.addActionListener(e -> getCategory());
        categoryBoxFacebook.addActionListener(e -> getCategory());
//        unitOfMeasureBox.addActionListener(e -> );
    }

    protected WindowAdapter barChartAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                barChart.setVisible(false);
                barChartTwo.setVisible(false);
                barChart.dispose();
                barChartTwo.dispose();
                setBarChart(null);
                setBarChartTwo(null);
            }
        };
    }

    protected WindowAdapter lineChartAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                lineChart.setVisible(false);
                lineChart.dispose();
                setLineChart(null);
            }
        };
    }

    protected void setResultsBarChart() {
        if (getMedia().equals(media[1]))
            retrieveFromDatabaseValuesTwitterBarchart();
        else if (getMedia().equals(media[2]))
            retrieveFromDatabaseValuesFacebookBarChart();
    }

    protected void setResultsLineChart() {
        if (getMedia().equals(media[1]))
            retrieveFromDatabaseValuesTwitterLineChart();
        else if (getMedia().equals(media[2]))
            retrieveFromDatabaseValuesFacebookLineChart();

    }

    protected int promptFrame() {
        int choice = JOptionPane.showOptionDialog(
                this, "There is already a statistic being displayed.\n" +
                        "Do you want to close the current one and display the new one?",
                "A statistic is already being displayed",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"Yes close!", "No thanks!"}, "No thanks"
        );
        return choice;
    }

    protected void showCategory() {
        if (getMedia().equals(media[1])) {
            categoryBoxFacebook.setVisible(false);
            categoryBoxTwitter.setVisible(true);
            selectCategory.setEnabled(true);
            selectCategory.setVisible(true);
        } else if (getMedia().equals(media[2])) {
            categoryBoxFacebook.setVisible(true);
            categoryBoxTwitter.setVisible(false);
            selectCategory.setEnabled(true);
            selectCategory.setVisible(true);
        }
    }

    protected String getCategory() {
        String item = "";
        if (getMedia().equals(media[1]) && !categoryBoxFacebook.isVisible()) {
            item = categoryBoxTwitter.getSelectedItem().toString();
        } else if (getMedia().equals(media[2]) && !categoryBoxTwitter.isVisible()) {
            item = categoryBoxFacebook.getSelectedItem().toString();
        }
        selectCategory.setVisible(true);
        return item;
    }

    protected void unitOfMeasurementState() {
        if (Display.graph.isSelected() && Display.graph.isEnabled()) {
            unitOfMeasure.setVisible(true);
            unitOfMeasureBox.setVisible(true);
            unitOfMeasure.setEnabled(true);
            Display.percentage.setEnabled(false);
            Display.integer.setSelected(true);
            Display.integer.setEnabled(false);
        } else if (Display.bar.isSelected() && Display.bar.isEnabled()) {
            unitOfMeasureBox.setVisible(false);
            unitOfMeasure.setVisible(false);
            unitOfMeasure.setEnabled(false);
            Display.percentage.setEnabled(true);
        }
    }

    protected String getUnitOfMeasure() {
        return unitOfMeasureBox.getSelectedItem().toString();
    }

    protected void toggleGraph() {
        if (!getMedia().equals("") && getMedia() != null &&
                getCategory() != null && !getCategory().equals("") &&
                lineChart == null && !startDateBox.getDatePicker().getModel().getValue().toString().equals("") &&
                !endDateBox.getDatePicker().getModel().getValue().toString().equals("")) {
            setResultsLineChart();
            setLineChart(new LineChart(getMedia() + " Statistics", getCategory(), datasetObject, getUnitOfMeasure()));
            lineChart.addWindowListener(lineChartAdapter());
            if (Display.graph.isSelected())
                lineChart.setVisible(true);
            if (Display.bar.isSelected())
                lineChart.setVisible(false);
        } else if (lineChart != null && Display.graph.isSelected()) {
            int choice = promptFrame();
            if (choice == 0) {
                lineChart.setVisible(false);
                lineChart.dispose();
                setResultsLineChart();
                setLineChart(new LineChart(getMedia() + " Statistics", getCategory(), datasetObject, getUnitOfMeasure()));
                if (Display.graph.isSelected())
                    lineChart.setVisible(true);
                if (Display.bar.isSelected())
                    lineChart.setVisible(false);
            } else if (choice == 1) {
                return;
            }
        }
    }

    protected void toggleBars() {
        if (!getMedia().equals("") && getMedia() != null &&
                getCategory() != null && !getCategory().equals("") &&
                barChart == null && barChartTwo == null &&
                !startDateBox.getDatePicker().getModel().getValue().toString().equals("") &&
                !endDateBox.getDatePicker().getModel().getValue().toString().equals("")) {
            setResultsBarChart();
            setBarChart(new BarChart(getMedia() + " Statistics", getCategory(), datasetObject, false));
            setBarChartTwo(new BarChart(getMedia() + " Statistics", getCategory(), datasetObject, true));
            barChart.addWindowListener(barChartAdapter());
            barChartTwo.addWindowListener(barChartAdapter());
            if (Display.integer.isSelected())
                barChart.setVisible(true);
            if (Display.percentage.isSelected())
                barChartTwo.setVisible(true);
        } else if (barChart != null) {
            int choice = promptFrame();
            if (choice == 0) {
                barChart.setVisible(false);
                barChartTwo.setVisible(false);
                barChart.dispose();
                barChartTwo.dispose();
                setResultsBarChart();
                setBarChart(new BarChart(getMedia() + " Statistics", getCategory(), datasetObject, false));
                setBarChartTwo(new BarChart(getMedia() + " Statistics", getCategory(), datasetObject, true));
                if (Display.integer.isSelected())
                    barChart.setVisible(true);
                if (Display.percentage.isSelected())
                    barChartTwo.setVisible(true);
            } else if (choice == 1) {
                return;
            }
        }
    }


    protected void writeToDataset(String query, String column) {
        String sql;
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement;
        sql = new StringBuilder(query).toString();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                datasetObject.add(new DatasetObject(rs.getInt(column), rs.getString("CompanyName"), getCategory(), rs.getString("DatePulled"), rs.getString("Time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void retrieveFromDatabaseValuesTwitterBarchart() {
        if (!datasetObject.isEmpty())
            datasetObject.clear();
        if (getCategory().equals(valuesTwitter[1])) {
            writeToDataset("SELECT FollowersCount, CompanyName, DatePulled, Time FROM TwitterFollowers WHERE DatePulled >=" +
                            "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                            "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " ORDER BY DatePulled DESC LIMIT 5",
                    "FollowersCount");
        } else if (getCategory().equals(valuesTwitter[2])) {
            writeToDataset("SELECT TweetCount, CompanyName, DatePulled, Time FROM TwitterTweets WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " ORDER BY DatePulled DESC LIMIT 5", "TweetCount");
        } else if (getCategory().equals(valuesTwitter[3])) {
            writeToDataset("SELECT ForeignersCount, CompanyName, DatePulled, Time FROM TwitterForeigners WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " ORDER BY DatePulled DESC LIMIT 5", "ForeignersCount");
        } else if (getCategory().equals(valuesTwitter[4])) {
            writeToDataset("SELECT FriendsCount, CompanyName, DatePulled, Time FROM TwitterFriends WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " ORDER BY DatePulled DESC LIMIT 5", "FriendsCount");
        } else if (getCategory().equals(valuesTwitter[5])) {
            writeToDataset("SELECT FavoritesCount, CompanyName, DatePulled, Time FROM TwitterFavorites WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " ORDER BY DatePulled DESC LIMIT 5", "FavoritesCount");
        }
    }

    protected void retrieveFromDatabaseValuesFacebookBarChart() {
        if (!datasetObject.isEmpty())
            datasetObject.clear();
        if (getCategory().equals(valuesFacebook[1])) {
            writeToDataset("SELECT LikesCount, CompanyName, DatePulled, Time FROM FacebookLikes WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " ORDER BY DatePulled DESC LIMIT 5", "LikesCount");
        } else if (getCategory().equals(valuesFacebook[2])) {
            writeToDataset("SELECT CheckinsCount, CompanyName, DatePulled, Time FROM FacebookCheckins WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " ORDER BY DatePulled DESC LIMIT 5", "CheckinsCount");
        } else if (getCategory().equals(valuesFacebook[3])) {
            writeToDataset("SELECT TalkingAboutCount, CompanyName, DatePulled, Time FROM FacebookCheckins WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " ORDER BY DatePulled DESC LIMIT 5", "TalkingAboutCount");
        }
    }

    protected void retrieveFromDatabaseValuesFacebookLineChart() {
        if (!datasetObject.isEmpty())
            datasetObject.clear();
        if (getCategory().equals(valuesFacebook[1])) {
            writeToDataset("SELECT LikesCount, CompanyName, DatePulled, Time FROM FacebookLikes WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'", "LikesCount");
        } else if (getCategory().equals(valuesFacebook[2])) {
            writeToDataset("SELECT CheckinsCount, CompanyName, DatePulled, Time FROM FacebookCheckins WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'", "CheckinsCount");
        } else if (getCategory().equals(valuesFacebook[3])) {
            writeToDataset("SELECT TalkingAboutCount, CompanyName, DatePulled, Time FROM FacebookCheckins WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'", "TalkingAboutCount");
        }
    }

    protected void retrieveFromDatabaseValuesTwitterLineChart() {
        if (!datasetObject.isEmpty())
            datasetObject.clear();
        if (getCategory().equals(valuesTwitter[1])) {
            writeToDataset("SELECT FollowersCount, CompanyName, DatePulled, Time FROM TwitterFollowers WHERE DatePulled >=" +
                            "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                            "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'",
                    "FollowersCount");
        } else if (getCategory().equals(valuesTwitter[2])) {
            writeToDataset("SELECT TweetCount, CompanyName, DatePulled, Time FROM TwitterTweets WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'", "TweetCount");
        } else if (getCategory().equals(valuesTwitter[3])) {
            writeToDataset("SELECT ForeignersCount, CompanyName, DatePulled, Time FROM TwitterForeigners WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'", "ForeignersCount");
        } else if (getCategory().equals(valuesTwitter[4])) {
            writeToDataset("SELECT FriendsCount, CompanyName, DatePulled, Time FROM TwitterFriends WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'", "FriendsCount");
        } else if (getCategory().equals(valuesTwitter[5])) {
            writeToDataset("SELECT FavoritesCount, CompanyName, DatePulled, Time FROM TwitterFavorites WHERE DatePulled >=" +
                    "'" + CustomDate.grabDate(startDateBox.getDatePicker().getModel().getValue().toString()) + "'" + " AND DatePulled <=" +
                    "'" + CustomDate.grabDate(endDateBox.getDatePicker().getModel().getValue().toString()) + "'", "FavoritesCount");
        }
    }

    protected String getMedia() {
        return mediaBox.getSelectedItem().toString();
    }
}
