// Twitter4j imports
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.ArrayList;
import java.util.List;

// Program 2: Retrieve latest home timeline from Twitter.
public class GetHomeTimeline {

    // Main method
    public static void main(String[] args) {
        List<Status> statuses = new ArrayList<Status>();
        try {
            statuses = retreiveHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        System.out.println("Your home timeline:");
        displayHomeTimeline(statuses);
    }

    // Get home timeline
    public static List<Status> retreiveHomeTimeline() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        List<Status> statuses = null;
        statuses = twitter.getHomeTimeline();
        return statuses;
    }

    // Print home timeline
    public static void displayHomeTimeline(List<Status> statuses) {
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" +
                    status.getText());
        }
    }

}
