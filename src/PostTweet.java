// Twitter4j imports
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.Scanner;

// Program 1: Pass in an argument representing a post to be published on Twitter
//            Include error checking as if we were to deploy this to production
public class PostTweet {

    // Main method
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Write your tweet (up to 280 chars): ");
        String tweet = s.nextLine();

        try {
            Status status = post(tweet);
            System.out.println("Successfully tweeted: " + status.getText());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    // Posts the given tweet
    public static Status post(String tweet) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        Status status = null;
        if (isValidTweet(tweet)) {
            status = twitter.updateStatus(tweet);
        }
        return status;
    }

    // Checks if tweet is valid
    private static boolean isValidTweet(String tweet) {
        // Tweet limit between 1-280 chars
        if (tweet.length() <= 0 || tweet.length() > 280) {
            return false;
        }
        return true;
    }

}