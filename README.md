# TwitterLab

A simple java program allowing you to tweet and view your home timeline.

Usage (from command line):
1) Change directory to twitter folder `cd twitter`

2) Create file `twitter4j.properties` with your Twitter application keys following this format:
`debug=true
oauth.consumerKey=*********************
oauth.consumerSecret=******************************************
oauth.accessToken=**************************************************
oauth.accessTokenSecret=******************************************`

3) Modify the two main class tags in pom.xml to either `<mainClass>com.khoros.twitter.GetHomeTimeline</mainClass>` or `<mainClass>com.khoros.twitter.PostTweet</mainClass>` for whichever program you want to run

4) Run program: `mvn clean install` `mvn exec:java`
