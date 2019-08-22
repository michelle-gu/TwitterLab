package com.khoros.twitter;

import com.khoros.twitter.services.TwitterLabService;
import dagger.Module;
import dagger.Provides;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

@Module
public class TwitterLabModule {

    private TwitterFactory tf;

    public  TwitterLabModule(TwitterFactory tf) {
        this.tf = tf;
    }

    @Provides
    public TwitterLabService provideTwitterLabService() {
        Twitter t =  tf.getInstance();
        return new TwitterLabService(t);
    }

}
