package Media;

import facebook4j.conf.ConfigurationBuilder;

/**
 * Created by jls on 3/24/15.
 */
public class FacebookConfiguration {

    private static FacebookConfiguration instance = null;
    public final static String accesTokenString = "CAAUS0yftcAcBAMPMCP3OZCDCcuZB75g7SZAh0pYFWQoCxb2BBFWSAKjuHmcBm7ZB9WFGYBQZAt55EtxPsZBwc8Hui9HwMQdiJWPfwqqcFrEuFFK0Mxr9qVmZB7OjZBhPORoLK1DhshrGGBVB4cetq2ZCSDnvnS3ZCkTBY2Q8XWOomYPWcsfdzWcYIj3EdMYQLBUfvBZCwIM03r3vDDceDk1UZBhA";

    private FacebookConfiguration() {
    }

    public static FacebookConfiguration getInstance() {
        if (instance == null) {
            instance = new FacebookConfiguration();
        }
        return instance;
    }

    public static ConfigurationBuilder getConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthAccessToken(accesTokenString);
        cb.setJSONStoreEnabled(false);
        return cb;
    }

    public static ConfigurationBuilder getConfiguration(boolean json) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthAccessToken(accesTokenString);
        cb.setJSONStoreEnabled(json);
        return cb;
    }
}
