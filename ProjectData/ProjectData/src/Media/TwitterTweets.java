package Media;

import Calendar.CustomDate;
import ConnectionManager.ConnectionManager;
import twitter4j.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jls on 3/12/15.
 */
public class TwitterTweets {
    private static final Twitter twitter = new TwitterFactory(TwitterConfiguration.getConfiguration().build()).getInstance();
    private static TwitterTweets instance = null;
    private static int limitRemaning = 0;
    private static List<String> foreigners = new ArrayList<String>();
//    private static Paging pg = new Paging();
//    private static int numTweets = 200;
//    private static long lastID = Long.MAX_VALUE;
//    private static ArrayList<Status> tweets = new ArrayList<Status>();
////    private static List<Status> statuses;
////    private static ResponseList<Status> status;
//
//    public void getTweets(String username) {
//        while (tweets.size() < numTweets)
//            try {
//                tweets.addAll(twitter.getUserTimeline(username, pg));
//                System.out.println("Gathered " + tweets.size() + " tweets");
//                for (Status t : tweets) {
//                    System.out.println(t.getCreatedAt());
//                    if (t.getId() < lastID) {
//                        lastID = t.getId();
//                    }
//                }
//            } catch (TwitterException e) {
//                System.out.println("Couldn't connect" + e);
//            }
//        pg.setMaxId(lastID - 1);
//    }


    private TwitterTweets() {
        rateLimitListener();
    }

    public static TwitterTweets getInstance() {
        if (instance == null) {
            instance = new TwitterTweets();
        }
        return instance;
    }

    public int getAmountOfFollowers(String username) {
        int i = 0;
        try {
            i = twitter.showUser(username).getFollowersCount();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int getFavoritesCount(String username) {
        int i = 0;
        try {
            i = twitter.showUser(username).getFavouritesCount();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int getFriendsCount(String username) {
        int i = 0;
        try {
            i = twitter.showUser(username).getFriendsCount();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int getAmountOfTweets(String username) {
        int i = 0;
        try {
            i = twitter.showUser(username).getStatusesCount();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return i;
    }

    private void retrieveAmountofForeigners(String username) throws TwitterException {
        PagableResponseList<User> followers;
        long cursor = -1;
        do {
            followers = twitter.getFollowersList(username, cursor);
            for (User follower : followers) {
                foreigners.add(follower.getLocation());
            }
        } while ((cursor = followers.getNextCursor()) != 0 && getLimitRemaining() != 0);
    }

    public int getAmountOfForeigners(String username) {
        List<String> amount = new ArrayList<String>();
        String[] lang = {"nl", "NL"};
        if (foreigners.size() != 0) {
            for (String s : foreigners) {
                if (!s.equals(lang[0]) || !s.equals(lang[1]))
                    amount.add(s);
            }
        } else if (foreigners.isEmpty()) {
            try {
                retrieveAmountofForeigners(username);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        return amount.size();
    }

    public int getLimitRemaining() {
        return limitRemaning;
    }

    public void rateLimitListener() {
        twitter.addRateLimitStatusListener(new RateLimitStatusListener() {
            @Override
            public void onRateLimitStatus(RateLimitStatusEvent rateLimitStatusEvent) {
//                JOptionPane.showMessageDialog(new JPanel(), "Warning rate limit Reached!");
            }

            @Override
            public void onRateLimitReached(RateLimitStatusEvent rateLimitStatusEvent) {
                limitRemaning = rateLimitStatusEvent.getRateLimitStatus().getLimit();
            }
        });
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

    public void insert(String tableName, String tableDataField, int data, String username) {
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
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String checkUpdateDate() {
        Date date = new Date();
        CustomDate cdate = new CustomDate(date);
        Connection connection = null;
        PreparedStatement stmt = null;
        String lastUpdateDate = "";

        String sql = new StringBuilder("SELECT TwitterTweets.DatePulled FROM TwitterTweets ")
                .append("INNER JOIN TwitterFriends ON TwitterTweets.DatePulled = TwitterFriends.DatePulled ")
                .append("INNER JOIN TwitterForeigners ON TwitterFriends.DatePulled = TwitterForeigners.DatePulled ")
                .append("INNER JOIN TwitterFollowers ON TwitterForeigners.DatePulled = TwitterFollowers.DatePulled ")
                .append("INNER JOIN TwitterFavorites ON TwitterFollowers.DatePulled = TwitterFavorites.DatePulled ")
                .append("WHERE TwitterTweets.Datepulled=")
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
                connection.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lastUpdateDate;
    }

    public void update(String username, String realname) throws SQLException {
        create("TwitterFollowers", "FollowersCount");
        create("TwitterFriends", "FriendsCount");
        create("TwitterForeigners", "ForeignersCount");
        create("TwitterTweets", "TweetCount");
        create("TwitterFavorites", "FavoritesCount");
        insert("TwitterFollowers", "FollowersCount", getAmountOfFollowers(username), realname);
        insert("TwitterFriends", "FriendsCount", getFriendsCount(username), realname);
        insert("TwitterForeigners", "ForeignersCount", getAmountOfForeigners(username), realname);
        insert("TwitterTweets", "TweetCount", getAmountOfTweets(username), realname);
        insert("TwitterFavorites", "FavoritesCount", getFavoritesCount(username), realname);
    }
}
