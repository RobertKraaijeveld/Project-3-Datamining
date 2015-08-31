package Media;

import twitter4j.*;

import java.io.*;
import java.util.List;

/**
 * Created by jls on 3/10/15.
 */
public final class SaveRawJSON {
    public static void main(String[] args) {
        Twitter twitter = new TwitterFactory(TwitterConfiguration.getConfiguration(true).build()).getInstance();
        System.out.println("Saving public timeline.");
        try {
            new File("statuses").mkdir();
            List<Status> statuses = twitter.getUserTimeline("@de_rotterdam");
            for (Status status : statuses) {
                String rawJSON = TwitterObjectFactory.getRawJSON(status);
                String fileName = "statuses/" + status.getId() + ".json";
                storeJSON(rawJSON, fileName);
                System.out.println(fileName + " - " + status.getText());
            }
            System.out.print("\ndone.");
            System.exit(0);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to store tweets: " + ioe.getMessage());
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }

    private static void storeJSON(String rawJSON, String fileName) throws IOException {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(fileName);
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            bw.write(rawJSON);
            bw.flush();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ignore) {
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException ignore) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignore) {
                }
            }
        }
    }
}
