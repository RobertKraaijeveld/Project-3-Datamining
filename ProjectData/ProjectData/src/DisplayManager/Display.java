package DisplayManager;

import Calendar.CustomDate;
import ConnectionManager.ConnectionManager;
import Media.FacebookPosts;
import Media.TwitterTweets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by jls on 2/9/15.
 */

public class Display {

    private static int WIDTH = 600;
    private static int HEIGHT = 400;

    private SelectionPanel selectionPanel;
    private LoginPanel loginPanel;

    private static JFrame frame;
    private static Display instance = null;
    private JMenuBar menu;

    private JMenu file;
    private JMenu unit;
    private JMenu visualisation;
    private JMenu about;
    protected static JCheckBoxMenuItem integer;
    protected static JCheckBoxMenuItem percentage;
    protected static JCheckBoxMenuItem bar;
    protected static JCheckBoxMenuItem graph;
    private JMenuItem help;
    private JMenuItem update;
    private JMenuItem print;
    private JMenuItem exit;

    //Very ugly code, but who cares?
    // @Robert:// I cleaned it up for ya! In case of alot of concatenation use a Stringbuilder to append to a single String object instead of creating multiple ones
    private String helpContent = new StringBuilder("Stap 1: Log bovenaan de applicatie in met uw gebruikersnaam en wachtwoord.\n").append("\n").append("Stap 2: Selecteer de begindatum en de einddatum waartussen u data wilt ophalen.\n").append(
            "BV: 1-1-12 en 1-1-13 haalt alle data op tussen de 1ste dag v.d. 1ste maand van 2012,\n").append("en de 1ste dag v.d. 1ste maand van 2013.\n").append(
            "\n").append("Stap 3: Selecteer onderaan het sociale netwerk waarvan u data wil ophalen.\n").append("\n").append("Stap 4: Selecteer daarna welke datatypen u wilt zien.\n").append(
            "Dit is afhankelijk van het sociale netwerk dat u kiest. \n").append("BV. Facebook -> Hoeveelheid Likes \n").append("\n").append("Stap 5: Als u al deze waarden geselecteerd heeft kunt u op \"find\" klikken,\n").append(
            "en krijgt u een grafiek te zien. Iedere kleur staaf/lijn staat voor 1 bedrijf. \n").toString();


    private Display() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // Initialize
        initComponents();
        // Create
        createMenu();
        createDisplay();
        // Listen
        menuListener();
    }

    public static Display getInstance() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        if (instance == null)
            instance = new Display();
        return instance;
    }

    private void initComponents() {
        frame = new JFrame(" Social media Statistics");
        selectionPanel = new SelectionPanel();
        loginPanel = new LoginPanel();
        menu = new JMenuBar();
        update = new JMenuItem(" Update Database ");
        file = new JMenu(" File ");
        unit = new JMenu(" Unit ");
        visualisation = new JMenu(" Visualisation ");
        about = new JMenu(" About ");
        integer = new JCheckBoxMenuItem(" Integer ");
        percentage = new JCheckBoxMenuItem(" Percentage ");
        bar = new JCheckBoxMenuItem(" Barchart ");
        graph = new JCheckBoxMenuItem(" Linechart ");
        help = new JMenuItem(" Help ");
        print = new JMenuItem(" Print ");
        exit = new JMenuItem(" Exit ");
    }

    private void createMenu() {
        file.add(update);
        file.addSeparator();
        file.add(print);
        file.addSeparator();
        file.add(exit);
        unit.add(integer);
        unit.add(percentage);
        visualisation.add(bar);
        visualisation.add(graph);
        about.add(help);
        menu.add(file);
        menu.add(unit);
        menu.add(visualisation);
        menu.add(about);
    }

    private void createDisplay() {
        bar.setSelected(true);
        bar.setEnabled(false);
        graph.setSelected(false);
        graph.setEnabled(false);
        integer.setSelected(true);
        integer.setEnabled(false);
        percentage.setEnabled(false);
        frame.setJMenuBar(menu);
        frame.setLayout(new BorderLayout());
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                frame.dispose();
            }
        });
        frame.add(loginPanel, BorderLayout.NORTH);
        frame.add(selectionPanel, BorderLayout.CENTER);
//        frame.add(barChart, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void menuListener() {
        integer.addActionListener(e -> {
            if (percentage.isSelected()) {
                percentage.setSelected(false);
                integer.setSelected(true);
                if (selectionPanel.barChart != null || selectionPanel.barChartTwo != null) {
                    selectionPanel.barChart.setVisible(true);
                    selectionPanel.barChartTwo.setVisible(false);
                }
            }
            if (!integer.isSelected()) {
                percentage.setSelected(true);
                if (selectionPanel.barChart != null || selectionPanel.barChartTwo != null) {
                    selectionPanel.barChart.setVisible(true);
                    selectionPanel.barChartTwo.setVisible(false);
                }
            }
        });

        percentage.addActionListener(e -> {
            if (integer.isSelected()) {
                integer.setSelected(false);
                percentage.setSelected(true);
                if (selectionPanel.barChart != null || selectionPanel.barChartTwo != null) {
                    selectionPanel.barChart.setVisible(false);
                    selectionPanel.barChartTwo.setVisible(true);
                }
            }
            if (!percentage.isSelected()) {
                integer.setSelected(true);
                if (selectionPanel.barChart != null || selectionPanel.barChartTwo != null) {
                    selectionPanel.barChart.setVisible(true);
                    selectionPanel.barChartTwo.setVisible(false);
                }
            }
        });

        bar.addActionListener(e -> {
            if (graph.isSelected()) {
                graph.setSelected(false);
                bar.setSelected(true);
                selectionPanel.unitOfMeasurementState();
                selectionPanel.setLineChart(null);
            }
            if (!bar.isSelected()) {
                graph.setSelected(true);
                selectionPanel.unitOfMeasurementState();
            }
        });

        graph.addActionListener(e -> {
            if (bar.isSelected()) {
                bar.setSelected(false);
                graph.setSelected(true);
                selectionPanel.unitOfMeasurementState();
            }
            if (!graph.isSelected()) {
                bar.setSelected(true);
                selectionPanel.unitOfMeasurementState();
            }
        });

        update.addActionListener(e -> doUpdate());
        help.addActionListener(e -> JOptionPane.showMessageDialog(new JPanel(), helpContent));
        print.addActionListener(e -> System.out.println("Create print results"));
        exit.addActionListener(e -> System.exit(0));
    }

    protected String convertUserNameToRealName(String username) {
        String realname = "";
        if (username.contains("ssrotterdam") || username.contains("de_rotterdam"))
            realname = "SS-Rotterdam";
        if (username.contains("indemarkthal"))
            realname = "Markthal";
        if (username.contains("euromast010"))
            realname = "Euromast";
        if (username.contains("hotelnewyorkrotterdam") || username.contains("HotelNewYork"))
            realname = "Hotel New York";
        if (username.contains("rdamcentraal") || username.contains("CS Rotterdam"))
            realname = "Rotterdam Centraal Station";
        return realname;
    }

    protected void doUpdate() {
        String[] usernamesFacebook = {"ssrotterdam", "indemarkthal", "euromast010", "hotelnewyorkrotterdam", "CS Rotterdam"};
        String[] usernamesTwitter = {"de_rotterdam", "indemarkthal", "euromast010", "HotelNewYork", "rdamcentraal"};
        Date date = new Date();
        CustomDate cdate = new CustomDate(date);
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        if (!FacebookPosts.checkUpdateDate().equals(new CustomDate(new Date()).getDateWithSplit())) {
            for (String username : usernamesFacebook)
                try {
                    FacebookPosts.getInstance().update(username, convertUserNameToRealName(username));
                } catch (SQLException e) {
                    System.out.println("error");
                    e.printStackTrace();
                }
        }
        if (!TwitterTweets.checkUpdateDate().equals(new CustomDate(new Date()).getDateWithSplit())) {
            for (String username : usernamesTwitter)
                try {
                    TwitterTweets.getInstance().update(username, convertUserNameToRealName(username));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    protected void login() {
        if (!loginPanel.getPassFieldText().equals("") && loginPanel.getPassFieldText() != null &&
                !loginPanel.getUserFieldText().equals("") && loginPanel.getUserFieldText() != null) {
            ConnectionManager.setLoginCredentials(loginPanel.getUserFieldText(), loginPanel.getPassFieldText());
            if (ConnectionManager.getConnection() != null) {
//                ConnectionManager.createQuery("CREATE SCHEMA IF NOT EXISTS `Project` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci");
                JOptionPane.showMessageDialog(loginPanel, "You've successfully logged in as: " + loginPanel.getUserFieldText());
                ConnectionManager.loggedIn = true;
                selectionPanel.setActiveState();
                loginPanel.loginBtn.setText("Hello, " + loginPanel.getUserFieldText());
                loginPanel.loginBtn.setEnabled(false);
                loginPanel.loginBtn.setVisible(false);
                loginPanel.passField.setVisible(false);
                loginPanel.userField.setVisible(false);
                loginPanel.userLabel.setVisible(false);
                loginPanel.passLabel.setVisible(false);
                loginPanel.userDisplay.setEnabled(false);
                loginPanel.userDisplay.setVisible(true);
                loginPanel.userDisplay.setText("Hello: " + loginPanel.getUserFieldText());
                integer.setEnabled(true);
                percentage.setEnabled(true);
                bar.setEnabled(true);
                graph.setEnabled(true);
                // Update
                doUpdate();
            } else {
                loginPanel.clearPassField();
            }
        }
    }
}
