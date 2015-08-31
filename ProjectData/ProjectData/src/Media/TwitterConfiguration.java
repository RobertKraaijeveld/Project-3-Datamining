package Media;

import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by jls on 3/10/15.
 */
public class TwitterConfiguration {

    private static TwitterConfiguration instance = null;

    private TwitterConfiguration() {
    }

    public static TwitterConfiguration getInstance() {
        if (instance == null) {
            instance = new TwitterConfiguration();
        }
        return instance;
    }

    public static ConfigurationBuilder getConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("L2iCyLPJRmqZbeq7qbDX0ShIB");
        cb.setOAuthConsumerSecret("Lfm4tQV7eagweMCnAouDfKN3qq4umtjbvKvzIrmbqbByLoud2J");
        cb.setOAuthAccessToken("613536729-w6Gv2WohuCECxBIDQsssnqpVOynMK4DICuvmeszb");
        cb.setOAuthAccessTokenSecret("7pu9hrEqUHdLAFjNpoBPkFE1wrkKJkyBkwoYH2KBcUi6Q");
        return cb;
    }

    public static ConfigurationBuilder getConfiguration(boolean json) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("L2iCyLPJRmqZbeq7qbDX0ShIB");
        cb.setOAuthConsumerSecret("Lfm4tQV7eagweMCnAouDfKN3qq4umtjbvKvzIrmbqbByLoud2J");
        cb.setOAuthAccessToken("613536729-w6Gv2WohuCECxBIDQsssnqpVOynMK4DICuvmeszb");
        cb.setOAuthAccessTokenSecret("7pu9hrEqUHdLAFjNpoBPkFE1wrkKJkyBkwoYH2KBcUi6Q");
        cb.setJSONStoreEnabled(json);
        return cb;
    }
}
