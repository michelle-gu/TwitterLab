# TwitterLab

A simple java program allowing you to tweet and view your home timeline.

Usage (from command line):
1) Create file `twitter4j.properties` with your Twitter application keys following this format:
`debug=true
oauth.consumerKey=*********************
oauth.consumerSecret=******************************************
oauth.accessToken=**************************************************
oauth.accessTokenSecret=******************************************`

2) Modify the mainClass properties tag in pom.xml to either `<mainClass>com.khoros.twitter.GetHomeTimeline</mainClass>` or `<mainClass>com.khoros.twitter.PostTweet</mainClass>` for whichever program you want to run

3) Run program: `mvn clean install` `java -jar target/twitter-1.0-SNAPSHOT-jar-with-dependencies.jar`
