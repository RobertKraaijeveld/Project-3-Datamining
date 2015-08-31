package Media;

import Calendar.CustomDate;
import ConnectionManager.ConnectionManager;
import facebook4j.*;

import java.sql.*;
import java.util.Date;

/**
 * Created by jls on 3/24/15.
 */
public class FacebookPosts {
    private static final Facebook facebook = new FacebookFactory(FacebookConfiguration.getConfiguration().build()).getInstance();
    private static FacebookPosts instance = null;
    private static Page pgID;
    private static ResponseList<Post> feeds;
//    private static Facebook facebook;
//    private static FacebookPosts instance = null;

//    private FacebookPosts() {
//        // Generate facebook instance.
//        facebook = new FacebookFactory().getInstance();
//
//        // access token ...
//        String accessTokenString = "CAAUS0yftcAcBAMPMCP3OZCDCcuZB75g7SZAh0pYFWQoCxb2BBFWSAKjuHmcBm7ZB9WFGYBQZAt55EtxPsZBwc8Hui9HwMQdiJWPfwqqcFrEuFFK0Mxr9qVmZB7OjZBhPORoLK1DhshrGGBVB4cetq2ZCSDnvnS3ZCkTBY2Q8XWOomYPWcsfdzWcYIj3EdMYQLBUfvBZCwIM03r3vDDceDk1UZBhA";
//
//        facebook.setOAuthAppId("","");
//        AccessToken at = new AccessToken(accessTokenString);
//
//        // Set access token.
//        facebook.setOAuthAccessToken(at);
//    }
//
//    public static FacebookPosts getInstance() {
//        if (instance == null) {
//            instance = new FacebookPosts();
//        }
//        return instance;
//    }


    private FacebookPosts() {
    }

    public static FacebookPosts getInstance() {
        if (instance == null) {
            instance = new FacebookPosts();
        }
        return instance;
    }

    public int getAmountOfCheckins(String username) {
        try {
            pgID = facebook.getPage(username);
        } catch (FacebookException e) {
//            JOptionPane.showMessageDialog(new JPanel(), "There seems to be a problem with the: 'Checkings'!" );
            return pgID.getCheckins();
        }
        return pgID.getCheckins();
    }

    public int getAmountOfLikes(String username) {
        try {
            pgID = facebook.getPage(username);
        } catch (FacebookException e) {
//            JOptionPane.showMessageDialog(new JPanel(), "There seems to be a problem with the: 'Likes'!");
            return pgID.getLikes();
        }
        return pgID.getLikes();
    }

    public int getTalkingAboutCount(String username) {
        try {
            pgID = facebook.getPage(username);
        } catch (FacebookException e) {
//            JOptionPane.showMessageDialog(new JPanel(), "There seems to be a problem with the: About Count!");
            return pgID.getTalkingAboutCount();
        }
        return pgID.getTalkingAboutCount();
    }

    public int getAmountOfFeeds(String username) {
        try {
            feeds = facebook.getFeed(username, new Reading().limit(250));
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        return feeds.size();
    }

    public void create(String tableName, String tableDataField) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(tableName)
                .append("(ID INT NOT NULL AUTO_INCREMENT,")
                .append(tableDataField).append(" INT NULL,")
                .append("DatePulled DATE NULL,")
                .append("Time TIME NULL,")
                .append("CompanyName VARCHAR(45) NULL,")
                .append("PRIMARY KEY (ID))").toString();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }
    }

    public void insert(String tableName, String tableDataField, int data, String username) throws SQLException {
        Date date = new Date();
        CustomDate cdate = new CustomDate(date);
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String sql = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append("(")
                .append(tableDataField)
                .append(",")
                .append("DatePulled, Time, CompanyName)")
                .append("VALUES(?, ?, ?, ?);")
                .toString();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, data);
            preparedStatement.setString(2, cdate.getDate());
            preparedStatement.setString(3, cdate.getTime());
            preparedStatement.setString(4, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }
    }

    public static String checkUpdateDate() {
        Date date = new Date();
        CustomDate cdate = new CustomDate(date);
        Connection connection = null;
        PreparedStatement stmt = null;
        String lastUpdateDate = "";

        String sql = new StringBuilder("SELECT FacebookLikes.DatePulled FROM FacebookLikes ")
                .append("INNER JOIN FacebookTalkingabout ON FacebookLikes.DatePulled = FacebookTalkingAbout.DatePulled ")
                .append("INNER JOIN FacebookCheckins ON FacebookTalkingAbout.DatePulled = FacebookCheckins.DatePulled ")
                .append("WHERE FacebookLikes.Datepulled=")
                .append("'" + cdate.getDateWithSplit() + "'")
                .append(" LIMIT 1;").toString();

        try {
            connection = ConnectionManager.getConnection();
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next() && lastUpdateDate.equals("")) {
                lastUpdateDate = rs.getString(1);
            }
        } catch (SQLException e1) {
            System.err.println(e1.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lastUpdateDate;
    }

    public void update(String username, String realname) throws SQLException {
        create("FacebookLikes", "LikesCount");
        create("FacebookCheckins", "CheckinsCount");
        create("FacebookTalkingAbout", "FacebookAboutCount");
        insert("FacebookLikes", "LikesCount", getAmountOfLikes(username), realname);
        insert("FacebookCheckins", "CheckinsCount", getAmountOfCheckins(username), realname);
        insert("FacebookTalkingAbout", "FacebookAboutCount", getTalkingAboutCount(username), realname);
    }
}
